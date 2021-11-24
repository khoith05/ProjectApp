package com.example.android.projectmanagement;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.projectmanagement.database.DatabaseHelper;
import com.example.android.projectmanagement.database.TaskSQL;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;


public class AnalyticsFragment extends Fragment {

    public long projectid;
    public static final int ANALYTICS_PROJECT_CODE = 123;
    public DatabaseHelper db;
    public List<TaskSQL> taskSQLList;

    public AnalyticsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AnalyticsFragment newInstance(long projectid) {
        AnalyticsFragment fragment = new AnalyticsFragment();
        fragment.projectid = projectid;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    PieChart mChart;
    View mView;
    private String[] xData = {"Trễ hạn", "Đang thực hiện", "Hoàn thành", "Chưa bắt đầu"};
    ArrayList<Integer> yData = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_analytics, container, false);
        db = new DatabaseHelper(getContext());
        taskSQLList = new ArrayList<>();
        taskSQLList.addAll(db.getAllTask(projectid));

        //get task state
        getTaskDataState();

        mChart = mView.findViewById(R.id.pieChart);
        mChart.setUsePercentValues(true);

        //mChart.setDescription("Smartphone market");
        mChart.setDrawHoleEnabled(true);

        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);

        mChart.setRotation(0);
        mChart.setRotationEnabled(true);

        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, Highlight highlight) {
                if (entry == null) {
                    return;
                }

            }

            @Override
            public void onNothingSelected() {

            }
        });

        // add data
        addData();

        // customize legends
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

        return mView;
    }

    private void addData() {
        ArrayList<PieEntry> yVals1 = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int i = 0; i < yData.size(); i++) {
            if (yData.get(i) > 0) {
                yVals1.add(new PieEntry(yData.get(i), xData[i]));
                // add many colors
                if (i == 0) {
                    colors.add(Color.rgb(228, 26, 28));  //late
                } else if (i == 1) {
                    colors.add(Color.BLUE); //processing
                } else if (i == 2) {
                    colors.add(Color.rgb(77, 175, 74));     //complete
                } else {
                    colors.add(Color.rgb(226, 179, 22));          //pening
                }
            }
        }

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "Tổng quan tình hình dự án");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);


        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(16f);
        data.setValueTextColor(Color.WHITE);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();


    }

    public void getTaskDataState() {
        int late = 0, processing = 0, complete = 0, notstart = 0;
        for (int i = 0; i < taskSQLList.size(); i++) {
            if (taskSQLList.get(i).state.equals("late")) {
                late++;
            } else if (taskSQLList.get(i).state.equals("processing")) {
                processing++;
            } else if (taskSQLList.get(i).state.equals("complete")) {
                complete++;
            } else if (taskSQLList.get(i).state.equals("notstart")) {
                notstart++;
            }
        }
        yData.add(late);
        yData.add(processing);
        yData.add(complete);
        yData.add(notstart);
    }
}