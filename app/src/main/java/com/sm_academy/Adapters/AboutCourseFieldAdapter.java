package com.sm_academy.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sm_academy.Activity.DefaultActivity.MainLandingActivity;
import com.sm_academy.ModelClass.AboutCourse.Section;
import com.sm_academy.ModelClass.MainCoursesDetails;
import com.sm_academy.R;

import java.util.ArrayList;

public class AboutCourseFieldAdapter extends BaseAdapter {
    private Context context; //context
    private ArrayList<Section> items; //data source of the list adapter
    private MainLandingActivity activity;

    //public constructor
    public AboutCourseFieldAdapter(Context context, ArrayList<Section> items) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.about_courese_list, parent, false);
        }

        // get current item to be displayed
        Section currentItem = (Section) getItem(position);

        // get the TextView for item name and item description
        TextView descriptionView = convertView.findViewById(R.id.descriptionView);
        TextView batchName = convertView.findViewById(R.id.batchName);// get the reference of ImageView
        // set logo images
        LinearLayout container = convertView.findViewById(R.id.container);
        container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));
        String s = "✓  " + currentItem.getDescription();

        s = s.replace("\n", "\n✓  ");
        descriptionView.setText(s);
        LinearLayout courseView = convertView.findViewById(R.id.courseView);
        //courseView.setBackgroundColor(activity.getResources().getColor(R.drawable.bgimg));
        batchName.setText(currentItem.getName());

        // returns the view for the current row
        return convertView;
    }
}

