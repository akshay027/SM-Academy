package com.sm_academy.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sm_academy.ModelClass.PracticeTest.MockTestList;
import com.sm_academy.ModelClass.StudyMaterialsSectionTopics.StudyMaterialsPhase;
import com.sm_academy.R;

import java.util.ArrayList;
import java.util.List;

public class StudyMaterialsPhaseAdapter extends RecyclerView.Adapter<StudyMaterialsPhaseAdapter.ViewHolder> implements Filterable {
    String date;
    private ArrayList<StudyMaterialsPhase> arrayList;
    private Activity activity;
    public static StudyMaterialsPhaseAdapter.OnItemClickListener mItemClickListener;
    //public static AssignmentViewListAdapter.OnItemClickListener mItemClickListener;

    Context context;
    private List<StudyMaterialsPhase> contactListFiltered;
    //private AssignmentViewListAdapter.ContactsAdapterListener listener;
    // private List<Integer> arrayList1;

    public StudyMaterialsPhaseAdapter(Activity activity, ArrayList<StudyMaterialsPhase> arrayList, Context context) {
        this.arrayList = arrayList;
        //  this.arrayList1 = arrayList1;
        this.activity = activity;
        this.context = context;

        this.contactListFiltered = arrayList;

    }

    @Override
    public StudyMaterialsPhaseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_study_material_view, parent, false);
        StudyMaterialsPhaseAdapter.ViewHolder vh = new StudyMaterialsPhaseAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(StudyMaterialsPhaseAdapter.ViewHolder holder, int position) {


        holder.filenameView.setText(contactListFiltered.get(position).getName());
        // holder.fileCount.setText(contactListFiltered.get(position).ge());
        if (contactListFiltered.get(position).getLocked() == true) {
            holder.closeLockView.setVisibility(View.VISIBLE);
            holder.openLockView.setVisibility(View.GONE);
        } else {
            holder.openLockView.setVisibility(View.VISIBLE);
            holder.closeLockView.setVisibility(View.GONE);
        }
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
                    List<StudyMaterialsPhase> filteredList = new ArrayList<>();
                    for (StudyMaterialsPhase row : arrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match

                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
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
                contactListFiltered = (ArrayList<StudyMaterialsPhase>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView filenameView, fileCount;
        LinearLayout container;
        ImageView openLockView, closeLockView;

        public ViewHolder(View v) {
            super(v);
            this.openLockView = v.findViewById(R.id.openLockView);
            this.closeLockView = v.findViewById(R.id.closeLockView);
            this.filenameView = v.findViewById(R.id.filenameView);
            this.fileCount = v.findViewById(R.id.fileCount);
            this.container = v.findViewById(R.id.container);

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


        public void onView(View view, StudyMaterialsPhase position);

    }

    public void SetOnItemClickListener(StudyMaterialsPhaseAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    /*public interface ContactsAdapterListener {
        void onContactSelected(StudyMaterialsDetails contact);
    }*/
}

