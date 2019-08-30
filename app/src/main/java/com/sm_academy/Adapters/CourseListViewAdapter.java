package com.sm_academy.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.sm_academy.Activity.DefaultActivity.MainLandingActivity;
import com.sm_academy.Activity.LearnerActivity.HomeNavigationTabActivity;
import com.sm_academy.ModelClass.DashBoard.DashCourse;
import com.sm_academy.ModelClass.DashBoard.Ielts;
import com.sm_academy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/*
public class CourseListViewAdapter extends BaseAdapter {
    private Context context; //context
    private ArrayList<Ielts> items; //data source of the list adapter
    private MainLandingActivity activity;
    public static CourseListViewAdapter.OnItemClickListener mItemClickListener;

    //public constructor
    public CourseListViewAdapter(Context context, ArrayList<Ielts> items) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.course_listing_list, parent, false);
        }

        // get current item to be displayed
        Ielts currentItem = (Ielts) getItem(position);
        RelativeLayout fullView = convertView.findViewById(R.id.fullView);
        // get the TextView for item name and item description
        TextView courseName = convertView.findViewById(R.id.courseName);
        //   ImageView imageView = convertView.findViewById(R.id.imageView);// get the reference of ImageView
        courseName.setText(currentItem.getTitleizedName()); // set logo images

*/
/*
        Picasso.with(context).load( currentItem.getPhoto()).resize(50, 50).transform(new CircleTransform()).into(imageView);
*//*

        fullView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mItemClickListener.onAnswerZeroClick("1", getAdapterPosition());
                //Log.e("data", "0");
                mItemClickListener.onViewClick(v, position);
            }
        });


        return convertView;
    }

    public interface OnItemClickListener {

        public void onViewClick(View view, int position);



    }

    public void SetOnItemClickListener(CourseListViewAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}
*/


public class CourseListViewAdapter extends RecyclerView.Adapter<CourseListViewAdapter.ViewHolder> {

    private ArrayList<DashCourse> arrayList;
    private Context activity;
    public static OnItemClickListener mItemClickListener;
    private int selectedIndex = -1;

    public CourseListViewAdapter(Context activity, ArrayList<DashCourse> arrayList) {
        this.arrayList = arrayList;

        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_listing_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        DashCourse dashCourse = arrayList.get(position);
        holder.courseName.setText(dashCourse.getName());
        if (selectedIndex == -1) {
            holder.fullView.setBackgroundResource(R.drawable.halfcircle2);
        }
        if (position == selectedIndex) {
            holder.fullView.setBackgroundResource(R.drawable.halfcircle1);
            holder.courseName.setTextColor(Color.WHITE);

        } else {
            holder.fullView.setBackgroundResource(R.drawable.halfcircle2);
            holder.courseName.setTextColor(Color.WHITE);
        }
    }

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView courseName;
        RelativeLayout fullView;

        public ViewHolder(View v) {
            super(v);

            this.courseName = v.findViewById(R.id.courseName);
            this.fullView = v.findViewById(R.id.fullView);

            this.fullView.setOnClickListener(this);

            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.fullView:
                    // imageView.setImageResource(R.drawable.write);
                    mItemClickListener.onViewClick(v, getAdapterPosition());
                default:
                    mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {

        public void onViewClick(View view, int position);

        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public ArrayList<DashCourse> getCurrentDada() {
        return this.arrayList;
    }

}
