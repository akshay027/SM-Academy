package com.sm_academy.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sm_academy.ModelClass.PracticeTest.MockTestList;
import com.sm_academy.ModelClass.ProgressGraph.CourseSummaries;
import com.sm_academy.R;

import java.util.ArrayList;
import java.util.List;

public class CourseSummariesAdapter extends RecyclerView.Adapter<CourseSummariesAdapter.ViewHolder> implements Filterable {
    String date;
    private ArrayList<CourseSummaries> arrayList;
    private Activity activity;
    public static CourseSummariesAdapter.OnItemClickListener mItemClickListener;
    //public static PracticeTestViewListAdapter.OnItemClickListener mItemClickListener;

    Context context;
    private List<CourseSummaries> contactListFiltered;
    //private PracticeTestViewListAdapter.ContactsAdapterListener listener;
    // private List<Integer> arrayList1;

    public CourseSummariesAdapter(Activity activity, ArrayList<CourseSummaries> arrayList, Context context) {
        this.arrayList = arrayList;
        //  this.arrayList1 = arrayList1;
        this.activity = activity;
        this.context = context;

        this.contactListFiltered = arrayList;

    }

    @Override
    public CourseSummariesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_course_summaries_view, parent, false);
        CourseSummariesAdapter.ViewHolder vh = new CourseSummariesAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CourseSummariesAdapter.ViewHolder holder, int position) {


        holder.testsCountView.setText(contactListFiltered.get(position).getNumberOfTests().toString());
        holder.fielsName.setText(contactListFiltered.get(position).getSectionName());
        holder.attemptsView.setText(contactListFiltered.get(position).getAttemptedTestsCount().toString());
        holder.tvProgress.setText(contactListFiltered.get(position).getProgressPercentage().toString() + " %");
        holder.progressBar.setProgress(contactListFiltered.get(position).getProgressPercentage());
        holder.container.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_scale_animation));
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
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
                    List<CourseSummaries> filteredList = new ArrayList<>();
                    for (CourseSummaries row : arrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getAttemptedTestsCount().toString().contains(charString.toLowerCase())
                                || row.getNumberOfTests().toString().contains(charString.toLowerCase())
                                || row.getProgressPercentage().toString().contains(charString.toLowerCase())
                                || row.getSectionName().toString().contains(charString.toLowerCase())) {
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
                contactListFiltered = (ArrayList<CourseSummaries>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView testsCountView, attemptsView, tvProgress, fielsName;
        ProgressBar progressBar;
        LinearLayout container;

        public ViewHolder(View v) {
            super(v);
            this.container = v.findViewById(R.id.container);
            this.testsCountView = v.findViewById(R.id.testsCountView);

            this.attemptsView = v.findViewById(R.id.attemptsView);

            this.tvProgress = v.findViewById(R.id.tvProgress);

            this.fielsName = v.findViewById(R.id.fielsName);

            this.progressBar = v.findViewById(R.id.progressBar);

            // this.scoreView = v.findViewById(R.id.scoreView);

            // this.startView.setOnClickListener(this);
            // this.scoreView.setOnClickListener(this);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    mItemClickListener.onView(view, contactListFiltered.get(getAdapterPosition()));
                }
            });
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                default:
                    mItemClickListener.onView(v, contactListFiltered.get(getAdapterPosition()));
            }
        }
    }

    public interface OnItemClickListener {


        public void onView(View view, CourseSummaries position);


    }

    public void SetOnItemClickListener(CourseSummariesAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}


