package com.sm_academy.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sm_academy.Database.PreferenceMangagerForTest;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.PracticeTest.PracticeTestStart;
import com.sm_academy.R;

import java.util.List;

public class FillBlankAdapter extends RecyclerView.Adapter<FillBlankAdapter.ViewHolder> {

    private List<PracticeTestStart> arrayList, filterList;
    private Activity activity;
    public static FillBlankAdapter.OnItemClickListener mItemClickListener;
    boolean isSelectedAll;
    private String className = "";
    private int lastSelectedPosition = -1;
    private boolean indexx;
    private String abc;

    public FillBlankAdapter(Activity activity, List<PracticeTestStart> arrayList) {
        this.arrayList = arrayList;
        this.activity = activity;
        this.filterList = arrayList;
    }

    @Override
    public FillBlankAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_blank_select_list, parent, false);
        FillBlankAdapter.ViewHolder vh = new FillBlankAdapter.ViewHolder(v);

        return vh;
    }


    @Override
    public void onBindViewHolder(FillBlankAdapter.ViewHolder holder, final int position) {

        Log.e("data", "===" + arrayList);
        //holder.topicView.setText("" + arrayList.get(0));
        //holder.topicNumberView.setText(arrayList.get(position).getIndex());
        if (indexx) {
            holder.topicView.setText(PreferenceMangagerForTest.getStringFields(activity, abc + arrayList.get(position).getIndex().toString()));
            Log.e("indexxqqq", "---" + abc + arrayList.get(position).getIndex().toString());

            Log.e("indexx", "---" + PreferenceMangagerForTest.getStringFields(activity, abc + arrayList.get(position).getIndex().toString()));
        }
    }

    public void setSelectedStringIndex(String index, boolean ind) {
        abc = index;
        indexx = ind;
        //lastSelectedPosition = ind;
        // notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        EditText topicView;
        TextView topicNumberView;

        public ViewHolder(View v) {
            super(v);

            this.topicView = v.findViewById(R.id.topicView);
            this.topicView.setSelection(topicView.getText().length());

            this.topicNumberView = v.findViewById(R.id.topicNumberView);

       /*     this.topicView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();

                }
            });*/

            //this.topicView.setOnClickListener(this);



            this.topicView.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    mItemClickListener.onEditText(getAdapterPosition(), topicView.getText().toString());
                }
            });
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                default:
                    mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {

        public void onItemClick(View view, int position);

        public void onEditText(int position, String S);

    }

    public void SetOnItemClickListener(FillBlankAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}


