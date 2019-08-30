package com.sm_academy.Activity.LearnerActivity;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.sm_academy.API.RetrofitAPI;
import com.sm_academy.ModelClass.DashBoard.DashBoardSectionResponse;
import com.sm_academy.ModelClass.OngoingLiveSeesion.OngoingLiveSession;
import com.sm_academy.ModelClass.OngoingLiveSeesion.OngoingLiveSessionResponse;
import com.sm_academy.R;
import com.sm_academy.Utility.UIUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OngoingLiveSessionFragment extends Fragment {
    private Button cancelBtn;
    MediaController mediaController;
    private String pos;
    private VideoView videoView;
    private String path;
    private TextView topicName, nodataText, descriptionView, courseName, trainerName, batchName, timeView, phaseName;
    private ImageView zoomView;
    private RelativeLayout header, footer;
    private LinearLayout fullView, tvNodata;
    private String live_session_id;
    private Button sessionBtn;

    public OngoingLiveSessionFragment() {
        // Required empty public constructor
    }

    public static OngoingLiveSessionFragment newInstance() {
        OngoingLiveSessionFragment fragment = new OngoingLiveSessionFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ongoing_live_session, container, false);

        mediaController = new MediaController(getActivity());

        header = view.findViewById(R.id.header);
        footer = view.findViewById(R.id.footer);
        fullView = view.findViewById(R.id.fullView);
        nodataText = view.findViewById(R.id.nodataText);
        tvNodata = view.findViewById(R.id.tvNodata);
        descriptionView = view.findViewById(R.id.descriptionView);
        topicName = view.findViewById(R.id.topicName);
        courseName = view.findViewById(R.id.courseName);
        trainerName = view.findViewById(R.id.trainerName);
        batchName = view.findViewById(R.id.batchName);
        timeView = view.findViewById(R.id.timeView);
        phaseName = view.findViewById(R.id.phaseName);
        zoomView = view.findViewById(R.id.zoomView);
        sessionBtn = view.findViewById(R.id.sessionBtn);
        videoView = (VideoView) view.findViewById(R.id.videoView);
        //Intent intent = getActivity().getIntent();

        ongoingLiveSessionDetails();
        //String path="http://www.ted.com/talks/download/video/8584/talk/761";

        zoomView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // finish();
                //startActivity(new Intent(getApplicationContext(), LearnerDashBoardActivity.class));
                //overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                fullView.setVisibility(View.GONE);
                header.setVisibility(View.GONE);
                footer.setVisibility(View.GONE);
            }
        });
        sessionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LiveSessionsActivity.class));
            }
        });
        return view;

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void ongoingLiveSessionDetails() {
        try {
            if (UIUtil.isInternetAvailable(getActivity())) {
                UIUtil.startProgressDialog(getActivity(), "Please Wait.. ");

                RetrofitAPI.getInstance(getActivity()).getApi().getongoingLiveSessionDetails(new Callback<OngoingLiveSessionResponse>() {
                    @Override
                    public void success(OngoingLiveSessionResponse object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {
                                UIUtil.stopProgressDialog(getActivity());
                                // Toast.makeText(getActivity(), object.getMessage(), Toast.LENGTH_SHORT).show();
                                if (object.getLiveSession() == null) {
                                    tvNodata.setVisibility(View.VISIBLE);
                                    videoView.setVisibility(View.GONE);
                                    // zoomView.setVisibility(View.GONE);
                                    nodataText.setVisibility(View.GONE);
                                    fullView.setVisibility(View.GONE);
                                } else {

                                    bindData(object.getLiveSession());
                                }
                                // Toast.makeText(getActivity(), object.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                UIUtil.stopProgressDialog(getActivity());
                                Toast.makeText(getActivity(), object.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        try {
                            UIUtil.stopProgressDialog(getActivity());
                            if (error.getMessage().equalsIgnoreCase("401 Unauthorized")) {
                                UIUtil.stopProgressDialog(getActivity());
                                Toast.makeText(getActivity(), "Please check you must be signed into the some other device", Toast.LENGTH_SHORT).show();

                                //logout();
                            } else {
                                Log.e("ups", "===" + error.getMessage());
                                UIUtil.stopProgressDialog(getActivity());
                                Toast.makeText(getActivity(), "Internal Server Error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                Toast.makeText(getActivity(), "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bindData(OngoingLiveSession ongoingLiveSession) {
        fullView.setVisibility(View.VISIBLE);

        phaseName.setMovementMethod(new ScrollingMovementMethod());
        courseName.setMovementMethod(new ScrollingMovementMethod());
        timeView.setMovementMethod(new ScrollingMovementMethod());
        topicName.setMovementMethod(new ScrollingMovementMethod());
        descriptionView.setMovementMethod(new ScrollingMovementMethod());
        trainerName.setMovementMethod(new ScrollingMovementMethod());
        batchName.setMovementMethod(new ScrollingMovementMethod());

        if (TextUtils.isEmpty(ongoingLiveSession.getPhaseName())) {
            phaseName.setText("");
        } else {
            phaseName.setText("Phase : " + ongoingLiveSession.getPhaseName());
        }
        if (TextUtils.isEmpty(ongoingLiveSession.getCourseName())) {
            courseName.setText("");
        } else {
            courseName.setText("Course : " + ongoingLiveSession.getCourseName());
        }
        if (TextUtils.isEmpty(ongoingLiveSession.getBatchName())) {
            batchName.setText("");
        } else {
            batchName.setText("Batch : " + ongoingLiveSession.getBatchName());
        }
        if (TextUtils.isEmpty(ongoingLiveSession.getTrainerName())) {
            trainerName.setText("");
        } else {
            trainerName.setText("Trainer : " + ongoingLiveSession.getTrainerName());
        }
        if (TextUtils.isEmpty(ongoingLiveSession.getTimings())) {
            timeView.setText("");
        } else {
            timeView.setText("Time : " + ongoingLiveSession.getTimings());
        }
        if (TextUtils.isEmpty(ongoingLiveSession.getSectionTopicTopic())) {
            topicName.setText("");
        } else {
            topicName.setText("Topic : " + ongoingLiveSession.getSectionTopicTopic());
        }
        if (TextUtils.isEmpty(ongoingLiveSession.getDescription())) {
            descriptionView.setText("");
        } else {
            descriptionView.setText("Description : " + ongoingLiveSession.getDescription());
        }

        path = ongoingLiveSession.getAttachmentUrl();
        if (TextUtils.isEmpty(ongoingLiveSession.getAttachmentUrl())) {
            tvNodata.setVisibility(View.GONE);
            nodataText.setVisibility(View.VISIBLE);

            videoView.setVisibility(View.GONE);
            //zoomView.setVisibility(View.GONE);
            //  fullView.setVisibility(View.GONE);
        } else {
            tvNodata.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
            // zoomView.setVisibility(View.VISIBLE);
            // fullView.setVisibility(View.VISIBLE);
            nodataText.setVisibility(View.GONE);
            Uri uri = Uri.parse(path);

            videoView.setVideoURI(uri);
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);
            mediaController.setMediaPlayer(videoView);
            videoView.start();
        }

    }
}
