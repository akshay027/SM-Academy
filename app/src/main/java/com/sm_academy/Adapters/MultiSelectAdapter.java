package com.sm_academy.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.sm_academy.Database.PreferenceMangagerForTest;
import com.sm_academy.ModelClass.PracticeTest.PracticeTestStart;
import com.sm_academy.R;

import java.util.List;


public class MultiSelectAdapter extends RecyclerView.Adapter<MultiSelectAdapter.ViewHolder> {

    private List<PracticeTestStart> arrayList, filterList;
    private Activity activity;
    private MultiSelectAdapter.OnItemClickListener mItemClickListener;
    private boolean isSelectedAll;
    private String className = "";
    private int lastSelectedPosition = -1;
    private String abc;
    private boolean indexx;

    public MultiSelectAdapter(Activity activity, List<PracticeTestStart> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
        this.filterList = arrayList;
    }


    @Override
    public MultiSelectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_multi_select_list, parent, false);
        MultiSelectAdapter.ViewHolder vh = new MultiSelectAdapter.ViewHolder(v);

        return vh;
    }


    @Override
    public void onBindViewHolder(MultiSelectAdapter.ViewHolder holder, final int position) {

        Log.e("data", "===" + arrayList);
        //holder.topicView.setText("" + arrayList.get(0));

        holder.topicView.setText("" + arrayList.get(position).getOption());

        if (indexx) {
            if (PreferenceMangagerForTest.getStringFields(activity, abc + arrayList.get(position).getIndex().toString()).equalsIgnoreCase("true")) {
                holder.topicView.setChecked(true);
            } else {
                holder.topicView.setChecked(false);
            }
        } else {
            if (arrayList.get(position).getCheck_box() == 1) {

                holder.topicView.setChecked(true);

            } else if (arrayList.get(position).getCheck_box() == 0) {
                holder.topicView.setChecked(false);
                //  holder.editView.setText("");
            }
        }

        /*if (indexx.contains(String.valueOf(arrayList.get(position).getIndex()))) {
            holder.topicView.setSelected(true);

            // holder.editView.setText();
            //  holder.editView.setText(PreferenceMangagerForTest.getStringFields(activity, String.valueOf(arrayList.get(position).getId())));
        } else {
            holder.topicView.setSelected(false);
        }
        if (arrayList.get(position).getCheck_box() == 1) {

            holder.topicView.setChecked(true);

        } else if (arrayList.get(position).getCheck_box() == 0) {
            holder.topicView.setChecked(false);
            //  holder.editView.setText("");
        }*/


    }

    public void setSelectedIndex(String index, boolean ind) {
        abc = index;
        indexx = ind;
        //notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CheckBox topicView;
        LinearLayout ll;

        public ViewHolder(View v) {
            super(v);

            this.topicView = v.findViewById(R.id.topicView);
            this.ll = v.findViewById(R.id.linearLayoutDecisions);
            // add edittext


            this.topicView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();

                }
            });


            this.topicView.setOnClickListener(this);
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.topicView:
                    mItemClickListener.ontopicItemClick(v, getAdapterPosition());
                    break;
                default:
                    mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {

        public void onItemClick(View view, int position);

        public void ontopicItemClick(View view, int position);

    }

    public void SetOnItemClickListener(MultiSelectAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}

