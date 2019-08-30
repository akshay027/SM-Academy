/*
package com.sm_academy.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.sm_academy.Activity.DefaultActivity.MainLandingActivity;
import com.sm_academy.ModelClass.MainCoursesDetails;
import com.sm_academy.R;

import java.util.ArrayList;

public class DynamicAdapter extends BaseAdapter {
    private Context context; //context
    private ArrayList<MainCoursesDetails> items; //data source of the list adapter
    private MainLandingActivity activity;
    private CourseListViewAdapter courseListViewAdapter;

    //public constructor
    public DynamicAdapter(Context context, ArrayList<MainCoursesDetails> items) {
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

    @SuppressLint("ResourceType")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.ielts_list, parent, false);
        }

        // get current item to be displayed
        MainCoursesDetails currentItem = (MainCoursesDetails) getItem(position);

        // get the TextView for item name and item description
        TextView coursename = convertView.findViewById(R.id.coursename);
        GridView secondView = convertView.findViewById(R.id.secondView);// get the reference of ImageView
        coursename.setText(currentItem.getDescription()); // set logo images
       // courseListViewAdapter = new CourseListViewAdapter(this, items);
        secondView.setAdapter(courseListViewAdapter);

        return convertView;
    }
}
*/
