package com.sm_academy.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.sm_academy.ModelClass.DashBoard.DashBoardSectionResponse;
import com.sm_academy.ModelClass.DashBoard.DashBoardSections;
import com.sm_academy.ModelClass.DashBoard.DashCourse;
import com.sm_academy.ModelClass.DashBoard.Pte;
import com.sm_academy.R;

import java.util.ArrayList;

/*
public class CourseSubFieldViewAdapter extends BaseAdapter {
    private Context context; //context
    private ArrayList<DashBoardSections> items; //data source of the list adapter


    //public constructor
    public CourseSubFieldViewAdapter(Context context, ArrayList<DashBoardSections> items) {
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
                    inflate(R.layout.course_block_list, parent, false);
        }

        // get current item to be displayed
        DashBoardSections currentItem = (DashBoardSections) getItem(position);

        // get the TextView for item name and item description
        TextView fielsName = convertView.findViewById(R.id.fielsName);
        ImageView imageView = convertView.findViewById(R.id.imageView);// get the reference of ImageView
        fielsName.setText(currentItem.getTitleizedName()); // set logo images

        //Picasso.with(context).load( currentItem.getPhoto()).resize(50, 50).transform(new CircleTransform()).into(imageView);
        CircularProgressBar circularProgressBar = convertView.findViewById(R.id.circularProgressbar);
        circularProgressBar.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(context, R.color.light_gray));
        circularProgressBar.setProgressBarWidth(context.getResources().getDimension(R.dimen.progressBar_dimen));
        circularProgressBar.setBackgroundProgressBarWidth(context.getResources().getDimension(R.dimen.default_stroke_width));
        int animationDuration = 2500;
        int a = currentItem.getPercentage();// 2500ms = 2,5s
        circularProgressBar.setProgressWithAnimation(a, animationDuration);
        if (currentItem.getTitleizedName().equals("Reading")) {

            imageView.setBackgroundResource(R.drawable.circle);
            imageView.setImageResource(R.drawable.books);

        } else if (currentItem.getTitleizedName().equals("Listening")) {

            imageView.setBackgroundResource(R.drawable.circle2);
            imageView.setImageResource(R.drawable.ear);

        } else if (currentItem.getTitleizedName().equals("Writing")) {

            imageView.setBackgroundResource(R.drawable.circle4);
            imageView.setImageResource(R.drawable.write);

        } else if (currentItem.getTitleizedName().equals("Speaking")) {
            imageView.setBackgroundResource(R.drawable.circle3);
            imageView.setImageResource(R.drawable.speak);

        } else {
            imageView.setBackgroundResource(R.drawable.circle3);
            imageView.setImageResource(R.drawable.conversation);
        }
        return convertView;
    }
}
*/


public class CourseSubFieldViewAdapter extends RecyclerView.Adapter<CourseSubFieldViewAdapter.ViewHolder> {

    private ArrayList<DashBoardSections> arrayList;
    private Context context;
    public static OnItemClickListener mItemClickListener;


    public CourseSubFieldViewAdapter(Context context, ArrayList<DashBoardSections> arrayList) {
        this.arrayList = arrayList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_block_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        DashBoardSections currentItem = arrayList.get(position);
        holder.fielsName.setText(currentItem.getTitleizedName());
        holder.testCount.setText("Test Count : " + currentItem.getTestCount());
        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));
        holder.circularProgressBar.setColor(ContextCompat.getColor(context, R.color.green));
        holder.circularProgressBar.setBackgroundColor(ContextCompat.getColor(context, R.color.light_gray));
        holder.circularProgressBar.setProgressBarWidth(context.getResources().getDimension(R.dimen.progressBar_dimen));
        holder.circularProgressBar.setBackgroundProgressBarWidth(context.getResources().getDimension(R.dimen.default_stroke_width));
        int animationDuration = 2500;
        int a = currentItem.getPercentage();// 2500ms = 2,5s
        holder.circularProgressBar.setProgressWithAnimation(a, animationDuration);
        if (currentItem.getTitleizedName().equals("Reading")) {

            holder.imageView.setBackgroundResource(R.drawable.read);
            // holder.imageView.setImageResource(R.drawable.books);
            holder.sideView.setBackgroundResource(R.color.read);

        } else if (currentItem.getTitleizedName().equals("Listening")) {

            holder.imageView.setBackgroundResource(R.drawable.listen);
            //holder.imageView.setImageResource(R.drawable.ear);
            holder.sideView.setBackgroundResource(R.color.listen);

        } else if (currentItem.getTitleizedName().equals("Writing")) {

            holder.imageView.setBackgroundResource(R.drawable.write);
            // holder.imageView.setImageResource(R.drawable.write);
            holder.sideView.setBackgroundResource(R.color.write);

        } else if (currentItem.getTitleizedName().equals("Speaking")) {
            holder.imageView.setBackgroundResource(R.drawable.speak);
            //holder.imageView.setImageResource(R.drawable.speak);
            holder.sideView.setBackgroundResource(R.color.speak);

        } else {
            holder.imageView.setBackgroundResource(R.drawable.speakwritingcircle);
            //holder.imageView.setImageResource(R.drawable.conversation);
            holder.sideView.setBackgroundResource(R.color.spaekandwrite);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView fielsName, testCount;
        ImageView imageView;
        LinearLayout sideView;
        CircularProgressBar circularProgressBar;
        LinearLayout container;

        public ViewHolder(View v) {
            super(v);
            this.container = v.findViewById(R.id.container);
            this.fielsName = v.findViewById(R.id.fielsName);
            this.imageView = v.findViewById(R.id.imageView);
            this.circularProgressBar = v.findViewById(R.id.circularProgressbar);
            this.sideView = v.findViewById(R.id.sideView);
            this.testCount = v.findViewById(R.id.testCount);

            //this.fullView.setOnClickListener(this);
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

    public void SetOnItemClickListener(CourseSubFieldViewAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public ArrayList<DashBoardSections> getCurrentDada() {
        return this.arrayList;
    }

}
