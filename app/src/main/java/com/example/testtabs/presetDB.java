package com.example.testtabs;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.String.valueOf;

public class presetDB extends AppCompatActivity {
    Button btnAddOneWeek, btnGetLastRow, btnDeleteAll, btnChkSumBy;
    TextView txtView;
    dbHelper db_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preset_db);

        btnAddOneWeek = findViewById(R.id.btnAddOneWk);
        btnGetLastRow = findViewById(R.id.btnSelectLastRow);
        btnDeleteAll = findViewById(R.id.btnDelAll);
        btnChkSumBy = findViewById(R.id.btnChkSumBy);
        txtView = findViewById(R.id.textView);

        btnAddOneWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db_helper = new dbHelper(presetDB.this);
                String txtFoodName;
                int calorie;
                String txtDT;

                Date currentTime;
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -7);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");

                int _i = -14; //gap in hours between now and 8:00 am
                cal.add(Calendar.HOUR, _i);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "apple pie";
                calorie = 308;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 1);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "apple pie";
                calorie = 308;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 3);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "baby back ribs";
                calorie = 548;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 1);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "baby back ribs";
                calorie = 548;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 5);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "baklava";
                calorie = 334;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 1);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "baklava";
                calorie = 334;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");

                cal.add(Calendar.HOUR, 13);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "beef carpaccio";
                calorie = 340;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 1);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "beef carpaccio";
                calorie = 340;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 3);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "beef tarfare";
                calorie = 541;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 1);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "beef tarfare";
                calorie = 541;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 5);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "beet salad";
                calorie = 150;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 1);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "beet salad";
                calorie = 150;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");

                cal.add(Calendar.HOUR, 13);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "beignets";
                calorie = 527;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 1);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "beignets";
                calorie = 527;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 3);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "bibimbap";
                calorie = 560;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 1);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "bibimbap";
                calorie = 560;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 6);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "bread pudding";
                calorie = 133;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");

                cal.add(Calendar.HOUR, 13);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "breakfast burrito";
                calorie = 351;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 1);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "breakfast burrito";
                calorie = 351;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 3);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "bruschetta";
                calorie = 452;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 1);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "bruschetta";
                calorie = 452;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 5);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "caesar salad";
                calorie = 100;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 1);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "caesar salad";
                calorie = 100;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");

                cal.add(Calendar.HOUR, 13);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "cannoli";
                calorie = 448;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 1);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "cannoli";
                calorie = 448;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 3);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "caprese salad";
                calorie = 280;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 1);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "caprese salad";
                calorie = 280;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 5);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "carrot cake";
                calorie = 320;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 1);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "carrot cake";
                calorie = 320;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");

                cal.add(Calendar.HOUR, 13);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "ceviche";
                calorie = 373;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 1);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "ceviche";
                calorie = 373;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 3);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "cheese plate";
                calorie = 388;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 1);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "cheese plate";
                calorie = 388;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 5);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "cheesecake";
                calorie = 372;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 1);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "cheesecake";
                calorie = 372;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");

                cal.add(Calendar.HOUR, 13);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "chicken curry";
                calorie = 440;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 1);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "chicken curry";
                calorie = 440;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 3);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "chicken quesadilla";
                calorie = 270;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 1);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "chicken quesadilla";
                calorie = 270;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 5);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "chicken wings";
                calorie = 347;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 1);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "chicken wings";
                calorie = 347;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");

                cal.add(Calendar.HOUR, 13);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "chocolate cake";
                calorie = 295;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
                cal.add(Calendar.HOUR, 1);
                currentTime = cal.getTime();
                txtDT = df.format(currentTime);
                txtFoodName = "chocolate cake";
                calorie = 295;
                db_helper.insertRowToLog(txtDT, txtFoodName, calorie, "");
            }
        });

        btnGetLastRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db_helper = new dbHelper(presetDB.this);
                String _temp = "";
                /*
                Cursor _cur = db_helper.getLastRow();
                if (_cur.moveToFirst()){
                    do{
                        _temp = _cur.getString(0) + "  " + valueOf(_cur.getString(1));
                    }while(_cur.moveToNext());
                }
                 */
                Cursor _cur = db_helper.getSumByDate();
                //while (_cur.moveToNext()){

                //}
                //_cur.moveToFirst();
                //_temp = _cur.getString(0) + "  " + valueOf(_cur.getString(1));
                _temp = valueOf(_cur.getCount());
                txtView.setText(_temp);
                _cur.close();
            }
        });

        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db_helper = new dbHelper(presetDB.this);
                db_helper.deleteAllRows();
            }
        });

        btnChkSumBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db_helper = new dbHelper(presetDB.this);
                Cursor _cur = db_helper.getSumByDate();
                txtView.setText(String.valueOf(_cur.getCount()));
            }
        });
    }
}