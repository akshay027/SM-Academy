package com.sm_academy.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sm_academy.ModelClass.BatchDetails;
import com.sm_academy.ModelClass.StudyMaterialView.StudyMaterialsDetails;
import com.sm_academy.ModelClass.Subscription.Subscription;
import com.sm_academy.R;

import java.util.ArrayList;
import java.util.List;

public class MySubscriptionAdapter extends RecyclerView.Adapter<MySubscriptionAdapter.ViewHolder>{
    String date;
    private ArrayList<Subscription> arrayList;
    private Activity activity;
    public static MySubscriptionAdapter.OnItemClickListener mItemClickListener;
    //public static MySubscriptionAdapter.OnItemClickListener mItemClickListener;
    private int selectedIndex = 0;
    Context context;
    private List<Subscription> contactListFiltered;
    //private MySubscriptionAdapter.ContactsAdapterListener listener;
    // private List<Integer> arrayList1;

    public MySubscriptionAdapter(Activity activity, ArrayList<Subscription> arrayList, Context context) {
        this.arrayList = arrayList;
        //  this.arrayList1 = arrayList1;
        this.activity = activity;
        this.context = context;

        this.contactListFiltered = arrayList;

    }

    @Override
    public MySubscriptionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_listing_list, parent, false);
        MySubscriptionAdapter.ViewHolder vh = new MySubscriptionAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MySubscriptionAdapter.ViewHolder holder, int position) {

        holder.courseName.setText(contactListFiltered.get(position).getBatchName().toString() );

     //   holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));

        if (position == selectedIndex) {
        holder.courseName.setBackgroundResource(R.drawable.halfcircle1);
        holder.courseName.setTextColor(Color.WHITE);

    } else {
        holder.courseName.setBackgroundResource(R.drawable.halfcircle2);
        holder.courseName.setTextColor(Color.WHITE);
    }
}

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView courseName;


        public ViewHolder(View v) {
            super(v);
            this.courseName = v.findViewById(R.id.courseName);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    mItemClickListener.onView(view, getAdapterPosition());
                }
            });

           /* btnExtend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Log.e("data", "1");
                    mItemClickListener.onbtnDownLoadClick(v, getAdapterPosition());
                }
            });*/

          /*  v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });*/


        }

    }

    public interface OnItemClickListener {

        // public void onbtnOpenClick(View view, StudyMaterialsDetails position);

       // public void onbtnDownLoadClick(View view, int position);


        public void onView(View view, int position);

    }

    public void SetOnItemClickListener(MySubscriptionAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    /*public interface ContactsAdapterListener {
        void onContactSelected(StudyMaterialsDetails contact);
    }*/
}