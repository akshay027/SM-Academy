package com.sm_academy.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.sm_academy.ModelClass.PracticeTest.PracticeTestStart;
import com.sm_academy.R;

import java.util.List;

public class TrueFalseAdapter extends RecyclerView.Adapter<TrueFalseAdapter.ViewHolder> {

    private List<PracticeTestStart> arrayList, filterList;
    private Activity activity;
    private TrueFalseAdapter.OnItemClickListener mItemClickListener;
    private boolean isSelectedAll;
    private String className = "";
    private int lastSelectedPosition = -1;
    private String indexx = "";

    public TrueFalseAdapter(Activity activity, List<PracticeTestStart> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
        this.filterList = arrayList;
    }


    @Override
    public TrueFalseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_true_false_list, parent, false);
        TrueFalseAdapter.ViewHolder vh = new TrueFalseAdapter.ViewHolder(v);

        return vh;
    }


    @Override
    public void onBindViewHolder(TrueFalseAdapter.ViewHolder holder, final int position) {

        Log.e("data", "===" + arrayList);
        //holder.topicView.setText("" + arrayList.get(0));

    //    holder.topicView.setText("" + arrayList.get(position).getOption());


   /*     if (arrayList.get(position).getCheck_box() == 1) {
            if (indexx.contains(String.valueOf(arrayList.get(position).getIndex()))) {
                holder.topicView.setChecked(true);
                // holder.editView.setText();
                //  holder.editView.setText(PreferencesManger.getStringFields(activity, String.valueOf(arrayList.get(position).getId())));
            } else {
                holder.topicView.setChecked(false);
            }
        } else if (arrayList.get(position).getCheck_box() == 0) {
            holder.topicView.setChecked(false);
            //  holder.editView.setText("");
        }*/

        if (indexx.equalsIgnoreCase("true")) {
            holder.trueView.setChecked(true);
        } else {
            holder.trueView.setChecked(false);
        }
        if (indexx.equalsIgnoreCase("false")) {
            holder.falseView.setChecked(true);
        } else {
            holder.falseView.setChecked(false);
        }
        if (indexx.equalsIgnoreCase("notgiven")) {
            holder.notGivenView.setChecked(true);
        } else {
            holder.notGivenView.setChecked(false);
        }


    }

    public void setSelectedIndex(String index, int ind) {
        indexx = index;
        lastSelectedPosition = ind;
        //notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CheckBox topicView;
        LinearLayout ll;
        RadioGroup rgb;
        RadioButton trueView, falseView, notGivenView;

        public ViewHolder(View v) {
            super(v);
            this.rgb = v.findViewById(R.id.rgb);
            this.trueView = v.findViewById(R.id.trueView);
            this.falseView = v.findViewById(R.id.falseView);
            this.notGivenView = v.findViewById(R.id.notGivenView);
            // add edittext


      /*      this.topicView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();

                }
            });*/


            this.trueView.setOnClickListener(this);
            this.falseView.setOnClickListener(this);
            this.notGivenView.setOnClickListener(this);
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.trueView:
                    mItemClickListener.onTrueClick(v, getAdapterPosition());
                    break;
                case R.id.falseView:
                    mItemClickListener.onFalseClick(v, getAdapterPosition());
                    break;
                case R.id.notGivenView:
                    mItemClickListener.onNotGivenClick(v, getAdapterPosition());
                    break;
                default:
                    mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {

        public void onItemClick(View view, int position);

        public void onTrueClick(View view, int position);

        public void onFalseClick(View view, int position);

        public void onNotGivenClick(View view, int position);
    }

    public void SetOnItemClickListener(TrueFalseAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}


