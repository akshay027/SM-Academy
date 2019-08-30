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

import com.sm_academy.ModelClass.CourseWithSyllabusDetails;
import com.sm_academy.ModelClass.StudyMaterialView.StudyMaterialsDetails;
import com.sm_academy.R;

import java.util.ArrayList;
import java.util.List;

public class CourseWithSyllabusAdapter extends RecyclerView.Adapter<CourseWithSyllabusAdapter.ViewHolder> implements Filterable {
    String date;
    private ArrayList<CourseWithSyllabusDetails> arrayList;
    private Activity activity;

    public static CourseWithSyllabusAdapter.OnItemClickListener mItemClickListener;
    //public static CourseWithSyllabusAdapter.OnItemClickListener mItemClickListener;

    Context context;
    private List<CourseWithSyllabusDetails> contactListFiltered;
    // private CourseWithSyllabusAdapter.ContactsAdapterListener listener;

    public CourseWithSyllabusAdapter(Activity activity, ArrayList<CourseWithSyllabusDetails> arrayList, Context context) {
        this.arrayList = arrayList;
        this.activity = activity;
        this.context = context;
        // this.listener = listener;
        this.contactListFiltered = arrayList;

    }

    @Override
    public CourseWithSyllabusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_with_syllabus_list, parent, false);
        CourseWithSyllabusAdapter.ViewHolder vh = new CourseWithSyllabusAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CourseWithSyllabusAdapter.ViewHolder holder, int position) {

        holder.filenameView.setText(contactListFiltered.get(position).getFileName());
        holder.filePath.setText(contactListFiltered.get(position).getAttachment());
        holder.container.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_scale_animation));
        if (TextUtils.isEmpty(contactListFiltered.get(position).getAbout())) {
            holder.discriptionView.setText("");
            holder.discriptionView.setVisibility(View.GONE);
        } else {
            holder.discriptionView.setVisibility(View.VISIBLE);
            holder.discriptionView.setText(contactListFiltered.get(position).getAbout());
        }
        if (contactListFiltered.get(position).getAbout().length() >= 60) {
            holder.showMore.setVisibility(View.VISIBLE);
            //holder.showless.setVisibility(View.VISIBLE);

        } else {
            holder.showMore.setVisibility(View.GONE);
            // holder.showless.setVisibility(View.GONE);
        }
        if (contactListFiltered.get(position).getExtension().equalsIgnoreCase("pdf")) {
            holder.imageView.setBackgroundResource(R.drawable.pdf);
            holder.btnDownLoad.setVisibility(View.VISIBLE);
        } else if (contactListFiltered.get(position).getExtension().equalsIgnoreCase("ppt")) {
            holder.imageView.setBackgroundResource(R.drawable.ppt);
            holder.btnDownLoad.setVisibility(View.VISIBLE);
        } else if (contactListFiltered.get(position).getExtension().equalsIgnoreCase("mp3")) {
            holder.imageView.setBackgroundResource(R.drawable.audio);
            holder.btnDownLoad.setVisibility(View.GONE);
        } else if (contactListFiltered.get(position).getExtension().equalsIgnoreCase("mp4")) {
            holder.imageView.setBackgroundResource(R.drawable.video);
            holder.btnDownLoad.setVisibility(View.GONE);
        } else if (contactListFiltered.get(position).getExtension().equalsIgnoreCase("png") ||
                contactListFiltered.get(position).getExtension().equalsIgnoreCase("jpeg") ||
                contactListFiltered.get(position).getExtension().equalsIgnoreCase("gif") ||
                contactListFiltered.get(position).getExtension().equalsIgnoreCase("jpg")) {
            holder.imageView.setBackgroundResource(R.drawable.image_icon);
            holder.btnDownLoad.setVisibility(View.VISIBLE);
        } else {
            holder.imageView.setBackgroundResource(R.drawable.docs);
            holder.btnDownLoad.setVisibility(View.VISIBLE);
        }

   /*     if (contactListFiltered.get(position).getExtension().equalsIgnoreCase("jpg") ||
                contactListFiltered.get(position).getExtension().equalsIgnoreCase("jpeg") ||
                contactListFiltered.get(position).getExtension().equalsIgnoreCase("gif") ||
                contactListFiltered.get(position).getExtension().equalsIgnoreCase("png")) {
            holder.btnOpen.setVisibility(View.GONE);
        }*/

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
                    List<CourseWithSyllabusDetails> filteredList = new ArrayList<>();
                    for (CourseWithSyllabusDetails row : arrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match

                        if (row.getFileName().toLowerCase().contains(charString.toLowerCase())) {
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
                contactListFiltered = (ArrayList<CourseWithSyllabusDetails>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView filenameView, showMore, filePath, showless, discriptionView, btnOpen;
        ImageView btnDownLoad, imageView;
        LinearLayout container;
        public ViewHolder(View v) {
            super(v);
            this.showMore = v.findViewById(R.id.showMore);
            this.container = v.findViewById(R.id.container);
            this.showless = v.findViewById(R.id.showless);
            this.filePath = v.findViewById(R.id.filePath);
            this.imageView = v.findViewById(R.id.imageView);
            this.filenameView = v.findViewById(R.id.filenameView);
            this.discriptionView = v.findViewById(R.id.discriptionView);
            this.btnOpen = v.findViewById(R.id.btnOpen);
            this.btnDownLoad = v.findViewById(R.id.btnDownLoad);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, contactListFiltered.get(getAdapterPosition()));
                }
            });
      /*      btnOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // mItemClickListener.onAnswerZeroClick("1", getAdapterPosition());
                    //Log.e("data", "0");
                    mItemClickListener.onbtnOpenClick(v, contactListFiltered.get(getAdapterPosition()));
                }
            });*/
            btnDownLoad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Log.e("data", "1");
                    mItemClickListener.onbtnDownLoadClick(v, contactListFiltered.get(getAdapterPosition()));
                }
            });
            showMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Log.e("data", "1");
                    showMore.setVisibility(View.GONE);
                    showless.setVisibility(View.VISIBLE);
                    discriptionView.setMaxLines(Integer.MAX_VALUE);
                    // mItemClickListener.onbtnShowMoreClick(v, contactListFiltered.get(getAdapterPosition()));
                }
            });
            showless.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Log.e("data", "1");
                    showless.setVisibility(View.GONE);
                    showMore.setVisibility(View.VISIBLE);
                    discriptionView.setMaxLines(2);
                    // mItemClickListener.onbtnShowLessClick(v, contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnItemClickListener {

        // public void onbtnOpenClick(View view, CourseWithSyllabusDetails position);

        public void onbtnDownLoadClick(View view, CourseWithSyllabusDetails position);


        public void onItemClick(View view, CourseWithSyllabusDetails position);

    }

    public void SetOnItemClickListener(CourseWithSyllabusAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
/*
    public interface ContactsAdapterListener {
        void onContactSelected(CourseWithSyllabusDetails contact);
    }*/
}