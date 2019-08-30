package com.sm_academy.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sm_academy.ModelClass.DashBoard.DashCourse;
import com.sm_academy.ModelClass.Subscription.CourseExtension;
import com.sm_academy.ModelClass.Subscription.Subscription;
import com.sm_academy.R;

import java.util.ArrayList;
import java.util.List;

public class MySubscriptionCourseNameAdapter extends RecyclerView.Adapter<MySubscriptionCourseNameAdapter.ViewHolder> implements Filterable {

    private ArrayList<CourseExtension> arrayList;
    private Context activity;
    public static MySubscriptionCourseNameAdapter.OnItemClickListener mItemClickListener;
    private int selectedIndex = -1;
    private static List<CourseExtension> contactListFiltered;

    public MySubscriptionCourseNameAdapter(Context activity, ArrayList<CourseExtension> arrayList) {
        this.arrayList = arrayList;

        this.contactListFiltered = arrayList;
        this.activity = activity;
    }

    @Override
    public MySubscriptionCourseNameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_my_subscription, parent, false);
        MySubscriptionCourseNameAdapter.ViewHolder vh = new MySubscriptionCourseNameAdapter.ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(MySubscriptionCourseNameAdapter.ViewHolder holder, final int position) {

        CourseExtension courseExtension = contactListFiltered.get(position);
        holder.tvAmount.setText("Amount :         " + " ₹ " + courseExtension.getAmount().toString());
        holder.tvDays.setText("Days Access :   " + courseExtension.getNumberOfDays().toString());

    }

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvDays, tvAmount, btnExtend;


        public ViewHolder(View v) {
            super(v);

            this.tvDays = v.findViewById(R.id.tvDays);
            this.btnExtend = v.findViewById(R.id.btnExtend);
            this.tvAmount = v.findViewById(R.id.tvAmount);

            this.btnExtend.setOnClickListener(this);
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btnExtend:
                    mItemClickListener.onExtendlick(v, contactListFiltered.get(getAdapterPosition()));
                    break;
                default:
                    mItemClickListener.onItemClick(v, contactListFiltered.get(getAdapterPosition()));
            }
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = arrayList;
                } else {
                    List<CourseExtension> filteredList = new ArrayList<>();
                    for (CourseExtension row : arrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match


                        if (row.getAmount().toLowerCase().contains(charString.toLowerCase())
                                || row.getNumberOfDays().toString().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<CourseExtension>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface OnItemClickListener {

        public void onExtendlick(View view, CourseExtension position);

        public void onItemClick(View view, CourseExtension position);
    }

    public void SetOnItemClickListener(MySubscriptionCourseNameAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public ArrayList<CourseExtension> getCurrentDada() {
        return this.arrayList;
    }

}

