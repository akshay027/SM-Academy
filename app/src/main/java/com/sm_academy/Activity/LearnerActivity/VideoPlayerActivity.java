package com.sm_academy.Activity.LearnerActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.sm_academy.R;

public class VideoPlayerActivity extends AppCompatActivity {
    private Button cancelBtn;
    MediaController mediaController;

    private String pos;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        Log.e("onCreate", "onCreate");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mediaController = new MediaController(this);
        cancelBtn = findViewById(R.id.cancelBtn);
        videoView = (VideoView) findViewById(R.id.videoView);
        Intent intent = getIntent();
        pos = intent.getStringExtra("pos");
        //String path="http://www.ted.com/talks/download/video/8584/talk/761";
        videoView.setVisibility(View.VISIBLE);
        String path1 = pos;

        Uri uri = Uri.parse(path1);

        videoView.setVideoURI(uri);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        mediaController.setMediaPlayer(videoView);
        videoView.start();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //startActivity(new Intent(getApplicationContext(), LearnerDashBoardActivity.class));
                overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);

            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onStart", "onStart");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("onPause", "onPause");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume", "onResume");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("onStop", "onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy", "onDestroy");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("onRestoreInstanceState", "onRestoreInstanceState");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("onSaveInstanceState", "onSaveInstanceState");

    }
}
