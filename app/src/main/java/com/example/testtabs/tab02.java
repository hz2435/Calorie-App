package com.example.testtabs;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class tab02 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public tab02() {
        // Required empty public constructor
    }

    public static tab02 newInstance(String param1, String param2) {
        tab02 fragment = new tab02();
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
        return inflater.inflate(R.layout.fragment_tab02, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper db_helper;
        db_helper = new dbHelper(getActivity());
        Cursor _cur = db_helper.getSumByDate();

        BarChart barChart = getView().findViewById(R.id.barChart);
        ArrayList<BarEntry> dailyCals = new ArrayList<>();

        if(_cur.getCount()>0){
            int i = 0;
            while (_cur.moveToNext()){
                i++;
                dailyCals.add(new BarEntry(i,_cur.getInt(1)));
            }
        } else{
            dailyCals.add(new BarEntry(1,420));
            dailyCals.add(new BarEntry(2,475));
            dailyCals.add(new BarEntry(3,508));
            dailyCals.add(new BarEntry(4,660));
            dailyCals.add(new BarEntry(5,550));
            dailyCals.add(new BarEntry(6,630));
            dailyCals.add(new BarEntry(7,470));
        }
        _cur.close();

        BarDataSet barDataSet = new BarDataSet(dailyCals,"Daily Calories");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("My Last 7 Days");
        barChart.animateY(2000);

    }
}