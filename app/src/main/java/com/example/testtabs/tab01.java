package com.example.testtabs;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class tab01 extends Fragment {
    ImageView imvPhoto;
    Button btnCapture, btnSave;
    EditText etFoodName, etCalorie;
    ListView lvList;
    TextView tvNote;
    RadioGroup rgPortion;
    RadioButton rbtnPortion;
    dbHelper db_helper;

    List<String> listNames = new ArrayList<String>();
    ArrayAdapter<String> arrAdp;

    private static final int INPUT_SIZE = 299;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128.0f;
    private static final String INPUT_NAME = "Mul:0";
    private static final String OUTPUT_NAME = "final_result";
    private static final String MODEL_FILE = "file:///android_asset/graph_new.pb";
    private static final String LABEL_FILE = "file:///android_asset/labels_new.txt";
    private Spinner spinner;
    private Classifier classifier;

    private ArrayAdapter<String> spDataAdapter;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public tab01() {
        // Required empty public constructor
    }

    public static tab01 newInstance(String param1, String param2) {
        tab01 fragment = new tab01();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab01, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initiate all variables
        imvPhoto = getView().findViewById(R.id.imvPhoto);
        btnCapture = getView().findViewById(R.id.btnCapture);
        btnSave= getView().findViewById(R.id.btnSave);
        etFoodName = getView().findViewById(R.id.et_foodName);
        etCalorie = getView().findViewById(R.id.et_calorie);
        lvList = getView().findViewById(R.id.lv_list);
        spinner = getView().findViewById(R.id.spinner);
        rgPortion = getView().findViewById(R.id.rgPortion);
        tvNote = getView().findViewById(R.id.tvNote);

        //populate Trie for manual entry's autocompletion
        String currentLine;
        Trie tFoodName = new Trie();
        try {
            BufferedReader objReader = new BufferedReader(new InputStreamReader(getActivity().getAssets().open("calories.txt")));
            while ((currentLine = objReader.readLine()) != null){
                tFoodName.insert(currentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        etFoodName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String _prefix = etFoodName.getText().toString();
                listNames.clear();
                listNames = tFoodName.autocomplete(_prefix);
                arrAdp = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, listNames);
                lvList.setAdapter(arrAdp);
            }
        });
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String _temp = lvList.getItemAtPosition(position).toString();
                etFoodName.setText(_temp.split(":")[0]);
                etCalorie.setText(_temp.split(":")[1]);
                listNames.clear();
                arrAdp = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, listNames);
                lvList.setAdapter(arrAdp);
            }
        });

        //populate a note at bottom of the page to remind user the remaining calorie allowance
        populateNote();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = spinner.getSelectedItem().toString();
                int _calorie = getCalorie(selectedItem);
                etFoodName.setText(selectedItem);
                etCalorie.setText(String.valueOf(_calorie));
                spDataAdapter.clear();
                spinner.setAdapter(spDataAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spDataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<>());
        spinner.setAdapter(spDataAdapter);

        ArrayList<String> strFoodNames = new ArrayList<>();
        String strFoodName = "";
        try {
            BufferedReader objReader = new BufferedReader(new InputStreamReader(getContext().getAssets().open("labels.txt")));
            while ((strFoodName = objReader.readLine()) != null){
                strFoodNames.add(strFoodName);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        ActivityResultLauncher<Intent> arl = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            showImage(data);
                            nameImage(data);
                        }
                    }
                });

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //request camera permission
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(), new String[]{
                            Manifest.permission.CAMERA
                    }, 100);
                }
                else {
                    //Toast.makeText(newCamera.this, "new code works", Toast.LENGTH_LONG).show();
                    Intent intTakePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    arl.launch(intTakePhoto);
                }

            }
        });

        Date currentTime = Calendar.getInstance().getTime();
        db_helper = new dbHelper(getActivity());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double mealPortion = 0.0;
                if(rgPortion.getCheckedRadioButtonId() != -1){
                    rbtnPortion = getView().findViewById(rgPortion.getCheckedRadioButtonId());
                    String checkedButton = (String) rbtnPortion.getText();
                    switch (checkedButton){
                        case "3":
                            mealPortion = 3.00;
                            break;
                        case "2":
                            mealPortion = 2.00;
                            break;
                        case "1":
                            mealPortion = 1.00;
                            break;
                        case "1/2":
                            mealPortion = 0.50;
                            break;
                        case "1/3":
                            mealPortion = 0.33;
                            break;
                        case "1/4":
                            mealPortion = 0.25;
                            break;
                        default:
                            mealPortion = 1.00;
                    }
                }

                String txtFoodName = String.valueOf(etFoodName.getText());
                int calorie = Integer.parseInt(String.valueOf(etCalorie.getText()));
                calorie= (int) (calorie* mealPortion);
                //String txtDT = currentTime.toString();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
                String txtDT = df.format(currentTime);

                boolean insertResutls = db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");

                if(insertResutls){
                    Toast.makeText(getActivity(), "Your meal has been recorded", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Saving failed", Toast.LENGTH_LONG).show();
                }
                populateNote();
                //Tue Jul 13 16:13:52 CDT 2021
            }
        });
    }

    protected void showImage(Intent data){
        Bitmap myPhoto = (Bitmap) data.getExtras().get("data");
        imvPhoto.setImageBitmap(myPhoto);
    }

    protected void nameImage(Intent data){
        Bitmap myImage = (Bitmap) data.getExtras().get("data");

        Bitmap bitmap2 = Bitmap.createScaledBitmap(myImage, 299, 299, false);
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, bs);
        byte[] BYTE = bs.toByteArray();
        Bitmap bitmap3 = BitmapFactory.decodeByteArray(BYTE, 0, BYTE.length);
        classifier =
                TensorFlowImageClassifier.create(
                        getContext().getAssets(),
                        MODEL_FILE,
                        LABEL_FILE,
                        INPUT_SIZE,
                        IMAGE_MEAN,
                        IMAGE_STD,
                        INPUT_NAME,
                        OUTPUT_NAME);

        final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap3);

        if(results.size() ==0){
            etFoodName.setText("can not name the image!");
        }
        else {
            etFoodName.setText("find name!");
        }

        setResults(results);
        spinner.setAdapter(spDataAdapter);
    }

    private void setResults(List<Classifier.Recognition> outputs) {
        spDataAdapter.clear();
        List<String> categories = new ArrayList<String>();

        for(Classifier.Recognition result: outputs){
            categories.add(result.getTitle());
        }
        spDataAdapter.addAll(categories);
    }

    private int getCalorie(String foodname){
        String currentLine;
        int calorie = 0;
        try {
            BufferedReader objReader = new BufferedReader(new InputStreamReader(getContext().getAssets().open("calories.txt")));
            while ((currentLine = objReader.readLine()) != null){
                if(foodname.equals(currentLine.split(":")[0])){
                    calorie = Integer.parseInt(currentLine.split(":")[1]);
                    return calorie;
                };
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return calorie;
    }

    private void populateNote(){
        db_helper = new dbHelper(getActivity());
        Cursor _curToday = db_helper.getTodayCalorie();
        Cursor _curGoal = db_helper.getCalorieGoal();
        String _strToday = "";
        String _strGoal = "";
        int _intGap = 0;

        if(_curToday.getCount() > 0){
            _curToday.moveToFirst();
            _strToday = _curToday.getString(1);
            if(_curGoal.getCount() > 0){
                _curGoal.moveToFirst();
                _strGoal = _curGoal.getString(0);
                _intGap = Integer.parseInt(_strGoal) - Integer.parseInt(_strToday);
                tvNote.setText("You have had " + _strToday + " calories for today, and you can have "
                        + String.valueOf(_intGap) +" more calories before reaching your goal.");
            } else {
                tvNote.setText("You have had " + _strToday + " calories for today.");
            }
        } else {
            if(_curGoal.getCount() > 0){
                _curGoal.moveToFirst();
                _strGoal = _curGoal.getString(0);
                tvNote.setText("This is your first meal for today, and your daily calorie goal is " + _strGoal);
            } else {
                tvNote.setText("This is your first meal for today.");
            }
        }

        _curToday.close();
        _curGoal.close();
    }
}