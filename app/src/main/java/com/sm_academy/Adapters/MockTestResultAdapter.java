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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sm_academy.ModelClass.PracticeTest.MockResult;
import com.sm_academy.ModelClass.PracticeTest.MockTestList;
import com.sm_academy.R;

import java.util.ArrayList;
import java.util.List;

public class MockTestResultAdapter extends RecyclerView.Adapter<MockTestResultAdapter.ViewHolder> {
    String date;
    private ArrayList<MockResult> arrayList;
    private Activity activity;
    public static MockTestResultAdapter.OnItemClickListener mItemClickListener;
    //public static AssignmentViewListAdapter.OnItemClickListener mItemClickListener;

    Context context;
    private List<MockResult> contactListFiltered;
    //private AssignmentViewListAdapter.ContactsAdapterListener listener;
    // private List<Integer> arrayList1;

    public MockTestResultAdapter(Activity activity, ArrayList<MockResult> arrayList) {
        this.arrayList = arrayList;
        //  this.arrayList1 = arrayList1;
        this.activity = activity;
        this.context = context;

        this.contactListFiltered = arrayList;

    }

    @Override
    public MockTestResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mock_test_result, parent, false);
        MockTestResultAdapter.ViewHolder vh = new MockTestResultAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MockTestResultAdapter.ViewHolder holder, int position) {


        holder.totalQuestionView.setText(contactListFiltered.get(position).getTotalQuestions().toString());


        holder.answerView.setText(contactListFiltered.get(position).getAnswered().toString());
        holder.timeTakeView.setText(contactListFiltered.get(position).getTimeTaken().toString());

        if (TextUtils.isEmpty(contactListFiltered.get(position).getMessage().toString())) {
            holder.wrongLVView.setVisibility(View.VISIBLE);
            holder.scoreLVView.setVisibility(View.VISIBLE);
            holder.correctLVView.setVisibility(View.VISIBLE);
            holder.delayView.setVisibility(View.GONE);
            holder.wrongView.setText(contactListFiltered.get(position).getWrongAnswers().toString());
            holder.scoreView.setText(contactListFiltered.get(position).getScore().toString());
            holder.correctAnswerView.setText(contactListFiltered.get(position).getCorrectAnswers().toString());
        } else {
            holder.delayView.setVisibility(View.VISIBLE);
            holder.wrongLVView.setVisibility(View.GONE);
            holder.scoreLVView.setVisibility(View.GONE);
            holder.correctLVView.setVisibility(View.GONE);
            holder.delayView.setText(contactListFiltered.get(position).getMessage().toString());
        }


        holder.SectionName.setText(contactListFiltered.get(position).getSectionName());
        holder.container.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_scale_animation));

    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView totalQuestionView, delayView, SectionName, scoreView, correctAnswerView, answerView, timeTakeView, wrongView;
        LinearLayout container,correctLVView,wrongLVView,scoreLVView;
        ImageView openLockView, closeLockView;

        public ViewHolder(View v) {
            super(v);

            this.correctLVView = v.findViewById(R.id.correctLVView);
            this.scoreLVView = v.findViewById(R.id.scoreLVView);
            this.wrongLVView = v.findViewById(R.id.wrongLVView);

            this.container = v.findViewById(R.id.container);
            this.correctAnswerView = v.findViewById(R.id.correctAnswerView);
            this.SectionName = v.findViewById(R.id.SectionName);
            this.totalQuestionView = v.findViewById(R.id.totalQuestionView);

            this.answerView = v.findViewById(R.id.answerView);
            this.delayView = v.findViewById(R.id.delayView);

            this.timeTakeView = v.findViewById(R.id.timeTakeView);
            this.wrongView = v.findViewById(R.id.wrongView);
            this.scoreView = v.findViewById(R.id.scoreView);
            this.openLockView = v.findViewById(R.id.openLockView);
            this.closeLockView = v.findViewById(R.id.closeLockView);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    mItemClickListener.onView(view, contactListFiltered.get(getAdapterPosition()));
                }
            });


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

        //  public void onbtnDownLoadClick(View view, Subscription position);


        public void onView(View view, MockResult position);

    }

    public void SetOnItemClickListener(MockTestResultAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    /*public interface ContactsAdapterListener {
        void onContactSelected(StudyMaterialsDetails contact);
    }*/
}

