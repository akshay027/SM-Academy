package com.sm_academy.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sm_academy.ModelClass.PracticeTest.PracticeTestTopicWise;
import com.sm_academy.R;

import java.util.ArrayList;
import java.util.List;

public class PracticeTestCountAdapter extends RecyclerView.Adapter<PracticeTestCountAdapter.ViewHolder> implements Filterable {
    String date;
    private ArrayList<PracticeTestTopicWise> arrayList;
    private Activity activity;
    public static PracticeTestCountAdapter.OnItemClickListener mItemClickListener;
    //public static PracticeTestViewListAdapter.OnItemClickListener mItemClickListener;

    Context context;
    private List<PracticeTestTopicWise> contactListFiltered;
    //private PracticeTestViewListAdapter.ContactsAdapterListener listener;
    // private List<Integer> arrayList1;

    public PracticeTestCountAdapter(Activity activity, ArrayList<PracticeTestTopicWise> arrayList, Context context) {
        this.arrayList = arrayList;
        //  this.arrayList1 = arrayList1;
        this.activity = activity;
        this.context = context;

        this.contactListFiltered = arrayList;

    }

    @Override
    public PracticeTestCountAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_practice_count_view, parent, false);
        PracticeTestCountAdapter.ViewHolder vh = new PracticeTestCountAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PracticeTestCountAdapter.ViewHolder holder, int position) {

        holder.difficultyView.setText(contactListFiltered.get(position).getDifficultyLevel());
   /*     if (TextUtils.isEmpty(contactListFiltered.get(position).getDuration()) || contactListFiltered.get(position).getDuration() == null ||
                contactListFiltered.get(position).getDuration().equalsIgnoreCase("null")) {
            holder.durationView.setText("0 mins");
        } else {
            holder.durationView.setText(contactListFiltered.get(position).getDuration());
        }*/
        holder.attemptsListView.setVisibility(View.VISIBLE);
        holder.testName.setText(contactListFiltered.get(position).getName());
        holder.questionsView.setText(contactListFiltered.get(position).getNumberOfQuestios().toString());
        holder.attemptsView.setText(contactListFiltered.get(position).getNumberOfAttempts().toString());
        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));
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
                    List<PracticeTestTopicWise> filteredList = new ArrayList<>();
                    for (PracticeTestTopicWise row : arrayList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getDifficultyLevel().toLowerCase().contains(charString.toLowerCase())
                                || row.getName().toLowerCase().contains(charString.toLowerCase())
                                || row.getNumberOfAttempts().toString().contains(charString.toLowerCase())
                                || row.getNumberOfQuestios().toString().contains(charString.toLowerCase())) {
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
                contactListFiltered = (ArrayList<PracticeTestTopicWise>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView scoreView, attemptsView, difficultyView, questionsView, startView, testName, durationView;
        Button btnStart, btnAnswer;
        LinearLayout attemptsListView,container;

        public ViewHolder(View v) {
            super(v);
            this.container = v.findViewById(R.id.container);
            this.btnStart = v.findViewById(R.id.btnStart);

            this.btnAnswer = v.findViewById(R.id.btnAnswer);

           // this.durationView = v.findViewById(R.id.durationView);

            this.attemptsView = v.findViewById(R.id.attemptsView);

            this.difficultyView = v.findViewById(R.id.difficultyView);

            this.attemptsListView = v.findViewById(R.id.attemptsListView);

            this.testName = v.findViewById(R.id.testName);

            //  this.startView = v.findViewById(R.id.startView);

            this.questionsView = v.findViewById(R.id.questionsView);

            // this.scoreView = v.findViewById(R.id.scoreView);

            this.btnStart.setOnClickListener(this);
            this.btnAnswer.setOnClickListener(this);

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
                case R.id.btnStart:
                    mItemClickListener.onstartClick(v, contactListFiltered.get(getAdapterPosition()));
                    break;
                case R.id.btnAnswer:
                    mItemClickListener.onAnswerClick(v, contactListFiltered.get(getAdapterPosition()));
                    break;
                default:
                    mItemClickListener.onView(v, contactListFiltered.get(getAdapterPosition()));
            }
        }
    }

    public interface OnItemClickListener {

        public void onstartClick(View view, PracticeTestTopicWise position);

        public void onView(View view, PracticeTestTopicWise position);

        public void onAnswerClick(View view, PracticeTestTopicWise position);

    }

    public void SetOnItemClickListener(PracticeTestCountAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}

