package com.sm_academy.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
/*import com.sm_academy.Activity.LabelFormatter;*/
import com.sm_academy.ModelClass.ProgressGraph.GraphSection;
import com.sm_academy.R;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProgressBarAdapter extends RecyclerView.Adapter<ProgressBarAdapter.ViewHolder> {

    private List<GraphSection> arrayList, filterList;
    private Activity activity;
    public static ProgressBarAdapter.OnItemClickListener mItemClickListener;
    boolean isSelectedAll;
    private String className = "";
    private int lastSelectedPosition = -1;
    private String indexx = "";
    private ArrayList<Integer> integerArrayList = new ArrayList<>();
    private ArrayList<String> dataArrayList = new ArrayList<>();
    // private ArrayList<String> datanewlist = new ArrayList<>();
    ArrayList<String> data;

    public ProgressBarAdapter(Activity activity, List<GraphSection> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
        this.filterList = arrayList;
    }

    @Override
    public ProgressBarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_progress_graph, parent, false);
        ProgressBarAdapter.ViewHolder vh = new ProgressBarAdapter.ViewHolder(v);

        return vh;
    }


    @Override
    public void onBindViewHolder(ProgressBarAdapter.ViewHolder holder, final int position) {

        holder.chartName.setText("" + arrayList.get(position).getSection_name());

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        integerArrayList.clear();
        dataArrayList.clear();
        integerArrayList.addAll(arrayList.get(position).getQuestion_paper_scores());
        Log.e("integerArrayList", "-------" + integerArrayList);
        dataArrayList.addAll(arrayList.get(position).getQuestion_paper_names());
        Log.e("dataArrayList", "-------" + dataArrayList);

        for (int i = 0; i < integerArrayList.size(); i++) {
            barEntries.addAll(Collections.singleton(new BarEntry(integerArrayList.get(i), i)));
        }
        BarDataSet barDataSet = new BarDataSet(barEntries, "Progress Chart");
        // barDataSet.setBarBorderWidth(50f);
        //barEntries.addAll(new BarEntry(arrayList));
        String[] str = dataArrayList.toArray(new String[dataArrayList.size()]);
        BarData barData = new BarData(str, barDataSet);
        holder.barGraphForMockTest.setData(barData);
        holder.barGraphForMockTest.setTouchEnabled(true);
        holder.barGraphForMockTest.setScaleEnabled(true);
        holder.barGraphForMockTest.setPinchZoom(true);
        holder.barGraphForMockTest.setDescription("");
        holder.barGraphForMockTest.getLegend().setEnabled(false);
        holder.barGraphForMockTest.setHorizontalScrollBarEnabled(true);
        holder.barGraphForMockTest.setVisibleXRangeMaximum(20);
        holder.barGraphForMockTest.moveViewToX(10);
        XAxis xAxis = holder.barGraphForMockTest.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setSpaceBetweenLabels(6);
        xAxis.setXOffset(12);
        xAxis.setTextSize(10);
        xAxis.setDrawGridLines(false);
        holder.container.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_scale_animation));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView chartName;
        BarChart barGraphForMockTest;
        LinearLayout container;

        public ViewHolder(View v) {
            super(v);
            this.container = v.findViewById(R.id.container);
            this.chartName = v.findViewById(R.id.chartName);

            this.barGraphForMockTest = v.findViewById(R.id.barGraphForMockTest);

            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.topicView:
                    mItemClickListener.ontopicClick(v, getAdapterPosition());
                    break;
                default:
                    mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {

        public void onItemClick(View view, int position);

        public void ontopicClick(View view, int position);

    }

    public void SetOnItemClickListener(ProgressBarAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}


