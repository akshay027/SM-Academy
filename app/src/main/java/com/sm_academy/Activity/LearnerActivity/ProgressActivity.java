/*
package com.sm_academy.Activity.LearnerActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sm_academy.Activity.BaseActivity;
import com.sm_academy.Activity.DefaultActivity.AccountSettingActivity;
import com.sm_academy.R;

import java.util.ArrayList;

public class ProgressActivity extends Fragment {
    private String[] xValues = {"Ak", "Pk", "Dk"};
    private View listenViewColor, writeViewColor, speakViewColor, readViewColor, assignmentViewColor, mockViewColor;
    //private Context context = ProgressActivity.this;

    public static ProgressActivity newInstance() {
        ProgressActivity fragment = new ProgressActivity();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_progress, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.commoncolor)));

        PieChart pieChart = (PieChart) view.findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);

        listenViewColor = view.findViewById(R.id.listenViewColor);
        writeViewColor = view.findViewById(R.id.writeViewColor);
        speakViewColor = view.findViewById(R.id.speakViewColor);
        readViewColor = view.findViewById(R.id.readViewColor);
        assignmentViewColor = view.findViewById(R.id.assignmentViewColor);
        mockViewColor = view.findViewById(R.id.mockViewColor);

        listenViewColor.setBackgroundColor(getActivity().getResources().getColor(R.color.listen));
        writeViewColor.setBackgroundColor(getActivity().getResources().getColor(R.color.write));
        speakViewColor.setBackgroundColor(getActivity().getResources().getColor(R.color.speak));
        readViewColor.setBackgroundColor(getActivity().getResources().getColor(R.color.read));
        assignmentViewColor.setBackgroundColor(getActivity().getResources().getColor(R.color.assignmet));
        mockViewColor.setBackgroundColor(getActivity().getResources().getColor(R.color.mocktest));

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(28.5f, ""));
        entries.add(new PieEntry(13.7f, ""));
        entries.add(new PieEntry(25.0f, ""));
        entries.add(new PieEntry(15.0f, ""));
        entries.add(new PieEntry(7.3f, ""));
        entries.add(new PieEntry(20.8f, ""));
        PieDataSet dataSet = new PieDataSet(entries, "");

        PieData data = new PieData(dataSet);
        // In Percentage term
        data.setValueFormatter(new PercentFormatter());
        // Default value
        //data.setValueFormatter(new DefaultValueFormatter(0));
        pieChart.setData(data);
        //pieChart.setDescription("This is Pie Chart");
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(58f);

        pieChart.setHoleRadius(58f);
        pieChart.setCenterText("Progress");
        pieChart.setCenterTextSize(10);
        pieChart.setDrawEntryLabels(true);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setHoleRadius(50f);
        pieChart.setRotationEnabled(true);
        // dataSet.setColors(new String[]{"#F44141", "#34DA76", "#5FC61B", "#FFC600"}, context);
        dataSet.setColors(new int[]{R.color.listen, R.color.write, R.color.speak, R.color.read, R.color.assignmet, R.color.mocktest}, getActivity());
        //dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        // dataSet.setValueTextSize(13f);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setSliceSpace(2f);
        //dataSet.setDrawValues(false);
        //data.setValueTextColor(Color.DKGRAY);
        //pieChart.setOnChartValueSelectedListener(this);
        // pieChart.setUsePercentValues(true);
        // pieChart.setDrawHoleEnabled(false);
        pieChart.setData(data);
        pieChart.setUsePercentValues(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);
        //pieChart.invalidate();
        // pieChart.animateXY(1400, 1400);
        return view;
    }


}
*/
