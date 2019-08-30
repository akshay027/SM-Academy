package com.sm_academy.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.BatchDetails;
import com.sm_academy.ModelClass.BatchTiming;
import com.sm_academy.ModelClass.BatchTiming;
import com.sm_academy.R;
import com.sm_academy.Utility.Constants;

import java.util.ArrayList;
import java.util.List;

/*
public class CalenderEventAdapter extends BaseAdapter {
    private Context context; //context
    private ArrayList<BatchTiming> items; //data source of the list adapter

    //public constructor
    public CalenderEventAdapter(Context context, ArrayList<BatchTiming> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return items.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.claender_event_list, parent, false);
        }

        // get current item to be displayed
        BatchTiming currentItem = (BatchTiming) getItem(position);

        // get the TextView for item name and item description
        TextView sectiontopicView = convertView.findViewById(R.id.sectiontopicView);
        TextView calenderTitleView = convertView.findViewById(R.id.calenderTitleView);// get the reference of ImageView
        calenderTitleView.setText(currentItem.getTitleForCalendar()); // set logo images
        sectiontopicView.setText(currentItem.getSectionTopicTopic());


        // returns the view for the current row
        return convertView;
    }
}
*/


public class CalenderEventAdapter extends RecyclerView.Adapter<CalenderEventAdapter.ViewHolder> {

    private List<BatchTiming> arrayList, filterList;
    private Activity activity;
    public static CalenderEventAdapter.OnItemClickListener mItemClickListener;
    boolean isSelectedAll;
    private String className = "";


    public CalenderEventAdapter(Activity activity, ArrayList<BatchTiming> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
        this.filterList = arrayList;
    }


    @Override
    public CalenderEventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.claender_event_list, parent, false);
        CalenderEventAdapter.ViewHolder vh = new CalenderEventAdapter.ViewHolder(v);

        return vh;
    }


    @Override
    public void onBindViewHolder(CalenderEventAdapter.ViewHolder holder, final int position) {

        BatchTiming BatchTiming = arrayList.get(position);

        holder.sectiontopicView.setText("" + BatchTiming.getSectionTopicTopic());

        if (PreferencesManger.getStringFields(activity, Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Learner")) {
            holder.calenderTitleView.setText("" + BatchTiming.getTooltipForCalendar());
        } else if (PreferencesManger.getStringFields(activity, Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Trainer")) {
            holder.calenderTitleView.setText("" + BatchTiming.getTooltipForCalendar());
        } else if (PreferencesManger.getStringFields(activity, Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Admin")) {
            holder.calenderTitleView.setText("" + BatchTiming.getTooltipForCalendar());
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView calenderTitleView, sectiontopicView;
        //CheckBox checkbox;

        public ViewHolder(View v) {
            super(v);

            this.sectiontopicView = v.findViewById(R.id.sectiontopicView);
            this.calenderTitleView = v.findViewById(R.id.calenderTitleView);

            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                default:
                    mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {

        public void onItemClick(View view, int position);

    }

    public void SetOnItemClickListener(CalenderEventAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}
