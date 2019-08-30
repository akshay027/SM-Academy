package com.sm_academy.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sm_academy.Activity.DefaultActivity.MainLandingActivity;
import com.sm_academy.ModelClass.MainCoursesDetails;
import com.sm_academy.R;

import java.util.ArrayList;
import java.util.List;

/*
public class CustomAdapter extends BaseAdapter {
    Context context;
    private ArrayList<MainCoursesDetails> mainCoursesDetailsArrayList;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, ArrayList mainCoursesDetailsArrayList) {
        this.context = applicationContext;
        this.mainCoursesDetailsArrayList = mainCoursesDetailsArrayList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return mainCoursesDetailsArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return mainCoursesDetailsArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.courese_list, null); // inflate the layout
        TextView descriptionView = view.findViewById(R.id.descriptionView);
        TextView textView = view.findViewById(R.id.coursename);// get the reference of ImageView
        descriptionView.setText(mainCoursesDetailsArrayList.get(i).getDescription()); // set logo images
        textView.setText(mainCoursesDetailsArrayList.get(i).getName());
        return view;
    }
}
*/


public class CustomAdapter extends BaseAdapter {
    private Context context; //context
    private ArrayList<MainCoursesDetails> items; //data source of the list adapter
    private MainLandingActivity activity;

    //public constructor
    public CustomAdapter(Context context, ArrayList<MainCoursesDetails> items) {
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
                    inflate(R.layout.courese_list, parent, false);
        }

        // get current item to be displayed
        MainCoursesDetails currentItem = (MainCoursesDetails) getItem(position);

        // get the TextView for item name and item description
        TextView descriptionView = convertView.findViewById(R.id.descriptionView);
        TextView textView = convertView.findViewById(R.id.coursename);// get the reference of ImageView
        descriptionView.setText(currentItem.getDescription()); // set logo images
        LinearLayout courseView = convertView.findViewById(R.id.courseView);

        courseView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));
        //courseView.setBackgroundColor(activity.getResources().getColor(R.drawable.bgimg));
        textView.setText(currentItem.getUpcasedName());

        // returns the view for the current row
        return convertView;
    }
}


/*

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<MainCoursesDetails> arrayList, filterList;
    private Activity activity;
    public static CustomAdapter.OnItemClickListener mItemClickListener;
    boolean isSelectedAll;
    private String className = "";


    public CustomAdapter(Activity activity, ArrayList<MainCoursesDetails> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
        this.filterList = arrayList;
    }


    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.courese_list, parent, false);
        CustomAdapter.ViewHolder vh = new CustomAdapter.ViewHolder(v);

        return vh;
    }

    public void selectAll() {
        Log.e("onClickSelectAll", "yes");
        isSelectedAll = true;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder holder, final int position) {

        MainCoursesDetails mainCoursesDetails = arrayList.get(position);

        holder.descriptionView.setText("" + mainCoursesDetails.getDescription());
        holder.coursename.setText("" + mainCoursesDetails.getName());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView coursename, descriptionView;
        CheckBox checkbox;

        public ViewHolder(View v) {
            super(v);

            this.descriptionView = v.findViewById(R.id.descriptionView);
            this.coursename = v.findViewById(R.id.coursename);


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

    public void SetOnItemClickListener(CustomAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}
*/
