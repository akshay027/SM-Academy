package com.sm_academy.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.sm_academy.Database.PreferenceMangagerForTest;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.PracticeTest.PracticeTestStart;
import com.sm_academy.R;

import java.util.List;

public class SingleSelectAdapter extends RecyclerView.Adapter<SingleSelectAdapter.ViewHolder> {

    private List<PracticeTestStart> arrayList, filterList;
    private Activity activity;
    public static SingleSelectAdapter.OnItemClickListener mItemClickListener;
    boolean isSelectedAll;
    private String className = "";
    private int lastSelectedPosition = -1;
    private String indexx = "";

    public SingleSelectAdapter(Activity activity, List<PracticeTestStart> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
        this.filterList = arrayList;
    }

    @Override
    public SingleSelectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_single_select_list, parent, false);
        SingleSelectAdapter.ViewHolder vh = new SingleSelectAdapter.ViewHolder(v);

        return vh;
    }


    @Override
    public void onBindViewHolder(SingleSelectAdapter.ViewHolder holder, final int position) {

        holder.topicView.setText("" + arrayList.get(position).getOption());
        Log.e("indexxxxxxx", "===" + (PreferencesManger.getStringFields(activity, String.valueOf(indexx)).equals(position)));
        if (indexx.equalsIgnoreCase(String.valueOf(position))) {
            holder.topicView.setChecked(true);
            Log.e("preffile", "===" + "true");
            Log.e("true position", "===" + position);
        } else {
            holder.topicView.setChecked(false);
            Log.e("preffile", "===" + "false");
            Log.e("false position", "===" + position);
        }

    }

    public void setSelectedIndex(String index, int ind) {
        indexx = index;
        lastSelectedPosition = ind;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RadioButton topicView;


        public ViewHolder(View v) {
            super(v);

            this.topicView = v.findViewById(R.id.topicView);


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
                    mItemClickListener.ontopicClick(v, getAdapterPosition());
                    break;
                default:
                    mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {

        public void onItemClick(View view, int position);

        public void ontopicClick(View view, int position);

    }

    public void SetOnItemClickListener(SingleSelectAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}

