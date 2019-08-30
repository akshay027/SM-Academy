package com.sm_academy.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sm_academy.ModelClass.PracticeTest.MockResult;
import com.sm_academy.ModelClass.PracticeTest.MockTestFullScore;
import com.sm_academy.R;

import java.util.ArrayList;
import java.util.List;

public class MockTestScoreSubAdapter extends RecyclerView.Adapter<MockTestScoreSubAdapter.ViewHolder> {
    String date;
    private ArrayList<MockTestFullScore> arrayList;
    private Activity activity;
    public static MockTestScoreSubAdapter.OnItemClickListener mItemClickListener;
    //public static AssignmentViewListAdapter.OnItemClickListener mItemClickListener;
    private int selectedIndex = 0;
    Context context;
    private List<MockTestFullScore> contactListFiltered;
    //private AssignmentViewListAdapter.ContactsAdapterListener listener;
    // private List<Integer> arrayList1;

    public MockTestScoreSubAdapter(Activity activity, ArrayList<MockTestFullScore> arrayList) {
        this.arrayList = arrayList;
        //  this.arrayList1 = arrayList1;
        this.activity = activity;
        this.context = context;

        this.contactListFiltered = arrayList;

    }

    @Override
    public MockTestScoreSubAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mock_test_result_sub_view, parent, false);
        MockTestScoreSubAdapter.ViewHolder vh = new MockTestScoreSubAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MockTestScoreSubAdapter.ViewHolder holder, int position) {

        holder.questionView.setText(contactListFiltered.get(position).getQuestionNumber().toString());
        if (selectedIndex == 0) {
            holder.questionView.setBackgroundResource(R.drawable.halfcircle2);
        }
        if (position == selectedIndex) {
            holder.questionView.setBackgroundResource(R.drawable.halfcircle1);
            holder.questionView.setTextColor(Color.WHITE);

        } else {
            holder.questionView.setBackgroundResource(R.drawable.halfcircle2);
            holder.questionView.setTextColor(Color.WHITE);
        }
        //holder.feedBackView.setText("Total Question : " + contactListFiltered.get(position).getFeedback().toString());

        // holder.scoreView.setText("Score : " + contactListFiltered.get(position).getScore().toString());
        //holder.correctAnswerView.setText("Correct Answer : " + contactListFiltered.get(position).getCorrectAnswer().toString());

   /*     if (contactListFiltered.get(position).getAnswered().toString().equalsIgnoreCase(contactListFiltered.get(position).getCorrectAnswer())) {


            holder.answerView.setText("Answered : " + contactListFiltered.get(position).getAnswered().toString());
            holder.answerView.setTextColor(Color.GREEN);
        } else if (contactListFiltered.get(position).getAnswered().toString().equalsIgnoreCase(contactListFiltered.get(position).getCorrectAnswer())) {


            holder.answerView.setText("Answered : " + contactListFiltered.get(position).getAnswered().toString());
            holder.answerView.setTextColor(Color.GREEN);
        } else {

        }*/

        //holder.container.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_scale_animation));

    }
    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
       // notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView questionView, feedBackView, scoreView, correctAnswerView, answerView;
        LinearLayout container;


        public ViewHolder(View v) {
            super(v);
           // this.container = v.findViewById(R.id.container);
            //this.correctAnswerView = v.findViewById(R.id.correctAnswerView);
            this.questionView = v.findViewById(R.id.questionView);
            // this.feedBackView = v.findViewById(R.id.feedBackView);

            // this.answerView = v.findViewById(R.id.answerView);

            // this.scoreView = v.findViewById(R.id.scoreView);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    mItemClickListener.onView(view, getAdapterPosition());
                }
            });


            /*questionView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    //listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));


                }
            });*/


        }

    }

    public interface OnItemClickListener {

        // public void onbtnOpenClick(View view, StudyMaterialsDetails position);

        //  public void onbtnDownLoadClick(View view, Subscription position);


        public void onView(View view, int position);

    }

    public void SetOnItemClickListener(MockTestScoreSubAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    /*public interface ContactsAdapterListener {
        void onContactSelected(StudyMaterialsDetails contact);
    }*/
}


