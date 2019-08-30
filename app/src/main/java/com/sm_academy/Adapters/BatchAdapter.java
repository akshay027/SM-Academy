/*
package com.sm_academy.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sm_academy.ModelClass.BatchDetails;
import com.sm_academy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BatchAdapter extends RecyclerView.Adapter<BatchAdapter.ViewHolder> implements Filterable {
    String date;
    private ArrayList<BatchDetails> arrayList;
    private Activity activity;

    //public static BatchAdapter.OnItemClickListener mItemClickListener;

    Context context;
    private List<BatchDetails> contactListFiltered;
    private ContactsAdapterListener listener;
    // private List<Integer> arrayList1;

    public BatchAdapter(Activity activity, ArrayList<BatchDetails> arrayList, ContactsAdapterListener listener, Context context) {
        this.arrayList = arrayList;
        //  this.arrayList1 = arrayList1;
        this.activity = activity;
        this.context = context;
        this.listener = listener;
        this.contactListFiltered = arrayList;

    }

    @Override
    public BatchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.batch_list_second_copy, parent, false);
        BatchAdapter.ViewHolder vh = new BatchAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(BatchAdapter.ViewHolder holder, int position) {

   */
/*     for (int i = 0; i < arrayList1.size(); i++) {
            if (contactListFiltered.get(position).getId().equals(arrayList1.get(i))) {
                contactListFiltered.get(position).setFlag("true");
                // Log.e("ye true hai","====");
            }

        }*//*


        if (contactListFiltered.get(position).getAlreadyEnrolled() == true) {
            holder.registerIconView.setVisibility(View.VISIBLE);
            holder.deviderView.setVisibility(View.VISIBLE);

        } else {
            holder.registerIconView.setVisibility(View.GONE);
            holder.deviderView.setVisibility(View.GONE);
        }
        holder.batchView.setText(contactListFiltered.get(position).getName() + " ( " +
                contactListFiltered.get(position).getStartDateEndDateInRangeInReadableFormat() + " )");
        // holder.startdateView.setText(contactListFiltered.get(position).getStartDateEndDateInRangeInReadableFormat());
        //  holder.enddateView.setText(contactListFiltered.get(position).getEndDate());
        holder.seatsView.setText(contactListFiltered.get(position).getSeatsLeft().toString());
        holder.priceView.setText(contactListFiltered.get(position).getPrice());
        holder.sessionHourView.setText(contactListFiltered.get(position).getLiveSessionHoursDisplay().toString());
   */
/*     if (contactListFiltered.get(position).getName()) {
            holder.registerIconView.setVisibility(View.VISIBLE);
        } else {
            holder.registerIconView.setVisibility(View.VISIBLE);
        }*//*


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
                    List<BatchDetails> filteredList = new ArrayList<>();
                    for (BatchDetails row : arrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match


                        if (row.getName().toLowerCase().contains(charString.toLowerCase())
                                || row.getName().toLowerCase().contains(charString.toLowerCase())
                                || row.getPrice().toLowerCase().contains(charString.toLowerCase())
                                || row.getStartDateEndDateInRangeInReadableFormat().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getLiveSessionHoursDisplay().contains(charSequence)) {
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
                contactListFiltered = (ArrayList<BatchDetails>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView batchView, startdateView, enddateView, seatsView, priceView, sessionHourView;
        ImageView registerIconView;
        LinearLayout deviderView;

        public ViewHolder(View v) {
            super(v);

            this.deviderView = v.findViewById(R.id.deviderView);

            this.batchView = v.findViewById(R.id.batchView);
            this.startdateView = v.findViewById(R.id.startdateView);
            this.enddateView = v.findViewById(R.id.enddateView);
            this.seatsView = v.findViewById(R.id.seatsView);
            this.priceView = v.findViewById(R.id.priceView);
            this.sessionHourView = v.findViewById(R.id.sessionHourView);
            this.registerIconView = v.findViewById(R.id.registerIconView);

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
        void onContactSelected(BatchDetails contact);
    }
}
*/
