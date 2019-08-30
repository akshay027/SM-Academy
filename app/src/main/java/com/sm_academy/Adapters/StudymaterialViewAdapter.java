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
import com.sm_academy.ModelClass.StudyMaterialsSectionTopics.SectionTopicDetails;
import com.sm_academy.R;

import java.util.ArrayList;
import java.util.List;

public class StudymaterialViewAdapter extends RecyclerView.Adapter<StudymaterialViewAdapter.ViewHolder> implements Filterable {
    String date;
    private ArrayList<StudyMaterialsDetails> arrayList;
    private Activity activity;
    public static StudymaterialViewAdapter.OnItemClickListener mItemClickListener;

    //public static StudymaterialViewAdapter.OnItemClickListener mItemClickListener;

    Context context;
    private List<StudyMaterialsDetails> contactListFiltered;
    // private StudymaterialViewAdapter.ContactsAdapterListener listener;

    public StudymaterialViewAdapter(Activity activity, ArrayList<StudyMaterialsDetails> arrayList, Context context) {
        this.arrayList = arrayList;
        this.activity = activity;
        this.context = context;
        //this.listener = listener;
        this.contactListFiltered = arrayList;

    }

   /* public void noFilter() {
        contactListFiltered = arrayList;
        notifyDataSetChanged();
    }

    public void filterVideo() {
        filterFile("mp4");
    }

    public void filterAudio() {

        filterFile("mp3");
    }


    public void filterImage() {
        filterFile("jpeg");
    }
    public void filterDoc() {

        filterFile("doc");
    }

    public void filterPdf() {
        filterFile("Pdf");
    }

    private void filterFile(String gender) {
        contactListFiltered = new ArrayList<>();
        for (StudyMaterialsDetails doc : arrayList) {
            if (doc.getExtension().equalsIgnoreCase(gender)) {
                contactListFiltered.add(doc);
            }
        }
        notifyDataSetChanged();
    }*/

    @Override
    public StudymaterialViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.study_material_view_list, parent, false);
        StudymaterialViewAdapter.ViewHolder vh = new StudymaterialViewAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(StudymaterialViewAdapter.ViewHolder holder, int position) {

        // holder.filenameView.setText(contactListFiltered.get(position).getAbout());
        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));
        holder.filePath.setText(contactListFiltered.get(position).getAttachment());
        holder.filenameView.setText(contactListFiltered.get(position).getFilename());
        // holder.discriptionView.setText(contactListFiltered.get(position).getAbout());
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


 /*       if (contactListFiltered.get(position).getExtension().equalsIgnoreCase("jpg") ||
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
                    List<StudyMaterialsDetails> filteredList = new ArrayList<>();
                    for (StudyMaterialsDetails row : arrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match


                        if (row.getAttachment().toLowerCase().contains(charString.toLowerCase())) {
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
                contactListFiltered = (ArrayList<StudyMaterialsDetails>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView filenameView, showMore, filePath, discriptionView, btnOpen, showless;
        ImageView btnDownLoad, imageView;
        LinearLayout container;

        public ViewHolder(View v) {
            super(v);
            this.container = v.findViewById(R.id.container);
            this.showMore = v.findViewById(R.id.showMore);
            this.showless = v.findViewById(R.id.showless);
            this.discriptionView = v.findViewById(R.id.discriptionView);
            this.imageView = v.findViewById(R.id.imageView);
            this.filePath = v.findViewById(R.id.filePath);
            this.filenameView = v.findViewById(R.id.filenameView);
            this.btnOpen = v.findViewById(R.id.btnOpen);
            this.btnDownLoad = v.findViewById(R.id.btnDownLoad);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, contactListFiltered.get(getAdapterPosition()));
                }
            });
           /* btnOpen.setOnClickListener(new View.OnClickListener() {
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

        // public void onbtnShowMoreClick(View view, StudyMaterialsDetails position);

        // public void onbtnShowLessClick(View view, StudyMaterialsDetails position);

        public void onbtnDownLoadClick(View view, StudyMaterialsDetails position);


        public void onItemClick(View view, StudyMaterialsDetails position);

    }

    public void SetOnItemClickListener(StudymaterialViewAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    /*public interface ContactsAdapterListener {
        void onContactSelected(StudyMaterialsDetails contact);
    }*/
}