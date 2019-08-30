package com.sm_academy.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sm_academy.ModelClass.BatchDetails;
import com.sm_academy.ModelClass.BatchTabDetails.Upcomingbatches;
import com.sm_academy.ModelClass.LiveSession.CompletedSession;
import com.sm_academy.R;

import java.util.ArrayList;
import java.util.List;

public class UpcomingBatchAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private Activity activity;
    private List<Upcomingbatches> arrayList;


    public UpcomingBatchAdapter(Activity activity, List<Upcomingbatches> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return arrayList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        UpcomingBatchAdapter.ViewHolder holder;

        Upcomingbatches completedSession = arrayList.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_upcoming_batch, null);
            holder = new UpcomingBatchAdapter.ViewHolder();
            holder.batchView = (TextView) convertView.findViewById(R.id.batchView);
            holder.seatsView = (TextView) convertView.findViewById(R.id.seatsView);
            holder.sessionHourView = (TextView) convertView.findViewById(R.id.sessionHourView);
            holder.priceView = (TextView) convertView.findViewById(R.id.priceView);
            holder.deviderView =convertView.findViewById(R.id.deviderView);
            holder.registerIconView =  convertView.findViewById(R.id.registerIconView);
          //  holder.sideView = convertView.findViewById(R.id.sideView);
            holder.container= convertView.findViewById(R.id.container);
            //  holder.imageviewdefault = (ImageView) convertView.findViewById(R.id.imageviewdefault);

            convertView.setTag(holder);

        } else {
            holder = (UpcomingBatchAdapter.ViewHolder) convertView.getTag();
        }
        if (completedSession.getAlreadyEnrolled() == true) {
            holder.registerIconView.setVisibility(View.VISIBLE);
            holder.deviderView.setVisibility(View.VISIBLE);

        } else {
            holder.registerIconView.setVisibility(View.GONE);
            holder.deviderView.setVisibility(View.GONE);
        }
        holder.batchView.setText(completedSession.getName() + " ( " +
                completedSession.getStartDateEndDateInRangeInReadableFormat() + " )");
        // holder.startdateView.setText(contactListFiltered.get(position).getStartDateEndDateInRangeInReadableFormat());
        //  holder.enddateView.setText(contactListFiltered.get(position).getEndDate());
        holder.seatsView.setText(completedSession.getSeatsLeft().toString());
        holder.priceView.setText(completedSession.getPrice());
        holder.sessionHourView.setText(completedSession.getLiveSessionHoursDisplay().toString());
        holder.container.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_scale_animation));


        return convertView;
    }

    static class ViewHolder {
        TextView batchView, startdateView, enddateView, seatsView, priceView, sessionHourView;
        ImageView registerIconView;
        LinearLayout deviderView,container;

    }

}
