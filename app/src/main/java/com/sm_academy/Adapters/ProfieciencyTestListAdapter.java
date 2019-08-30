package com.sm_academy.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sm_academy.Activity.DefaultActivity.MainLandingActivity;
import com.sm_academy.Activity.DefaultActivity.ProficiencyTestActivity;
import com.sm_academy.ModelClass.BatchDetails;
import com.sm_academy.ModelClass.MainCoursesDetails;
import com.sm_academy.ModelClass.PTDetails.PTAnswers;
import com.sm_academy.R;

import java.util.ArrayList;
import java.util.List;

import static com.sm_academy.Adapters.CalenderEventAdapter.mItemClickListener;


public class ProfieciencyTestListAdapter extends ArrayAdapter<PTAnswers> {
    private Context context; //context
    private ArrayList<PTAnswers> items; //data source of the list adapter
    private ProficiencyTestActivity activity;
    public static OnItemClickListener mItemClickListener;

    //public constructor
  /*  public ProfieciencyTestListAdapter(Context context, ArrayList<PTAnswers> items) {


    }*/

    public ProfieciencyTestListAdapter(Context context, int resource, ArrayList<PTAnswers> items) {
        super(context, resource, items);
        this.context = context;
        this.items = items;


    }


    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.pte_answer_list, parent, false);
        }

        // get current item to be displayed
        PTAnswers currentItem = (PTAnswers) getItem(position);

        // get the TextView for item name and item description
        questionNoView = convertView.findViewById(R.id.questionNoView);
        questionNo = convertView.findViewById(R.id.questionNo);

        answer1 = convertView.findViewById(R.id.answer1);
        answer0 = convertView.findViewById(R.id.answer0);
        answer2 = convertView.findViewById(R.id.answer2);
        answer3 = convertView.findViewById(R.id.answer3);
       // container = convertView.findViewById(R.id.container);
        radioGroup = convertView.findViewById(R.id.radioGroup);
        // get the reference of ImageView
        questionNoView.setText("Q." + (position + 1) + " : " + currentItem.getQuestion());
        answer0.setText(currentItem.getOption0());
        answer1.setText(currentItem.getOption1());
        answer2.setText(currentItem.getOption2());
        answer3.setText(currentItem.getOption3());
        //container.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_scale_animation));

        answer0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mItemClickListener.onAnswerZeroClick("1", getAdapterPosition());
                //Log.e("data", "0");
                mItemClickListener.onAnswerZeroClick(v, position);
            }
        });
        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("data", "1");
                mItemClickListener.onAnswerOneClick(v, position);
            }
        });
        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("data", "2");
                mItemClickListener.onAnswerTwoClick(v, position);
            }
        });
        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("data", "3");
                mItemClickListener.onAnswerThreeClick(v, position);
            }
        });

        // set logo images

        //courseView.setBackgroundColor(activity.getResources().getColor(R.drawable.bgimg));


        // returns the view for the current row
        return convertView;
    }

    TextView questionNoView, questionNo;
    RadioButton answer0, answer1, answer2, answer3;
    RadioGroup radioGroup;

    /*LinearLayout container;*/

    public interface OnItemClickListener {

        public void onAnswerZeroClick(View view, int position);

        public void onAnswerOneClick(View view, int position);

        public void onAnswerTwoClick(View view, int position);

        public void onAnswerThreeClick(View view, int position);

        //public void onItemClick(View view, int position);

    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}



/*

public class ProfieciencyTestListAdapter extends RecyclerView.Adapter<ProfieciencyTestListAdapter.ViewHolder> {
    String date;
    private ArrayList<PTAnswers> arrayList;
    private Activity activity;
    public static OnItemClickListener mItemClickListener;
    //public static ProfieciencyTestListAdapter.OnItemClickListener mItemClickListener;

    Context context;


    // private List<Integer> arrayList1;

    public ProfieciencyTestListAdapter(Activity activity, ArrayList<PTAnswers> arrayList) {
        this.arrayList = arrayList;
        //  this.arrayList1 = arrayList1;
        this.activity = activity;
        this.context = context;


    }

    @Override
    public ProfieciencyTestListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pte_answer_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.questionNoView.setText("Q." + (position + 1) + " : " + arrayList.get(position).getQuestion());

        holder.answer0.setText(arrayList.get(position).getOption0());
        holder.answer1.setText(arrayList.get(position).getOption1());
        holder.answer2.setText(arrayList.get(position).getOption2());
        holder.answer3.setText(arrayList.get(position).getOption3());

        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup rgp, int checkedId) {
                // TODO Auto-generated method stub
                arrayList.get(position).setCheckedId(rgp.getCheckedRadioButtonId());
            }
        });

        holder.radioGroup.check(arrayList.get(position).getCheckedId());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView questionNoView, questionNo;
        RadioButton answer0, answer1, answer2, answer3;
        RadioGroup radioGroup;

        public ViewHolder(View v) {
            super(v);

            this.questionNoView = v.findViewById(R.id.questionNoView);
            this.questionNo = v.findViewById(R.id.questionNo);
            this.answer0 = v.findViewById(R.id.answer0);
            this.answer1 = v.findViewById(R.id.answer1);
            this.answer2 = v.findViewById(R.id.answer2);
            this.answer3 = v.findViewById(R.id.answer3);
            this.radioGroup = v.findViewById(R.id.radioGroup);

            this.answer0.setOnClickListener(this);
            this.answer1.setOnClickListener(this);
            this.answer2.setOnClickListener(this);
            this.answer3.setOnClickListener(this);
            this.radioGroup.setOnClickListener(this);

            v.setOnClickListener(this);
            v.

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.answer0:
                    mItemClickListener.onAnswerZeroClick("1", getAdapterPosition());
                    break;
                case R.id.answer1:
                    mItemClickListener.onAnswerOneClick("2", getAdapterPosition());
                    break;
                case R.id.answer2:
                    mItemClickListener.onAnswerTwoClick("3", getAdapterPosition());
                    break;
                case R.id.answer3:
                    mItemClickListener.onAnswerThreeClick("4", getAdapterPosition());
                    break;
                default:
                    mItemClickListener.onItemClick("5", getAdapterPosition());
            }
        }
    }


    public interface OnItemClickListener {

        public void onAnswerZeroClick(String view, int position);

        public void onAnswerOneClick(String view, int position);

        public void onAnswerTwoClick(String view, int position);

        public void onAnswerThreeClick(String view, int position);

        public void onItemClick(String view, int position);

    }


    public void SetOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public ArrayList<PTAnswers> getCurrentDada() {
        return this.arrayList;
    }
    public interface onCheckedChanged {
        void onCheckedChanged(int position, boolean value);
    }
}
*/
