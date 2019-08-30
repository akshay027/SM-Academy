package com.sm_academy.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sm_academy.ModelClass.LiveSession.CompletedSession;
import com.sm_academy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CompletedSessionAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private Activity activity;
    private List<CompletedSession> arrayList;


    public CompletedSessionAdapter(Activity activity, List<CompletedSession> arrayList) {
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

        CompletedSessionAdapter.ViewHolder holder;

        CompletedSession completedSession = arrayList.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_completed_session, null);
            holder = new CompletedSessionAdapter.ViewHolder();
            holder.tvTimings = (TextView) convertView.findViewById(R.id.tvtimings);
            holder.tvSection = (TextView) convertView.findViewById(R.id.tvSection);
            holder.tvBatch = (TextView) convertView.findViewById(R.id.tvBatch);
            holder.tvCourse = (TextView) convertView.findViewById(R.id.tvCourse);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            holder.tvTopic = (TextView) convertView.findViewById(R.id.tvTopic);
            holder.sideView = convertView.findViewById(R.id.sideView);
            holder.container= convertView.findViewById(R.id.container);
            //  holder.imageviewdefault = (ImageView) convertView.findViewById(R.id.imageviewdefault);

            convertView.setTag(holder);

        } else {
            holder = (CompletedSessionAdapter.ViewHolder) convertView.getTag();
        }
        /*Picasso.with(context).load( defaulterDatamodel.getPhoto()).resize(50, 50)
                .transform(new CircleTransform()).into(holder.imageviewdefault);*/
        holder.tvTimings.setText(("" + completedSession.getTimings()));
        holder.tvSection.setText("" + completedSession.getSectionName() + "  |  " + completedSession.getSectionTopicTopic());
        // holder.tvBatch.setText(("" + completedSession.getBatchName()));
        holder.tvCourse.setText("" + completedSession.getBatchName() + "" + " ( " + completedSession.getCourseName() + ") ");
        holder.tvDate.setText(("" + completedSession.getSessionDateReadableFormat()));
        //holder.tvTopic.setText("" + completedSession.getSectionTopicTopic());
        /*        holder.tv_paymentfor.setText(("" + defaulterDatamodel.getPaymentFor()));*/
        holder.container.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_scale_animation));

        if (completedSession.getSectionName().equals("Reading")) {


            holder.sideView.setBackgroundResource(R.color.read);

        } else if (completedSession.getSectionName().equals("Listening")) {


            holder.sideView.setBackgroundResource(R.color.listen);

        } else if (completedSession.getSectionName().equals("Writing")) {

            holder.sideView.setBackgroundResource(R.color.write);

        } else if (completedSession.getSectionName().equals("Speaking")) {

            holder.sideView.setBackgroundResource(R.color.speak);

        } else if (completedSession.getSectionName().equals("Speaking And Writing")) {

            holder.sideView.setBackgroundResource(R.color.spaekandwrite);

        } else if (completedSession.getSectionName().equals("Analytical Writing")) {

            //holder.sideView.setBackgroundResource(R.color.speak);

        } else if (completedSession.getSectionName().equals("Verbal Reasoning")) {

            // holder.sideView.setBackgroundResource(R.color.speak);

        } else {

            //holder.sideView.setBackgroundResource(R.color.spaekandwrite);
        }


        return convertView;
    }

    static class ViewHolder {
        TextView tvTimings, tvSection, tvBatch, tvCourse;
        TextView tvDate;
        TextView tvTopic;
        LinearLayout sideView,container;
        //ImageView imageviewdefault;

    }

}

