package com.sm_academy.Activity.LearnerActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;

import com.sm_academy.R;

import java.io.IOException;


public class AudioPlayer extends AppCompatActivity implements MediaPlayer.OnPreparedListener, MediaController.MediaPlayerControl {
    private static final String TAG = "AudioPlayer";

    public static final String AUDIO_FILE_NAME = "audioFileName";

    private MediaPlayer mediaPlayer;
    private MediaController mediaController;
    private String audioFile, description;
    private Button cancelBtn;
    private Handler handler = new Handler();
    private TextView now_playing_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cancelBtn = findViewById(R.id.cancelBtn);
        Intent intent = getIntent();
        audioFile = intent.getStringExtra("pos");
        description = intent.getStringExtra("about");
        // audioFile = this.getIntent().getStringExtra(AUDIO_FILE_NAME);
        now_playing_text = findViewById(R.id.now_playing_text);
        now_playing_text.setText(description);
        if (TextUtils.isEmpty(now_playing_text.getText().toString())) {
            now_playing_text.setVisibility(View.GONE);
        } else {
            now_playing_text.setVisibility(View.VISIBLE);
        }
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //startActivity(new Intent(getApplicationContext(), LearnerDashBoardActivity.class));
                overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);

            }
        });
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaController = new MediaController(this);

        try {
            mediaPlayer.setDataSource(audioFile);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Log.e(TAG, "Could not open file " + audioFile + " for playback.", e);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaController.hide();
        mediaPlayer.stop();
        mediaPlayer.release();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //the MediaController will hide after 3 seconds - tap the screen to make it appear again
        mediaController.show();
        return false;
    }

    //--MediaPlayerControl methods----------------------------------------------------
    public void start() {
        mediaPlayer.start();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public void seekTo(int i) {
        mediaPlayer.seekTo(i);
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public int getBufferPercentage() {
        return 0;
    }

    public boolean canPause() {
        return true;
    }

    public boolean canSeekBackward() {
        return true;
    }

    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
    //--------------------------------------------------------------------------------

    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onPrepared");
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(findViewById(R.id.main_audio_view));

        handler.post(new Runnable() {
            public void run() {
                mediaController.setEnabled(true);
                mediaController.show();
            }
        });
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
}
