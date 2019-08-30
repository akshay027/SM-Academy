package com.sm_academy.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sm_academy.ModelClass.PaymentHistories.PaymentHistories;
import com.sm_academy.ModelClass.Subscription.Subscription;
import com.sm_academy.R;

import java.util.ArrayList;
import java.util.List;

public class PaymentHistoriesAdapter extends RecyclerView.Adapter<PaymentHistoriesAdapter.ViewHolder> implements Filterable {
    String date;
    private ArrayList<PaymentHistories> arrayList;
    private Activity activity;
    public static PaymentHistoriesAdapter.OnItemClickListener mItemClickListener;
    //public static PaymentHistoriesAdapter.OnItemClickListener mItemClickListener;

    Context context;
    private List<PaymentHistories> contactListFiltered;
    //private PaymentHistoriesAdapter.ContactsAdapterListener listener;
    // private List<Integer> arrayList1;

    public PaymentHistoriesAdapter(Activity activity, ArrayList<PaymentHistories> arrayList, Context context) {
        this.arrayList = arrayList;
        //  this.arrayList1 = arrayList1;
        this.activity = activity;
        this.context = context;

        this.contactListFiltered = arrayList;

    }

    @Override
    public PaymentHistoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_payment_history, parent, false);
        PaymentHistoriesAdapter.ViewHolder vh = new PaymentHistoriesAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PaymentHistoriesAdapter.ViewHolder holder, int position) {

        holder.tvStatus.setText(contactListFiltered.get(position).getStatus());

        if (contactListFiltered.get(position).getStatus().equalsIgnoreCase("Pending")) {

            holder.tvStatus.setTextColor(Color.parseColor("#bed51f"));

        } else if (contactListFiltered.get(position).getStatus().equalsIgnoreCase("Paid")) {

            holder.tvStatus.setTextColor(Color.parseColor("#509e27"));

        } else {

            holder.tvStatus.setTextColor(Color.parseColor("#d51f1f"));

        }
        holder.tvpaymentId.setText(contactListFiltered.get(position).getReceiptNumber());
        holder.tvmodeofpayment.setText(contactListFiltered.get(position).getModeOfPayment());
        holder.tvBatchName.setText(contactListFiltered.get(position).getBatch_name() + " ( " +
                contactListFiltered.get(position).getCourse_name() + " )");
        holder.tvAmount.setText(" ₹ " + contactListFiltered.get(position).getAmount().toString());
        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation));
        holder.tvPaymentFor.setText(contactListFiltered.get(position).getPayment_for());
        holder.tvCreatedAt.setText(contactListFiltered.get(position).getCreatedAt());
       /* holder.tvpaymentId.setText("Payment Id" + contactListFiltered.get(position).getPaymentId().toString() + " ( " +
                contactListFiltered.get(position).getReceiptNumber() + " )");*/

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
                    List<PaymentHistories> filteredList = new ArrayList<>();
                    for (PaymentHistories row : arrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match

                        if (row.getPayment_for().toLowerCase().contains(charString.toLowerCase())
                                || row.getCourse_name().toLowerCase().contains(charString.toLowerCase())
                                || row.getModeOfPayment().toLowerCase().contains(charString.toLowerCase())
                                || row.getBatch_name().toLowerCase().contains(charString.toLowerCase())
                                || row.getAmount().toString().contains(charString.toLowerCase())
                                || row.getReceiptNumber().contains(charString.toLowerCase())
                                || row.getStatus().toLowerCase().contains(charString.toLowerCase())) {
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
                contactListFiltered = (ArrayList<PaymentHistories>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvpaymentId, tvCreatedAt, tvBatchName, tvmodeofpayment, tvAmount, tvStatus, tvPaymentFor;
        ImageView registerIconView;
        LinearLayout deviderView, container;


        public ViewHolder(View v) {
            super(v);
            this.container = v.findViewById(R.id.container);
            this.deviderView = v.findViewById(R.id.deviderView);
            this.tvBatchName = v.findViewById(R.id.tvBatchName);
            this.tvpaymentId = v.findViewById(R.id.tvpaymentId);
            this.tvmodeofpayment = v.findViewById(R.id.tvmodeofpayment);
            this.tvCreatedAt = v.findViewById(R.id.tvCreatedAt);
            this.tvAmount = v.findViewById(R.id.tvAmount);
            this.tvStatus = v.findViewById(R.id.tvStatus);
            this.tvPaymentFor = v.findViewById(R.id.tvPaymentFor);


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

        // public void onbtnDownLoadClick(View view, PaymentHistories position);


        public void onView(View view, PaymentHistories position);

    }

    public void SetOnItemClickListener(PaymentHistoriesAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    /*public interface ContactsAdapterListener {
        void onContactSelected(StudyMaterialsDetails contact);
    }*/
}