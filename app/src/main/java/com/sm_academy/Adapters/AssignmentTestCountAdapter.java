package com.sm_academy.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import com.sm_academy.ModelClass.Subscription.Subscription;
import com.sm_academy.R;

import java.util.ArrayList;
import java.util.List;

public class AssignmentTestCountAdapter extends RecyclerView.Adapter<AssignmentTestCountAdapter.ViewHolder> implements Filterable {
    String date;
    private ArrayList<PracticeTestTopicWise> arrayList;
    private Activity activity;
    public static AssignmentTestCountAdapter.OnItemClickListener mItemClickListener;
    //public static PracticeTestViewListAdapter.OnItemClickListener mItemClickListener;

    Context context;
    private List<PracticeTestTopicWise> contactListFiltered;
    //private PracticeTestViewListAdapter.ContactsAdapterListener listener;
    // private List<Integer> arrayList1;

    public AssignmentTestCountAdapter(Activity activity, ArrayList<PracticeTestTopicWise> arrayList, Context context) {
        this.arrayList = arrayList;
        //  this.arrayList1 = arrayList1;
        this.activity = activity;
        this.context = context;

        this.contactListFiltered = arrayList;

    }

    @Override
    public AssignmentTestCountAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_assignment_count_view, parent, false);
        AssignmentTestCountAdapter.ViewHolder vh = new AssignmentTestCountAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AssignmentTestCountAdapter.ViewHolder holder, int position) {

        holder.difficultyView.setText(contactListFiltered.get(position).getDifficultyLevel());
        holder.durationView.setText(contactListFiltered.get(position).getDuration());
        holder.testName.setText(contactListFiltered.get(position).getName());
        holder.questionsView.setText(contactListFiltered.get(position).getNumberOfQuestios().toString());
        //holder.attemptsView.setText(contactListFiltered.get(position).getNumberOfAttempts().toString());
        holder.attemptsListView.setVisibility(View.INVISIBLE);
        holder.btnAnswer.setVisibility(View.GONE);
        holder.container.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_scale_animation));
        if (contactListFiltered.get(position).getAlready_taken().equalsIgnoreCase("true")) {
            holder.btnStart.setVisibility(View.VISIBLE);
            holder.btnStart.setText("Score");
            holder.btnStart.setBackgroundResource(R.drawable.buttonshape);
        } else {
            holder.btnStart.setVisibility(View.VISIBLE);
            holder.btnStart.setText("Start Test");
            holder.btnStart.setBackgroundResource(R.drawable.blue_button_shape);
        }
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
                                || row.getDuration().toLowerCase().contains(charString.toLowerCase())
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
        TextView difficultyView, attemptsView, questionsView, startView, testName, durationView;
        Button btnStart, btnAnswer;
        LinearLayout attemptsListView, container;

        public ViewHolder(View v) {
            super(v);
            this.container = v.findViewById(R.id.container);
            this.btnStart = v.findViewById(R.id.btnStart);
            this.btnAnswer = v.findViewById(R.id.btnAnswer);

            this.durationView = v.findViewById(R.id.durationView);

            this.difficultyView = v.findViewById(R.id.difficultyView);

            this.testName = v.findViewById(R.id.testName);

            this.attemptsListView = v.findViewById(R.id.attemptsListView);

            this.questionsView = v.findViewById(R.id.questionsView);

            //this.scoreView = v.findViewById(R.id.scoreView);
            this.attemptsView = v.findViewById(R.id.attemptsView);
            // this.startView.setOnClickListener(this);
            this.btnStart.setOnClickListener(this);

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
                    mItemClickListener.onButtonClick(v, contactListFiltered.get(getAdapterPosition()));
                    break;
           /*     case R.id.scoreView:
                    mItemClickListener.onscoreClick(v, contactListFiltered.get(getAdapterPosition()));
                    break;*/
                default:
                    mItemClickListener.onView(v, contactListFiltered.get(getAdapterPosition()));
            }
        }
    }

    public interface OnItemClickListener {

        public void onButtonClick(View view, PracticeTestTopicWise position);

        public void onView(View view, PracticeTestTopicWise position);

        public void onscoreClick(View view, PracticeTestTopicWise position);

    }

    public void SetOnItemClickListener(AssignmentTestCountAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}

