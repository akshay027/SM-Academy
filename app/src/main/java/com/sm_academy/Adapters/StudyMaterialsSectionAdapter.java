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

import com.sm_academy.ModelClass.CourseWithSyllabusDetails;
import com.sm_academy.ModelClass.StudyMaterialsSectionTopics.SectionTopicDetails;
import com.sm_academy.R;

import java.util.ArrayList;
import java.util.List;

public class StudyMaterialsSectionAdapter extends RecyclerView.Adapter<StudyMaterialsSectionAdapter.ViewHolder> implements Filterable {
    String date;
    private ArrayList<SectionTopicDetails> arrayList;
    private Activity activity;


    //public static StudyMaterialsSectionAdapter.OnItemClickListener mItemClickListener;

    Context context;
    private List<SectionTopicDetails> contactListFiltered;
    private StudyMaterialsSectionAdapter.ContactsAdapterListener listener;

    public StudyMaterialsSectionAdapter(Activity activity, ArrayList<SectionTopicDetails> arrayList, StudyMaterialsSectionAdapter.ContactsAdapterListener listener, Context context) {
        this.arrayList = arrayList;
        this.activity = activity;
        this.context = context;
        this.listener = listener;
        this.contactListFiltered = arrayList;

    }


    @Override
    public StudyMaterialsSectionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.study_material_section_list, parent, false);
        StudyMaterialsSectionAdapter.ViewHolder vh = new StudyMaterialsSectionAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(StudyMaterialsSectionAdapter.ViewHolder holder, int position) {

        holder.filenameView.setText(contactListFiltered.get(position).getTopic());
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
                    List<SectionTopicDetails> filteredList = new ArrayList<>();
                    for (SectionTopicDetails row : arrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match


                        if (row.getTopic().toLowerCase().contains(charString.toLowerCase())) {
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
                contactListFiltered = (ArrayList<SectionTopicDetails>) filterResults.values;
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
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });


        }

    }

    public interface ContactsAdapterListener {
        void onContactSelected(SectionTopicDetails contact);
    }
}