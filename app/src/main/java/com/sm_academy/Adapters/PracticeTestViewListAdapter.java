package com.sm_academy.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sm_academy.ModelClass.PracticeTest.PreacticeTestSectionTopic;
import com.sm_academy.ModelClass.Subscription.Subscription;
import com.sm_academy.R;

import java.util.ArrayList;
import java.util.List;

public class PracticeTestViewListAdapter extends RecyclerView.Adapter<PracticeTestViewListAdapter.ViewHolder> implements Filterable {
    String date;
    private ArrayList<PreacticeTestSectionTopic> arrayList;
    private Activity activity;
    public static PracticeTestViewListAdapter.OnItemClickListener mItemClickListener;
    //public static PracticeTestViewListAdapter.OnItemClickListener mItemClickListener;

    Context context;
    private List<PreacticeTestSectionTopic> contactListFiltered;
    //private PracticeTestViewListAdapter.ContactsAdapterListener listener;
    // private List<Integer> arrayList1;

    public PracticeTestViewListAdapter(Activity activity, ArrayList<PreacticeTestSectionTopic> arrayList, Context context) {
        this.arrayList = arrayList;
        //  this.arrayList1 = arrayList1;
        this.activity = activity;
        this.context = context;
        this.contactListFiltered = arrayList;

    }

    @Override
    public PracticeTestViewListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_assignment_view, parent, false);
        PracticeTestViewListAdapter.ViewHolder vh = new PracticeTestViewListAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PracticeTestViewListAdapter.ViewHolder holder, int position) {

        holder.tvTestDetails.setText("Number of tests : " + contactListFiltered.get(position).getNoOfTests().toString());
        holder.tvTestName.setText(contactListFiltered.get(position).getTopic());
        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));
        if (contactListFiltered.get(position).getLocked() == true) {
            holder.closeLockView.setVisibility(View.VISIBLE);
            holder.openLockView.setVisibility(View.GONE);
        } else {
            holder.openLockView.setVisibility(View.VISIBLE);
            holder.closeLockView.setVisibility(View.GONE);
        }
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
                    List<PreacticeTestSectionTopic> filteredList = new ArrayList<>();
                    for (PreacticeTestSectionTopic row : arrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match

                        if (row.getTopic().toLowerCase().contains(charString.toLowerCase())
                                || row.getNoOfTests().toString().contains(charString.toLowerCase())) {
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
                contactListFiltered = (ArrayList<PreacticeTestSectionTopic>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTestDetails, tvTestName;
        LinearLayout container;
        ImageView closeLockView, openLockView;

        public ViewHolder(View v) {
            super(v);

            this.tvTestName = v.findViewById(R.id.tvTestName);
            this.closeLockView = v.findViewById(R.id.closeLockView);
            this.openLockView = v.findViewById(R.id.openLockView);
            this.container = v.findViewById(R.id.container);
            this.tvTestDetails = v.findViewById(R.id.tvTestDetails);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    mItemClickListener.onView(view, contactListFiltered.get(getAdapterPosition()));
                }
            });
        }

    }

    public interface OnItemClickListener {

        public void onView(View view, PreacticeTestSectionTopic position);

    }

    public void SetOnItemClickListener(PracticeTestViewListAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}
