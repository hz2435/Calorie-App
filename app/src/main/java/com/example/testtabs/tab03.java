package com.example.testtabs;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

public class tab03 extends Fragment {
    Button btnUpdate, btnCalculate;
    TextView tvTest, tvRecCalorie;
    EditText tvAge, tvWeight, tvWeightGoal, tvCalorieGoal;
    RadioGroup rgGender;
    RadioButton rbtnGender;
    RadioButton rbnM, rbnF, rbnO;
    Spinner spActivity;
    dbHelper db_helper;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public tab03() {
        // Required empty public constructor
    }

    public static tab03 newInstance(String param1, String param2) {
        tab03 fragment = new tab03();
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
        return inflater.inflate(R.layout.fragment_tab03, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnUpdate = getView().findViewById(R.id.btnUpdate);
        btnCalculate = getView().findViewById(R.id.btnCal);
        tvAge = getView().findViewById(R.id.tvAge);
        tvWeight = getView().findViewById(R.id.tvWeight);
        tvWeightGoal = getView().findViewById(R.id.tvWeightGoal);
        spActivity = getView().findViewById(R.id.spActivity);
        tvRecCalorie = getView().findViewById(R.id.tvRecCalorie);
        tvCalorieGoal = getView().findViewById(R.id.tvCalorieGoal);
        rgGender = getView().findViewById(R.id.rgGender);
        rbnM = getView().findViewById(R.id.rbM);
        rbnF = getView().findViewById(R.id.rbF);
        rbnO = getView().findViewById(R.id.rbO);
        db_helper = new dbHelper(getActivity());
        tvTest = getView().findViewById(R.id.tvTest);

        Cursor _curMySettings = db_helper.getAllRows2();
        String _name = "";
        if (_curMySettings.moveToFirst()) {
            do {
                _name = _curMySettings.getString(0);
                switch (_name){
                    case "Age":
                        tvAge.setText(_curMySettings.getString(1));
                        break;
                    case "Gender":
                        switch (_curMySettings.getString(1)){
                            case "M":
                                rbnM.setChecked(true);
                                break;
                            case "F":
                                rbnF.setChecked(true);
                                break;
                            case "Other":
                                rbnO.setChecked(true);
                                break;
                        }
                        break;
                    case "Weight":
                        tvWeight.setText(_curMySettings.getString(1));
                        break;
                    case "WeightGoal":
                        tvWeightGoal.setText(_curMySettings.getString(1));
                        break;
                    case "Activity":
                        spActivity.setSelection(Integer.parseInt(_curMySettings.getString(1)));
                        break;
                    case "CalorieGoal":
                        tvCalorieGoal.setText(_curMySettings.getString(1));
                        break;
                }
            } while (_curMySettings.moveToNext());
        }
        _curMySettings.close();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _gender = "Other";
                if(rgGender.getCheckedRadioButtonId() != -1){
                    rbtnGender = getView().findViewById(rgGender.getCheckedRadioButtonId());
                    _gender = (String) rbtnGender.getText();
                }
                String _age = "";
                _age = tvAge.getText().toString();
                String _weight = "";
                _weight = tvWeight.getText().toString();
                String _weightGoal = "";
                _weightGoal = tvWeightGoal.getText().toString();
                long _activityID = 0;
                _activityID = spActivity.getSelectedItemId();
                String _calorieGoal = "";
                _calorieGoal = tvCalorieGoal.getText().toString();

                boolean results;
                //results = db_helper.insertRow2("Weight", _weight);
                results = db_helper.updateIfExistsElseInsert("Age", _age);
                results = db_helper.updateIfExistsElseInsert("Gender", _gender);
                results = db_helper.updateIfExistsElseInsert("Weight", _weight);
                results = db_helper.updateIfExistsElseInsert("WeightGoal", _weightGoal);
                results = db_helper.updateIfExistsElseInsert("Activity", String.valueOf(_activityID));
                results = db_helper.updateIfExistsElseInsert("CalorieGoal", _calorieGoal);
                if(results){
                    Toast.makeText(getActivity(), "Your info has been saved", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Saving failed", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCalculate.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _gender = "";
                int _genderID = 0;
                int _age = 0;
                int _weight = 0;
                int _weightGoal = 0;
                boolean _toLoseWeight = true;
                long _activityID = 0;
                int _recCalorie = 0;

                //step 1: gender
                _genderID = rgGender.getCheckedRadioButtonId();
                RadioButton _selected = rgGender.findViewById(_genderID);
                _gender = (String) _selected.getText();
                switch (_gender){
                    case "M":
                        _recCalorie = 2600;
                        break;
                    case "F":
                        _recCalorie = 2000;
                        break;
                    case "Other":
                        _recCalorie = 2300;
                        break;
                }
                //step 2: activity level
                _activityID = spActivity.getSelectedItemId();
                switch ((int) _activityID){
                    case 0:
                        _recCalorie = (int) (_recCalorie * 0.9);
                        break;
                    case 2:
                        _recCalorie = (int) (_recCalorie *1.1);
                }
                //step 3: want to lose weight
                _weight = Integer.parseInt(tvWeight.getText().toString());
                _weightGoal = Integer.parseInt(tvWeightGoal.getText().toString());
                if(_weight > _weightGoal){
                    _recCalorie = (int) (_recCalorie * _weightGoal/_weight);
                }
                //step 4: age
                _age = Integer.parseInt(tvAge.getText().toString());
                if(_age >= 50) _recCalorie = (int) (_recCalorie * 0.9);
                if(_age <= 30) _recCalorie = (int) (_recCalorie * 1.1);

                tvRecCalorie.setText(String.valueOf(_recCalorie));
            }
        }));
    }
}