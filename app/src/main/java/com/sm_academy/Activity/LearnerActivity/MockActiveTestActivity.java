package com.sm_academy.Activity.LearnerActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.devlomi.record_view.RecordView;
import com.sm_academy.API.RetrofitAPI;
import com.sm_academy.API.RetrofitAPIInterface;
import com.sm_academy.Activity.DefaultActivity.MainLandingActivity;
import com.sm_academy.Adapters.AssignmentTestCountAdapter;
import com.sm_academy.Adapters.FillBlankAdapter;
import com.sm_academy.Adapters.MockTestResultAdapter;
import com.sm_academy.Adapters.MultiSelectAdapter;
import com.sm_academy.Adapters.SingleSelectAdapter;
import com.sm_academy.Adapters.TrueFalseAdapter;
import com.sm_academy.Database.PreferenceMangagerForTest;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.CommonResponse;
import com.sm_academy.ModelClass.PracticeTest.MockResult;
import com.sm_academy.ModelClass.PracticeTest.MockTestList;
import com.sm_academy.ModelClass.PracticeTest.MockTestResultResponse;
import com.sm_academy.ModelClass.PracticeTest.MockTestStartResponse;
import com.sm_academy.ModelClass.PracticeTest.MockTestSubmitResponse;
import com.sm_academy.ModelClass.PracticeTest.PracticeTest;
import com.sm_academy.ModelClass.PracticeTest.PracticeTestResult;
import com.sm_academy.ModelClass.PracticeTest.PracticeTestStart;
import com.sm_academy.ModelClass.PracticeTest.PracticeTestStartResponse;
import com.sm_academy.ModelClass.PracticeTest.PracticeTestTopicWiseResponse;
import com.sm_academy.R;
import com.sm_academy.Utility.Constants;
import com.sm_academy.Utility.UIUtil;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MockActiveTestActivity extends AppCompatActivity {
    private WebView tv_question;
    private TextView tv_Section, countDownView;
    //  private TextView showMore, showlessforDesc, showMoreforDesc, showless;
    private Button descriptionBtn, sectionDescriptionBtn, passageBtn, cancelBtn;
    private RecyclerView optionView;
    private LinearLayout scoreCardView;
    private TextView sectionQuesBtn, wrongAnswerView, timeTakenView;
    private Button btnDoneTest;
    private LinearLayout audioView, SecionWiseView, buttonView;
    String timeleftformated;
    private WebView descriptionView;
    private ImageView nextBtn, prevBtn, submitBtn;
    private TrueFalseAdapter trueFalseAdapter;
    private FillBlankAdapter fillBlankAdapter;
    private MultiSelectAdapter multiSelectAdapter;
    private SingleSelectAdapter singleSelectAdapter;


    private Button b1, b2, b3, b4;
    private ImageView iv;
    private MediaPlayer mediaPlayer;

    private double startTime = 0;
    private double finalTime = 0;
    ProgressDialog mediaPlayerLoadingBar;
    private Handler myHandler = new Handler();
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekbar;
    private TextView tx1, tx2, tx3;
    public static int oneTimeOnly = 0;
    String course_id, duration, duration_taken, mock_test_id;
    //  private ProgressBar progressBar;

    private int itempos;
    //  private String answer;

    private ArrayList<PracticeTest> mainResponseArrayList;
    private ArrayList<PracticeTest> optionArrayList;
    private ArrayList<PracticeTest> answerArrayList;

    private static final String TAG = MockActiveTestActivity.class.getSimpleName();

    private ArrayList<PracticeTest> questionList;

    public HashMap<String, String> map = new HashMap();
    private HashMap<String, String> mapfile = new HashMap<>();
    private int totaltime, question_number, optionNo;
    private long timeleft;
    private CountDownTimer countDownTimer;
    private long mtimeleftinmiles;
    private ArrayList<PracticeTestStart> temporaryOptionArrayList, optionPositionArraylist;
    private String time_taken, selectItem = "", fileString, mapstring, question_paper_position, last_question = "false", question_paper_id, section_name, section_position, mock_id, submission_id;
    int hour, min, sec, i, valueforsection, valueforaudio;
    private LinearLayout audioallBtnView;
    private RelativeLayout resultview;

    boolean next = true, prev, timercount;

    private boolean temp = true, qwe = true, testfinshed = false;

    private RelativeLayout header;
    private LinearLayout speakView, linkView;
    private TextView audioLocation, sectioNameView;
    private Button playButton, stopButton;
    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder;
    Random random;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer1;
    File file;
    Button stopRecordButton, recordButton;
    RecordView recordView;
    private Handler mHandler = new Handler();
    TextView textviewone;
    int counter = 0;
    private String question_id, mock_test_cat_id;
    ProgressDialog progressDialog;

    private ImageView imageFile;
    private ProgressBar imageprogress;
    private RelativeLayout imageView;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private GestureDetector gestureDetector;
    private MockTestResultAdapter mockTestResultAdapter;
    private ArrayList mockTestResultViewArrayList;
    private RecyclerView mockTestView;
    private TextView tv_nodata, recordtime;
    private boolean submittest = false,timetakestatus=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_active_test);
        Log.e("onCreate", "onCreate");
        Intent intent = getIntent();
        // course_id = intent.getStringExtra("id");
        //sectionName = intent.getStringExtra("section_name");
        mock_test_id = intent.getStringExtra("mock_test_id");
        Log.e("mock_test_id", "mock_test_id-" + mock_test_id);
        duration = intent.getStringExtra("duration");
        duration = duration.replace(" hrs", "");
        //String time = "01:02:30"; //mm:ss
        String[] units = duration.split(":"); //will break the string up into an array
        int hour = Integer.parseInt(units[0]);
        int minutes = Integer.parseInt(units[1]); //first element
        int seconds = Integer.parseInt(units[2]); //second element
        int duration = (hour * 3600) + (60 * minutes) + seconds;
        Log.e("time", "====" + duration);
        mockTestResultViewArrayList = new ArrayList();

        mockTestView = findViewById(R.id.mockTestView);
        tv_nodata = findViewById(R.id.tv_nodata);
        recordtime = findViewById(R.id.recordtime);
        // responseView = findViewById(R.id.responseView);
        buttonView = findViewById(R.id.buttonView);
        SecionWiseView = findViewById(R.id.SecionWiseView);

        sectioNameView = findViewById(R.id.sectioNameView);

        totaltime = Integer.parseInt(String.valueOf(duration));
        timeleft = Integer.parseInt(String.valueOf(duration)) * 1000;

        mtimeleftinmiles = timeleft;
        audioallBtnView = findViewById(R.id.audioallBtnView);
        header = findViewById(R.id.header);
        audioView = findViewById(R.id.audioView);

        descriptionBtn = findViewById(R.id.descriptionBtn);
        passageBtn = findViewById(R.id.passageBtn);

        sectionDescriptionBtn = findViewById(R.id.sectionDescriptionBtn);
        sectionQuesBtn = findViewById(R.id.sectionQuesBtn);
        linkView = findViewById(R.id.linkView);

        mainResponseArrayList = new ArrayList<>();
        optionArrayList = new ArrayList<>();
        answerArrayList = new ArrayList<>();
        temporaryOptionArrayList = new ArrayList<>();
        optionPositionArraylist = new ArrayList<>();
        questionList = new ArrayList<>();

        imageView = findViewById(R.id.imageView);
        imageFile = findViewById(R.id.imageFile);
        imageprogress = findViewById(R.id.imageprogress);


        audioLocation = findViewById(R.id.audioLocation);
        speakView = findViewById(R.id.SpeakView);
        recordView = findViewById(R.id.record_view);
        recordButton = findViewById(R.id.recordButton);
        //saveButton = findViewById(R.id.saveButton);
        playButton = findViewById(R.id.playButton);
        stopButton = findViewById(R.id.stopButton);
        //stopRecordButton.setRecordView(stopRecordButton);
        //recordButton.setListenForRecord(true);

        stopRecordButton = findViewById(R.id.stopRecordButton);
        resultview = findViewById(R.id.resultview);
        // scoreCardView = findViewById(R.id.scoreCardView);
        btnDoneTest = findViewById(R.id.btnDoneTest);
        // percentageView = findViewById(R.id.percentageView);
        // correctAnswerView = findViewById(R.id.correctAnswerView);
        //totalQuestionView = findViewById(R.id.totalQuestionView);
        timeTakenView = findViewById(R.id.timeTakenView);
        //  wrongAnswerView = findViewById(R.id.wrongAnswerView);

      /*  showMore = findViewById(R.id.showMore);
        showless = findViewById(R.id.showless);
        showMoreforDesc = findViewById(R.id.showMoreforDesc);
        showlessforDesc = findViewById(R.id.showlessforDesc);*/

        optionView = findViewById(R.id.optionView);
        countDownView = findViewById(R.id.countDownView);
        //   optionsecondView = findViewById(R.id.optionsecondView);

        nextBtn = findViewById(R.id.nextBtn);
        prevBtn = findViewById(R.id.prevBtn);
        submitBtn = findViewById(R.id.submitBtn);

        tv_question = findViewById(R.id.tv_question);
        tv_Section = findViewById(R.id.tv_Section);

        optionView.setHasFixedSize(true);
        optionView.setLayoutManager(new LinearLayoutManager(this));
        PreferenceMangagerForTest.clearPreferences(getApplicationContext());

        countDownView.setVisibility(View.VISIBLE);
        audioallBtnView.setVisibility(View.GONE);

        startPracticeTest();

        mockTestView.setHasFixedSize(true);
        mockTestView.setLayoutManager(new LinearLayoutManager(this));
        mockTestResultAdapter = new MockTestResultAdapter(this, mockTestResultViewArrayList);
        mockTestView.setAdapter(mockTestResultAdapter);
        mockTestResultAdapter.notifyDataSetChanged();

     /*   showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("data", "1");
                showMore.setVisibility(View.GONE);
                showless.setVisibility(View.VISIBLE);
                tv_question.setMaxLines(Integer.MAX_VALUE);
                // mItemClickListener.onbtnShowMoreClick(v, contactListFiltered.get(getAdapterPosition()));
            }
        });

        showless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("data", "1");
                showless.setVisibility(View.GONE);
                showMore.setVisibility(View.VISIBLE);
                tv_question.setMaxLines(1);
                // mItemClickListener.onbtnShowLessClick(v, contactListFiltered.get(getAdapterPosition()));
            }
        });
        showMoreforDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("data", "1");
                showMoreforDesc.setVisibility(View.GONE);
                showlessforDesc.setVisibility(View.VISIBLE);
                tv_Section.setMaxLines(Integer.MAX_VALUE);
                // mItemClickListener.onbtnShowMoreClick(v, contactListFiltered.get(getAdapterPosition()));
            }
        });

        showlessforDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("data", "1");
                showlessforDesc.setVisibility(View.GONE);
                showMoreforDesc.setVisibility(View.VISIBLE);
                tv_Section.setMaxLines(1);
                // mItemClickListener.onbtnShowLessClick(v, contactListFiltered.get(getAdapterPosition()));
            }
        });*/
        btnDoneTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), LearnerDashBoardActivity.class));
                Intent intent = new Intent(getApplicationContext(), MockTestCountActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //intent.setAction(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("mock_test_id", mock_test_cat_id);
                //intent.putExtra("section_name", sectionName);
                startActivity(intent);
                finish();
            }
        });

        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);

        tx1 = (TextView) findViewById(R.id.textView2);
        tx2 = (TextView) findViewById(R.id.textView3);
        tx3 = (TextView) findViewById(R.id.textView4);
        tx3.setText("Song.mp3");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mediaPlayer = new MediaPlayer();

     /*   try {
            //  mediaPlayer.setDataSource(audioFile);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            // Log.e(TAG, "Could not open file " + audioFile + " for playback.", e);
        }*/
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        seekbar.setClickable(true);
        seekbar.setVisibility(View.GONE);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                //Toast.makeText(getApplicationContext(),"seekbar progress: "+progress, Toast.LENGTH_SHORT).show();
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Toast.makeText(getApplicationContext(),"seekbar touch started!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //  Toast.makeText(getApplicationContext(),"seekbar touch stopped!", Toast.LENGTH_SHORT).show();
            }
        });
        // b2.setEnabled(false);

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Playing sound", Toast.LENGTH_SHORT).show();
                mediaPlayer.start();

                finalTime = mediaPlayer.getDuration();
                startTime = mediaPlayer.getCurrentPosition();

                if (oneTimeOnly == 0) {
                    seekbar.setMax((int) finalTime);
                    oneTimeOnly = 1;
                }

                tx2.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        finalTime)))
                );

                tx1.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        startTime)))
                );

                seekbar.setProgress((int) startTime);
                myHandler.postDelayed(UpdateSongTime, 100);

                b2.setVisibility(View.VISIBLE);
                b3.setVisibility(View.GONE);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Pausing sound", Toast.LENGTH_SHORT).show();
                mediaPlayer.pause();

                b2.setVisibility(View.GONE);
                b3.setVisibility(View.VISIBLE);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int) startTime;

                if ((temp + forwardTime) <= finalTime) {
                    startTime = startTime + forwardTime;
                    mediaPlayer.seekTo((int) startTime);
                    Toast.makeText(getApplicationContext(), "You have Jumped forward 5 seconds", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot jump forward 5 seconds", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int) startTime;

                if ((temp - backwardTime) > 0) {
                    startTime = startTime - backwardTime;
                    mediaPlayer.seekTo((int) startTime);
                    Toast.makeText(getApplicationContext(), "You have Jumped backward 5 seconds", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot jump backward 5 seconds", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            tx1.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekbar.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
    };

    private void startPracticeTest() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", mock_test_id);
                //params.put("auth_token", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_TOKEN));

                RetrofitAPI.getInstance(this).getApi().getMockStartTest(params, new Callback<MockTestStartResponse>() {
                    @Override
                    public void success(MockTestStartResponse object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {
                                try {
                                    PreferenceMangagerForTest.clearPreferences(MockActiveTestActivity.this);
                                    // generateDataList(response.body());
                                    section_name = "";
                                    submission_id = object.getSubmissionId().toString();
                                    question_number = object.getQuestionNumber();
                                    optionNo = object.getQuestionNumber();
                                    section_position = object.getSectionPosition().toString();
                                    section_name = object.getSectionName().toString();
                                    section_name = section_name.toUpperCase();
                                    question_paper_position = object.getQuestionPaperPosition().toString();
                                    mock_id = object.getId();
                                    question_paper_id = object.getQuestion_paper_id();

                                    mainResponseArrayList.clear();
                                    optionArrayList.clear();
                                    mainResponseArrayList.addAll(object.getTests());

                                    //secondResponseArrayList.addAll(mainResponseArrayList);
                                    for (int i = 0; i < mainResponseArrayList.size(); i++) {
                                        try {
                                            optionArrayList.add(mainResponseArrayList.get(i));
                                            for (int j = 0; j < optionArrayList.size(); j++) {
                                                try {
                                                    String que = optionArrayList.get(j).getQuestion();
                                                    String sec = optionArrayList.get(j).getSectionName();
                                                    Integer id = optionArrayList.get(j).getId();
                                                    String que_type = optionArrayList.get(j).getQuestionType();
                                                    String dec = optionArrayList.get(j).getDescription();
                                                    String passage = optionArrayList.get(j).getPassage();
                                                    //  String aud = optionArrayList.get(j).get();

                                                    //String ans = optionArrayList.get(j).getCorrectIndex();
                                                    Log.e("getCorrectIndex", "---" + que_type);
                                                    temporaryOptionArrayList.clear();
                                                    temporaryOptionArrayList.addAll(optionArrayList.get(j).getOptions());
                                                    Log.e("temporaryOptionList", "---" + temporaryOptionArrayList);
                                                    PracticeTest qn = new PracticeTest(sec, id, que_type, que, temporaryOptionArrayList, dec, passage);
                                                    // questionList.clear();

                                                    questionList.add(qn);

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    Log.e("mainResponsessss", "---" + optionArrayList.size());
                                    Log.e("questionListsssss", "---" + questionList.size());
                                    i = 0;
                                    valueforsection = i;
                                    valueforaudio = i;
                                    nextqeustion(questionList, i);
                                    if (i <= 0) {
                                        prevBtn.setVisibility(View.INVISIBLE);
                                    } else {
                                        prevBtn.setVisibility(View.VISIBLE);
                                    }
                                    countDownTimer = new CountDownTimer(mtimeleftinmiles, 1000) {
                                        public void onTick(long millisUntilFinished) {
                                            mtimeleftinmiles = millisUntilFinished;
                                            updateCountDownTimer();
                                        }

                                        public void onFinish() {
                                            //dialogBox();
                                            //submitTest();
                                            Log.e("optionNobefor add", "===" + optionNo);
                                            if (mainResponseArrayList.size() <= 0) {
                                                optionNo = optionNo + 0;
                                            } else {
                                                optionNo = optionNo + mainResponseArrayList.size() - 1;
                                            }
                                            Log.e("optionNoafter", "===" + optionNo);
                                            Log.e("mainResponseArrayList", "===" + mainResponseArrayList.size());
                                            timercount = true;
                                            if (last_question.equalsIgnoreCase("true")) {
                                                submitPracticeTest();
                                                mockTestFinalResult();
                                            } else {
                                                submitPracticeTest();
                                            }

                                        }
                                    }.start();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "Please check you must be signed into the some other device", Toast.LENGTH_SHORT).show();
                                UIUtil.stopProgressDialog(getApplicationContext());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            if (error.getMessage().equalsIgnoreCase("401 Unauthorized")) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "Please check you must be signed into the some other device", Toast.LENGTH_SHORT).show();

                                logout();
                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "Internal Server Error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateCountDownTimer() {
        hour = (int) (mtimeleftinmiles / 1000) / 3600;
        min = (int) ((mtimeleftinmiles / 1000) % 3600) / 60;
        sec = (int) (mtimeleftinmiles / 1000) % 60;
        timeleftformated = String.format("%02d:%02d:%02d", hour, min, sec);
        countDownView.setText(timeleftformated);
    }

    public void dialogBox() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setCancelable(false);
        builder1.setTitle("Confirmation");
        builder1.setMessage("Time\"s Up Submit Your Test");
        // builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Submit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        endQuiz();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void logout() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. logging out..");
                //Map<String, String> params = new HashMap<String, String>();
                //params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
                RetrofitAPI.getInstance(this).getApi().logout(new Callback<JSONObject>() {
                    @Override
                    public void success(JSONObject object, Response response) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            Log.e("API", "logout-" + object.toString());
                            Toast.makeText(getApplicationContext(), "Logged Out successfully", Toast.LENGTH_SHORT).show();
                            //  PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.LOGIN_FLAG, "0");
                            PreferencesManger.clearPreferences(getApplicationContext());

                            startActivity(new Intent(getApplicationContext(), MainLandingActivity.class));
                            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
                            finishAffinity();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        PreferencesManger.clearPreferences(getApplicationContext());
                        UIUtil.stopProgressDialog(getApplicationContext());
                        Toast.makeText(getApplicationContext(), "Logout successfully..", Toast.LENGTH_SHORT).show();
                        // PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.LOGIN_FLAG, "0");
                        startActivity(new Intent(getApplicationContext(), MainLandingActivity.class));
                        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
                        finishAffinity();
                    }
                });
            } else {
                Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void nextqeustion(final ArrayList<PracticeTest> qns, final int index) {

        if (TextUtils.isEmpty(optionArrayList.get(index).getSectionQuestion())) {
            sectionQuesBtn.setVisibility(View.GONE);

        } else {
            sectionQuesBtn.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(optionArrayList.get(index).getSectionDescription())) {
            sectionDescriptionBtn.setVisibility(View.GONE);

        } else {
            sectionDescriptionBtn.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(optionArrayList.get(index).getSectionDescription()) && (TextUtils.isEmpty(optionArrayList.get(index).getSectionQuestion()))) {
            SecionWiseView.setVisibility(View.GONE);
        } else {
            SecionWiseView.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(optionArrayList.get(index).getPassage())) {
            passageBtn.setVisibility(View.GONE);

        } else {
            passageBtn.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(optionArrayList.get(index).getDescription())) {
            descriptionBtn.setVisibility(View.GONE);

        } else {
            descriptionBtn.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(optionArrayList.get(index).getDescription()) && (TextUtils.isEmpty(optionArrayList.get(index).getPassage()))) {
            buttonView.setVisibility(View.GONE);
        } else {
            buttonView.setVisibility(View.VISIBLE);
        }
        try {
            if (mainResponseArrayList.size() == 1) {
                submitBtn.setVisibility(View.VISIBLE);
                if (last_question.equalsIgnoreCase("true")) {
                    submitBtn.setBackgroundResource(R.drawable.circle4);
                } else {
                    submitBtn.setBackgroundResource(R.drawable.halfcircle1);
                }
                nextBtn.setVisibility(View.GONE);
                prevBtn.setVisibility(View.GONE);
            } else if (mainResponseArrayList.size() == 0) {
                submitBtn.setVisibility(View.GONE);
                nextBtn.setVisibility(View.GONE);
                prevBtn.setVisibility(View.GONE);
            } else {
                submitBtn.setVisibility(View.GONE);
                nextBtn.setVisibility(View.VISIBLE);
                prevBtn.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //progressBar.setVisibility(View.VISIBLE);
       /* showless.setVisibility(View.GONE);
        showMore.setVisibility(View.VISIBLE);
        showlessforDesc.setVisibility(View.GONE);
        showMoreforDesc.setVisibility(View.VISIBLE);*/
        //tv_question.setMaxLines(1);
        //tv_Section.setMaxLines(1);

        // selectArray.clear();
        //selectArray.add(PreferencesManger.getStringFields(getApplicationContext(), String.valueOf(index)));

        selectItem = "";
        selectItem = PreferenceMangagerForTest.getStringFields(getApplicationContext(), String.valueOf(index));
        Log.e("replaceAll*", "===" + selectItem);
        selectItem = selectItem.replaceAll(",$", "");
        String s = selectItem;
        Log.e("string", "===" + s);

        s = s.replaceAll(",$", "");
        Log.e("after $ string", "===" + s);

        // map.put(String.valueOf(index), s);


        Log.e("after next map string", "===" + map);
        if (index <= 0) {
            prevBtn.setVisibility(View.INVISIBLE);
        } else {
            prevBtn.setVisibility(View.VISIBLE);
        }
        descriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descriptionDilogbox(index);
            }
        });
        passageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passageDilogbox(index);
            }
        });
        sectionDescriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sectionDescriptionDilogbox(index);
            }
        });
        sectionQuesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sectionQuestionDilogbox(index);
            }
        });
        // tv_question.setText("Q." + (index + 1) + " " + qns.get(index).getQuestion());
        String blank = optionArrayList.get(index).getQuestion();
        blank = blank.replace("{{}}", "......");
        question_id = optionArrayList.get(index).getId().toString();
        tv_question.loadData("Q." + (question_number) + " " + blank, "text/html; charset=utf-8", "UTF-8");
        String section = optionArrayList.get(valueforsection).getSectionName();


        if (TextUtils.isEmpty(optionArrayList.get(index).getAttachment())) {

            audioView.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            mediaPlayer.stop();
            if (next) {
                try {
                    if (qwe) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            tv_Section.setText(Html.fromHtml(Html.fromHtml(section).toString()));
                            sectioNameView.setText(section_name);
                            qwe = false;
                            // progressBar.setVisibility(View.GONE);
                            Log.e("value1", "----" + valueforsection);
                            Log.e("index", "----" + index);
                            // progressBar.setVisibility(View.GONE);
                            //proView.setVisibility(View.GONE);
                            // herader.setVisibility(View.GONE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (section.equals(optionArrayList.get(index).getSectionName())) {
                        try {
                            qwe = false;
                            // progressBar.setVisibility(View.GONE);
                            //proView.setVisibility(View.GONE);
                            Log.e("same", "----" + valueforsection);
                            // header.setVisibility(View.GONE);
                            UIUtil.stopProgressDialog(getApplicationContext());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            valueforsection = index;
                            valueforaudio = index;
                            //  progressBar.setVisibility(View.GONE);
                            // proView.setVisibility(View.GONE);
                            //header.setVisibility(View.GONE);
                            UIUtil.stopProgressDialog(getApplicationContext());
                            Log.e("value2", "----" + valueforsection);

                            tv_Section.setText(Html.fromHtml(Html.fromHtml(optionArrayList.get(index).getSectionName()).toString()));
                            sectioNameView.setText(section_name);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    if (section.equals(optionArrayList.get(index).getSectionName())) {
                        // tv_Section.setText(section);
                        // progressBar.setVisibility(View.GONE);
                        //proView.setVisibility(View.GONE);
                        //header.setVisibility(View.GONE);

                        // progressBar.setVisibility(View.GONE);
                        Log.e("value1", "----" + valueforsection);
                        Log.e("index", "----" + index);
                    } else {
                        try {
                            valueforsection = index;
                            valueforaudio = index;
                            // progressBar.setVisibility(View.GONE);
                            // proView.setVisibility(View.GONE);
                            // header.setVisibility(View.GONE);

                            Log.e("value2", "----" + valueforsection);
                            tv_Section.setText(Html.fromHtml(Html.fromHtml(optionArrayList.get(index).getSectionName()).toString()));
                            sectioNameView.setText(section_name);
                            // tv_Section.setText(qns.get(index).getSectionName());
                            //progressBar.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (optionArrayList.get(index).getQp_attachment_type().equalsIgnoreCase("png")
                || optionArrayList.get(index).getQp_attachment_type().equals("jpg") ||
                optionArrayList.get(index).getQp_attachment_type().equals("jpeg") ||
                optionArrayList.get(index).getQp_attachment_type().equals("gif")) {
            try {
                imageView.setVisibility(View.VISIBLE);
                audioView.setVisibility(View.GONE);
                Glide.with(this)
                        .load(optionArrayList.get(index).getAttachment())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                imageprogress.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                imageprogress.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(imageFile);
                //gestureDetector = new GestureDetector(this, new MockActiveTestActivity.GestureListener());
                //mScaleGestureDetector = new ScaleGestureDetector(this, new MockActiveTestActivity.ScaleListener());
                UIUtil.stopProgressDialog(getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (optionArrayList.get(index).getQp_attachment_type().equalsIgnoreCase("mp3")
                || optionArrayList.get(index).getQp_attachment_type().equals("3gp")
                || optionArrayList.get(index).getQp_attachment_type().equals("mp4")) {

            audioView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            String audio = optionArrayList.get(valueforaudio).getAttachment();
            if (next == true) {
                if (temp) {

                    mediaPlayerLoadingBar = ProgressDialog.show(MockActiveTestActivity.this, "", "Loading. Please wait...", true);

                    // progressBar.setVisibility(View.VISIBLE);
                    Log.e("nextvalueaaaa", "----" + valueforaudio);
                    Log.e("nextindexaaa", "----" + index);

                    temp = false;
                    //mediaPlayer.stop();
                    mediaPlayer = new MediaPlayer();


                    tx1.setText("0 min,0 sec");
                    tx2.setText("0 min,0 sec");
                    tx3.setText("");

                    try {
                        /*dubstep stream*/

                        mediaPlayer.setDataSource(audio);
                        mediaPlayer.prepare();
                        mediaPlayer.start();

                        finalTime = mediaPlayer.getDuration();
                        startTime = mediaPlayer.getCurrentPosition();

                        if (oneTimeOnly == 0) {
                            seekbar.setMax((int) finalTime);
                            oneTimeOnly = 1;
                        }

                        tx2.setText(String.format("%d min, %d sec",
                                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                                finalTime)))
                        );

                        tx1.setText(String.format("%d min, %d sec",
                                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                                startTime)))
                        );

                        seekbar.setProgress((int) startTime);
                        myHandler.postDelayed(UpdateSongTime, 100);
                        b2.setVisibility(View.VISIBLE);
                        b3.setVisibility(View.GONE);
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayerLoadingBar.dismiss();

                        } else {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mediaPlayerLoadingBar.dismiss();
                                }
                            }, 3 * 1000);
                        }
                        UIUtil.stopProgressDialog(getApplicationContext());
                        Log.e("timelag " + " for playback.", "timelag rhah ");
                        //  progressBar.setVisibility(View.GONE);
                        // proView.setVisibility(View.GONE);
                        // header.setVisibility(View.GONE);

                        // mediaPlayer.start();
                        // tx3.setText(optionArrayList.get(index).getAttachment());

                    } catch (IOException e) {
                        UIUtil.stopProgressDialog(getApplicationContext());

                        Log.e(TAG, "Could not open file " + audio + " for playback.", e);
                    }


                    Log.e("value3", "----" + valueforaudio);
                } else if (audio.equals(optionArrayList.get(index).getAttachment())) {
                    Log.e("value39999", "----" + valueforaudio);
                    Log.e("indexzzzz39999", "----" + index);
                    // progressBar.setVisibility(View.GONE);
                    // proView.setVisibility(View.GONE);
                    UIUtil.stopProgressDialog(getApplicationContext());
                    // header.setVisibility(View.GONE);


                    temp = false;
                } else {
                    // progressBar.setVisibility(View.VISIBLE);
                    //  proView.setVisibility(View.VISIBLE);
                    mediaPlayerLoadingBar = ProgressDialog.show(MockActiveTestActivity.this, "", "Loading. Please wait...", true);

                    mediaPlayer.stop();
                    mediaPlayer = new MediaPlayer();


                    tx1.setText("0 min,0 sec");
                    tx2.setText("0 min,0 sec");
                    // tx3.setText("");

                    //mediaPlayer.stop();

                    valueforaudio = index;
                    valueforsection = index;
                    Log.e("valuezzzz", "----" + valueforaudio);
                    Log.e("indexzzzz", "----" + index);
                    // tx3.setText(optionArrayList.get(index).getAttachment());
                    try {
                        /*dubstep stream*/

                        mediaPlayer.setDataSource(optionArrayList.get(index).getAttachment());
                        mediaPlayer.prepare();
                        mediaPlayer.start();

                        finalTime = mediaPlayer.getDuration();
                        startTime = mediaPlayer.getCurrentPosition();

                        if (oneTimeOnly == 0) {
                            seekbar.setMax((int) finalTime);
                            oneTimeOnly = 1;
                        }

                        tx2.setText(String.format("%d min, %d sec",
                                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                                finalTime)))
                        );

                        tx1.setText(String.format("%d min, %d sec",
                                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                                startTime)))
                        );

                        seekbar.setProgress((int) startTime);
                        myHandler.postDelayed(UpdateSongTime, 100);
                        b2.setVisibility(View.VISIBLE);
                        b3.setVisibility(View.GONE);
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayerLoadingBar.dismiss();
                        } else {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mediaPlayerLoadingBar.dismiss();
                                }
                            }, 3 * 1000);
                        }
                        UIUtil.stopProgressDialog(getApplicationContext());
                        //  progressBar.setVisibility(View.GONE);
                        //  proView.setVisibility(View.GONE);
                        //header.setVisibility(View.GONE);

                        // mediaPlayer.start();
                    } catch (IOException e) {
                        UIUtil.stopProgressDialog(getApplicationContext());
                        Log.e(TAG, "Could not open file " + audio + " for playback.", e);
                    }
                    // tv_Section.setText(qns.get(index).getAudio());
                }
            } else if (prev == true) {
                //   value=value-index;
                Log.e("qaxsedc", "----" + valueforaudio);
                if (audio.equals(optionArrayList.get(index).getAttachment())) {
                    // valueforaudio = valueforaudio - 1;
                    Log.e("prevvalueaaaa", "----" + valueforaudio);
                    Log.e("previndexaaa", "----" + index);
                    Log.e("sahi h ", "----" + audio.equals(optionArrayList.get(index).getAttachment()));
                    //progressBar.setVisibility(View.GONE);
                    //proView.setVisibility(View.GONE);
                    //header.setVisibility(View.GONE);
                    Log.e("value3", "----" + valueforaudio);

                } else {
                    // progressBar.setVisibility(View.VISIBLE);

                    mediaPlayerLoadingBar = ProgressDialog.show(MockActiveTestActivity.this, "", "Loading. Please wait...", true);


                    mediaPlayer.stop();
                    mediaPlayer = new MediaPlayer();


                    tx1.setText("0 min,0 sec");
                    tx2.setText("0 min,0 sec");
                    // tx3.setText("");

                    //mediaPlayer.stop();

                    valueforaudio = index;
                    valueforsection = index;
                    Log.e("valuezzzz", "----" + valueforaudio);
                    Log.e("indexzzzz", "----" + index);
                    // tx3.setText(optionArrayList.get(index).getAttachment());
                    try {
                        /*dubstep stream*/

                        mediaPlayer.setDataSource(optionArrayList.get(index).getAttachment());
                        mediaPlayer.prepare();
                        mediaPlayer.start();

                        finalTime = mediaPlayer.getDuration();
                        startTime = mediaPlayer.getCurrentPosition();

                        if (oneTimeOnly == 0) {
                            seekbar.setMax((int) finalTime);
                            oneTimeOnly = 1;
                        }

                        tx2.setText(String.format("%d min, %d sec",
                                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                                finalTime)))
                        );

                        tx1.setText(String.format("%d min, %d sec",
                                TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                                TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                                startTime)))
                        );

                        seekbar.setProgress((int) startTime);
                        myHandler.postDelayed(UpdateSongTime, 100);
                        b2.setVisibility(View.VISIBLE);
                        b3.setVisibility(View.GONE);
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayerLoadingBar.dismiss();
                        } else {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mediaPlayerLoadingBar.dismiss();
                                }
                            }, 3 * 1000);
                        }
                        UIUtil.stopProgressDialog(getApplicationContext());
                        //  progressBar.setVisibility(View.GONE);
                        //  proView.setVisibility(View.GONE);
                        //header.setVisibility(View.GONE);
                        // mediaPlayer.start();

                    } catch (IOException e) {
                        mediaPlayerLoadingBar.dismiss();
                        UIUtil.stopProgressDialog(getApplicationContext());
                        Log.e(TAG, "Could not open file " + audio + " for playback.", e);
                    }
                    // tv_Section.setText(qns.get(index).getAudio());
                }
            }
            if (next) {
                if (qwe) {
                    try {
                        tv_Section.setText(Html.fromHtml(Html.fromHtml(section).toString()));
                        sectioNameView.setText(section_name);
                        qwe = false;
                        // progressBar.setVisibility(View.GONE);
                        // proView.setVisibility(View.GONE);
                        Log.e("value1", "----" + valueforsection);
                        Log.e("index", "----" + index);
                        //   progressBar.setVisibility(View.GONE);
                        //  proView.setVisibility(View.GONE);
                        //header.setVisibility(View.GONE);
                        //UIUtil.stopProgressDialog(getApplicationContext());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (section.equals(optionArrayList.get(index).getSectionName())) {
                    qwe = false;
                    //progressBar.setVisibility(View.GONE);
                    Log.e("same", "----" + valueforsection);
                    // header.setVisibility(View.GONE);


                } else {
                    try {
                        valueforaudio = index;
                        valueforsection = index;
                        // progressBar.setVisibility(View.GONE);
                        //  proView.setVisibility(View.GONE);
                        //header.setVisibility(View.GONE);

                        Log.e("value2", "----" + valueforsection);
                        // UIUtil.stopProgressDialog(getApplicationContext());
                        tv_Section.setText(Html.fromHtml(Html.fromHtml(optionArrayList.get(index).getSectionName()).toString()));
                        sectioNameView.setText(section_name);
                        //progressBar.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    if (section.equals(optionArrayList.get(index).getSectionName())) {
                        // tv_Section.setText(section);
                        // progressBar.setVisibility(View.GONE);
                        // header.setVisibility(View.GONE);
                        // UIUtil.stopProgressDialog(getApplicationContext());
                        //progressBar.setVisibility(View.GONE);
                        // proView.setVisibility(View.GONE);
                        Log.e("value1", "----" + valueforsection);
                        Log.e("index", "----" + index);
                    } else {
                        valueforaudio = index;
                        valueforsection = index;
                        //progressBar.setVisibility(View.GONE);
                        // proView.setVisibility(View.GONE);
                        //header.setVisibility(View.GONE);
                        //UIUtil.stopProgressDialog(getApplicationContext());
                        Log.e("value2", "----" + valueforsection);
                        tv_Section.setText(Html.fromHtml(Html.fromHtml(optionArrayList.get(index).getSectionName()).toString()));
                        sectioNameView.setText(section_name);
                        // tv_Section.setText(qns.get(index).getSectionName());
                        //progressBar.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        optionPositionArraylist.clear();
        PreferenceMangagerForTest.addStringFields(getApplicationContext(), String.valueOf(index), String.valueOf(PreferenceMangagerForTest.getStringFields(getApplicationContext(), String.valueOf(index))));

        if (optionArrayList.get(index).getQuestionType().equalsIgnoreCase("general_speaking")) {

            try {
                mediaPlayer1 = new MediaPlayer();

                optionView.setVisibility(View.GONE);
                speakView.setVisibility(View.VISIBLE);
                // recordButton.setRecordView(recordView);
                //recordButton.setListenForRecord(true);
           /*     saveButton.setEnabled(false);
                playButton.setEnabled(true);
                stopButton.setEnabled(false);

                recordButton.setEnabled(true);*/
                audioLocation.setText("Audio File : " + PreferenceMangagerForTest.getStringFields(getApplicationContext(), optionArrayList.get(index).getId().toString()));

                if (TextUtils.isEmpty(PreferenceMangagerForTest.getStringFields(getApplicationContext(), optionArrayList.get(index).getId().toString()))) {
                    recordButton.setEnabled(true);
                    playButton.setEnabled(false);
                    stopButton.setEnabled(false);
                    stopButton.setVisibility(View.GONE);
                    playButton.setVisibility(View.VISIBLE);

                    if (mediaPlayer1 != null) {
                        mediaPlayer1.stop();
                        mediaPlayer1.release();
                        MediaRecorderReady();
                    }
                    // saveButton.setEnabled(false);
                    // nextBtn.setEnabled(false);
                    // prevBtn.setEnabled(false);
                } else {
                    //saveButton.setEnabled(false);
                    playButton.setEnabled(true);
                    stopButton.setEnabled(false);
                    recordButton.setEnabled(true);
                    stopButton.setVisibility(View.GONE);
                    playButton.setVisibility(View.VISIBLE);

                    if (mediaPlayer1 != null) {
                        mediaPlayer1.stop();
                        mediaPlayer1.release();
                        MediaRecorderReady();
                    }
                    // nextBtn.setEnabled(true);
                    // prevBtn.setEnabled(true);
                }
                //recordButton.setRecordView(recordView);
                //recordButton.setListenForRecord(true);
                recordView.setCancelBounds(8);


                recordView.setSmallMicColor(Color.parseColor("#c2185b"));

                //prevent recording under one Second
                recordView.setLessThanSecondAllowed(false);


                recordView.setSlideToCancelText("Recording...");


                recordView.setCustomSounds(R.raw.record_start, R.raw.record_finished, 0);
               /* stopRecordButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        counter = 0;
                        recordtime.setText("");
                        recordtime.setVisibility(View.GONE);
                        recordButton.setVisibility(View.VISIBLE);
                        stopRecordButton.setVisibility(View.GONE);
                        Log.e("record btn", "-----" + "ACTION_UP working");
                        recordButton.setBackgroundColor(Color.RED);
                        //  String time = getHumanTimeText(recordTime);
                        // Toast.makeText(PracticeActiveTestActivity.this, "onFinishRecord - Recorded Time is: " + time, Toast.LENGTH_SHORT).show();
                        Log.d("RecordView", "onFinish");
                        try {
                            mediaRecorder.stop();
                            //saveButton.setEnabled(false);
                            playButton.setEnabled(true);
                            //recordButton.setEnabled(true);
                            stopButton.setEnabled(false);
                            // nextBtn.setEnabled(true);
                            //  prevBtn.setEnabled(true);
                            audioLocation.setText("Audio File : " + PreferenceMangagerForTest.getStringFields(getApplicationContext(), optionArrayList.get(index).getId().toString()));
                            submitSpeakTest();
                           *//* Toast.makeText(PracticeActiveTestActivity.this, "Recording Completed",
                                    Toast.LENGTH_LONG).show();*//*
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });*/
          /*      recordButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("record btn", "-----" + "ACTION_DOWN working");
                        recordButton.setBackgroundColor(Color.GREEN);
                        // AppLog.logString("Start Recording");
                        stopButton.setVisibility(View.GONE);
                        playButton.setVisibility(View.VISIBLE);

                        try {
                            if (checkPermission()) {
                                audioLocation.setText("");
                                AudioSavePathInDevice =
                                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                                                optionArrayList.get(index).getId().toString() + "AudioRecording.3gp";
                                PreferenceMangagerForTest.addStringFields(getApplicationContext(), optionArrayList.get(index).getId().toString(), AudioSavePathInDevice);
                                map.put("\"" + optionArrayList.get(index).getId().toString() + "\"", "\"" + PreferenceMangagerForTest.getStringFields(getApplicationContext(), optionArrayList.get(index).getId().toString()) + "\"");
                                //file = new File(PreferenceMangagerForTest.getStringFields(getApplicationContext(), optionArrayList.get(index).getId().toString()));
                                //  PreferenceMangagerForTest.addStringFields(getApplicationContext(), String.valueOf(index) + "A", file.getName());
                                //mapfile.put("\"" + String.valueOf(index) + "A", PreferenceMangagerForTest.getStringFields(getApplicationContext(), String.valueOf(index) + "A") + "\"");
                                Log.e("audioSpeak", "-----" + map);
                                //RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                                // PreferenceMangagerForTest.addStringFields(getApplicationContext(), index + optionArrayList.get(index).getId().toString(), String.valueOf(requestFile));

                                MediaRecorderReady();

                                try {
                                    mediaRecorder.prepare();
                                    mediaRecorder.start();
                                } catch (IllegalStateException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                                //recordButton.setEnabled(false);
                                // saveButton.setEnabled(true);

                                *//*Toast.makeText(PracticeActiveTestActivity.this, "Recording started",
                                        Toast.LENGTH_LONG).show();*//*
                            } else {
                                requestPermission();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        recordButton.setVisibility(View.GONE);
                        stopRecordButton.setVisibility(View.VISIBLE);
                        recordtime.setVisibility(View.VISIBLE);
                        Thread t = new Thread() {
                            @Override
                            public void run() {
                                while (!isInterrupted()) {
                                    try {
                                        Thread.sleep(1000);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                counter++;
                                                // countDownView.setText(String.valueOf(counter) + " " + "Sec");
                                                try {
                                                               *//* hour = counter * 60 * 60;
                                                                min = counter * 60;
                                                                sec = counter;*//*
                                                    hour = (counter / 60) / 60;
                                                    min = (counter / 60) % 60;
                                                    sec = counter % 60;
                                                    timeleftformated = String.format("%02d:%02d:%02d", hour, min, sec);
                                                    recordtime.setText(timeleftformated);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        };
                        t.start();
                    }
                });*/

                recordButton.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // TODO Auto-generated method stub
                        Log.e("record btn", "-----" + "touch working");
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                Log.e("record btn", "-----" + "ACTION_DOWN working");
                                recordButton.setBackgroundColor(Color.GREEN);
                                // AppLog.logString("Start Recording");
                                stopButton.setVisibility(View.GONE);
                                playButton.setVisibility(View.VISIBLE);

                                try {
                                    audioLocation.setText("");
                                    AudioSavePathInDevice =
                                            Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                                                    optionArrayList.get(index).getId().toString() + "AudioRecording.3gp";
                                    PreferenceMangagerForTest.addStringFields(getApplicationContext(), optionArrayList.get(index).getId().toString(), AudioSavePathInDevice);
                                    map.put("\"" + optionArrayList.get(index).getId().toString() + "\"", "\"" + PreferenceMangagerForTest.getStringFields(getApplicationContext(), optionArrayList.get(index).getId().toString()) + "\"");
                                    //file = new File(PreferenceMangagerForTest.getStringFields(getApplicationContext(), optionArrayList.get(index).getId().toString()));
                                    //  PreferenceMangagerForTest.addStringFields(getApplicationContext(), String.valueOf(index) + "A", file.getName());
                                    //mapfile.put("\"" + String.valueOf(index) + "A", PreferenceMangagerForTest.getStringFields(getApplicationContext(), String.valueOf(index) + "A") + "\"");
                                    Log.e("audioSpeak", "-----" + map);
                                    //RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                                    // PreferenceMangagerForTest.addStringFields(getApplicationContext(), index + optionArrayList.get(index).getId().toString(), String.valueOf(requestFile));

                                    MediaRecorderReady();

                                    try {
                                        mediaRecorder.prepare();
                                        mediaRecorder.start();
                                    } catch (IllegalStateException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }

                                    //recordButton.setEnabled(false);
                                    // saveButton.setEnabled(true);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return true;
                            case MotionEvent.ACTION_UP:
                                Log.e("record btn", "-----" + "ACTION_UP working");
                                recordButton.setBackgroundColor(Color.RED);
                                //  String time = getHumanTimeText(recordTime);
                                // Toast.makeText(PracticeActiveTestActivity.this, "onFinishRecord - Recorded Time is: " + time, Toast.LENGTH_SHORT).show();
                                Log.d("RecordView", "onFinish");
                                try {
                                    mediaRecorder.stop();
                                    //saveButton.setEnabled(false);
                                    playButton.setEnabled(true);
                                    //recordButton.setEnabled(true);
                                    stopButton.setEnabled(false);
                                    // nextBtn.setEnabled(true);
                                    //  prevBtn.setEnabled(true);
                                    audioLocation.setText("Audio File : " + PreferenceMangagerForTest.getStringFields(getApplicationContext(), optionArrayList.get(index).getId().toString()));
                                    submitSpeakTest();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                //Log.d("RecordTime", time);
                                break;
                        }
                        return false;
                    }
                });
              /*  recordView.setOnRecordListener(new OnRecordListener() {
                    @Override
                    public void onStart() {
                        Log.d("RecordView", "onStart");
                        //Toast.makeText(PracticeActiveTestActivity.this, "OnStartRecord", Toast.LENGTH_SHORT).show();
                        stopButton.setVisibility(View.GONE);
                        playButton.setVisibility(View.VISIBLE);

                        try {
                            if (checkPermission()) {
                                audioLocation.setText("");
                                AudioSavePathInDevice =
                                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                                                optionArrayList.get(index).getId().toString() + "AudioRecording.3gp";
                                PreferenceMangagerForTest.addStringFields(getApplicationContext(), optionArrayList.get(index).getId().toString(), AudioSavePathInDevice);
                                map.put("\"" + optionArrayList.get(index).getId().toString() + "\"", "\"" + PreferenceMangagerForTest.getStringFields(getApplicationContext(), optionArrayList.get(index).getId().toString()) + "\"");
                                //file = new File(PreferenceMangagerForTest.getStringFields(getApplicationContext(), optionArrayList.get(index).getId().toString()));
                                //  PreferenceMangagerForTest.addStringFields(getApplicationContext(), String.valueOf(index) + "A", file.getName());
                                //mapfile.put("\"" + String.valueOf(index) + "A", PreferenceMangagerForTest.getStringFields(getApplicationContext(), String.valueOf(index) + "A") + "\"");
                                Log.e("audioSpeak", "-----" + map);
                                //RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                                // PreferenceMangagerForTest.addStringFields(getApplicationContext(), index + optionArrayList.get(index).getId().toString(), String.valueOf(requestFile));

                                MediaRecorderReady();

                                try {
                                    mediaRecorder.prepare();
                                    mediaRecorder.start();
                                } catch (IllegalStateException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                                //recordButton.setEnabled(false);
                                // saveButton.setEnabled(true);

                                *//*Toast.makeText(PracticeActiveTestActivity.this, "Recording started",
                                        Toast.LENGTH_LONG).show();*//*
                            } else {
                                requestPermission();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancel() {
                        // Toast.makeText(PracticeActiveTestActivity.this, "onCancel", Toast.LENGTH_SHORT).show();

                        Log.d("RecordView", "onCancel");

                    }

                    @Override
                    public void onFinish(long recordTime) {

                        String time = getHumanTimeText(recordTime);
                        // Toast.makeText(PracticeActiveTestActivity.this, "onFinishRecord - Recorded Time is: " + time, Toast.LENGTH_SHORT).show();
                        Log.d("RecordView", "onFinish");
                        try {
                            mediaRecorder.stop();
                            //saveButton.setEnabled(false);
                            playButton.setEnabled(true);
                            //recordButton.setEnabled(true);
                            stopButton.setEnabled(false);
                            // nextBtn.setEnabled(true);
                            //  prevBtn.setEnabled(true);
                            audioLocation.setText("Audio File : " + PreferenceMangagerForTest.getStringFields(getApplicationContext(), optionArrayList.get(index).getId().toString()));
                            submitSpeakTest();
                           *//* Toast.makeText(PracticeActiveTestActivity.this, "Recording Completed",
                                    Toast.LENGTH_LONG).show();*//*
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.d("RecordTime", time);
                    }

                    @Override
                    public void onLessThanSecond() {
                        // Toast.makeText(PracticeActiveTestActivity.this, "OnLessThanSecond", Toast.LENGTH_SHORT).show();
                        Log.d("RecordView", "onLessThanSecond");
                    }
                });


                recordView.setOnBasketAnimationEndListener(new OnBasketAnimationEnd() {
                    @Override
                    public void onAnimationEnd() {
                        Log.d("RecordView", "Basket Animation Finished");
                    }
                });*/


                playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) throws IllegalArgumentException,
                            SecurityException, IllegalStateException {


                        try {

                            if (TextUtils.isEmpty(PreferenceMangagerForTest.getStringFields(getApplicationContext(), optionArrayList.get(index).getId().toString()))) {
                                Toast.makeText(MockActiveTestActivity.this, "No Audio Available",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                //saveButton.setEnabled(false);
                                //recordButton.setEnabled(false);
                                stopButton.setEnabled(true);

                                mediaPlayer1 = new MediaPlayer();
                                try {
                                    mediaPlayer1.setDataSource(PreferenceMangagerForTest.getStringFields(getApplicationContext(), optionArrayList.get(index).getId().toString()));
                                    mediaPlayer1.prepare();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                mediaPlayer1.start();
                                Toast.makeText(MockActiveTestActivity.this, "Recording Playing",
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        stopButton.setVisibility(View.VISIBLE);
                        playButton.setVisibility(View.GONE);

                    }
                });

            /*    if (mediaPlayer1.isPlaying()) {
                    stopButton.setVisibility(View.VISIBLE);
                    playButton.setVisibility(View.GONE);
                } else {
                    stopButton.setVisibility(View.GONE);
                    playButton.setVisibility(View.VISIBLE);
                }*/
                stopButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            //  saveButton.setEnabled(false);
                            stopButton.setVisibility(View.GONE);
                            playButton.setVisibility(View.VISIBLE);
                            recordButton.setEnabled(true);
                            stopButton.setEnabled(false);
                            playButton.setEnabled(true);

                            if (mediaPlayer1 != null) {
                                mediaPlayer1.stop();
                                mediaPlayer1.release();
                                MediaRecorderReady();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (optionArrayList.get(index).getQuestionType().equalsIgnoreCase("fill_in_the_gaps_short_answer") ||
                optionArrayList.get(index).getQuestionType().equalsIgnoreCase("note_completion")
                || optionArrayList.get(index).getQuestionType().equalsIgnoreCase("general_writing")
                || optionArrayList.get(index).getQuestionType().equalsIgnoreCase("academic_writing")) {
            optionView.setVisibility(View.VISIBLE);
            speakView.setVisibility(View.GONE);
            try {

                optionView.setVisibility(View.VISIBLE);
                optionPositionArraylist.clear();
                optionPositionArraylist.addAll(optionArrayList.get(index).getOptions());
                fillBlankAdapter = new FillBlankAdapter(this, optionPositionArraylist);
                optionView.setAdapter(fillBlankAdapter);
                fillBlankAdapter.notifyDataSetChanged();
                // fillBlankAdapter.setSelectedIndex(PreferencesManger.getStringFields(getApplicationContext(), String.valueOf(index)), itempos);
                fillBlankAdapter.setSelectedStringIndex(String.valueOf(index), true);


                Log.e("dadaafaj", "====");
                Log.e("dadaafaj", "====" + optionPositionArraylist);
                fillBlankAdapter.SetOnItemClickListener(new FillBlankAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }

                    @Override
                    public void onEditText(int position, String s) {
                        try {
                            String data;
                            data = s;
                            PreferenceMangagerForTest.addStringFields(getApplicationContext(), index + optionPositionArraylist.get(position).getIndex().toString(), data);
                            map.put("\"" + optionPositionArraylist.get(position).getIndex() + "\"", "\"" + PreferenceMangagerForTest.getStringFields(getApplicationContext(), index + optionPositionArraylist.get(position).getIndex().toString()) + "\"");
                            //  multiSelectAdapter.setSelectedStringIndex(PreferencesManger.getStringFields(getApplicationContext(), optionPositionArraylist.get(position).getId().toString()), true);
                            Log.e("presentItemsize*", "===" + PreferenceMangagerForTest.getStringFields(getApplicationContext(), index + optionPositionArraylist.get(position).getIndex().toString()));
                            // multiSelectAdapter.notifyDataSetChanged();
                            Log.e("mapdddddd*", "===" + map);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (optionArrayList.get(index).getQuestionType().equalsIgnoreCase("true_false")) {
            optionView.setVisibility(View.VISIBLE);
            speakView.setVisibility(View.GONE);
            try {
                //   optionsecondView.setVisibility(View.VISIBLE);
                //optionView.setVisibility(View.GONE);

                optionView.setVisibility(View.VISIBLE);
                optionPositionArraylist.clear();
                optionPositionArraylist.addAll(optionArrayList.get(index).getOptions());
                trueFalseAdapter = new TrueFalseAdapter(this, optionPositionArraylist);
                Log.e("normal itempos", "====" + PreferenceMangagerForTest.getStringFields(getApplicationContext(), String.valueOf(index)));
                optionView.setAdapter(trueFalseAdapter);
                trueFalseAdapter.notifyDataSetChanged();
                trueFalseAdapter.setSelectedIndex(PreferenceMangagerForTest.getStringFields(getApplicationContext(), String.valueOf(index)), itempos);


                trueFalseAdapter.SetOnItemClickListener(new TrueFalseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }

                    @Override
                    public void onTrueClick(View view, int position) {
                        try {
                            itempos = position;
                            PreferenceMangagerForTest.addStringFields(getApplicationContext(), String.valueOf(index), "true");
                            Log.e("itempos", "====" + itempos);
                            // Log.e("key course", "---" + PreferencesManger.addStringFields(getApplicationContext(), String.valueOf(index)));
                            //if (TextUtils.isEmpty(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME)))
                            trueFalseAdapter.setSelectedIndex(PreferenceMangagerForTest.getStringFields(getApplicationContext(), String.valueOf(index)), position);
                            trueFalseAdapter.notifyDataSetChanged();
                            map.put("\"" + optionPositionArraylist.get(position).getIndex() + "\"", "\"" + "true" + "\"");
                            //  mainmap.put(qns.get(index).getId().toString(), map.put(optionPositionArraylist.get(position).getIndex(), String.valueOf(optionPositionArraylist.get(position).getOption())));

                            Log.e("map", "====" + map);
                            //Log.e("mainmap", "====" + mainmap);
                       /* Toast.makeText(getApplicationContext(), "Answer and id is - " + optionPositionArraylist.get(position).getOption() +
                                " - " + optionPositionArraylist.get(position).getIndex(), Toast.LENGTH_SHORT).show();
*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFalseClick(View view, int position) {
                        try {
                            itempos = position;
                            PreferenceMangagerForTest.addStringFields(getApplicationContext(), String.valueOf(index), "false");
                            Log.e("itempos", "====" + itempos);
                            // Log.e("key course", "---" + PreferencesManger.addStringFields(getApplicationContext(), String.valueOf(index)));
                            //if (TextUtils.isEmpty(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME)))
                            trueFalseAdapter.setSelectedIndex(PreferenceMangagerForTest.getStringFields(getApplicationContext(), String.valueOf(index)), position);
                            trueFalseAdapter.notifyDataSetChanged();
                            map.put("\"" + optionPositionArraylist.get(position).getIndex() + "\"", "\"" + "false" + "\"");
                            //mainmap.put(qns.get(index).getId().toString(), map.put(optionPositionArraylist.get(position).getIndex(), String.valueOf(optionPositionArraylist.get(position).getOption())));

                            Log.e("map", "====" + map);
                       /* Toast.makeText(getApplicationContext(), "Answer and id is - " + optionPositionArraylist.get(position).getOption() +
                                " - " + optionPositionArraylist.get(position).getIndex(), Toast.LENGTH_SHORT).show();
*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNotGivenClick(View view, int position) {
                        try {
                            itempos = position;
                            PreferenceMangagerForTest.addStringFields(getApplicationContext(), String.valueOf(index), "notgiven");
                            Log.e("itempos", "====" + itempos);
                            // Log.e("key course", "---" + PreferencesManger.addStringFields(getApplicationContext(), String.valueOf(index)));
                            //if (TextUtils.isEmpty(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME)))
                            trueFalseAdapter.setSelectedIndex(PreferenceMangagerForTest.getStringFields(getApplicationContext(), String.valueOf(index)), position);
                            trueFalseAdapter.notifyDataSetChanged();
                            map.put("\"" + optionPositionArraylist.get(position).getIndex() + "\"", "\"" + "not_given" + "\"");
                            //mainmap.put(qns.get(index).getId().toString(), map.put(optionPositionArraylist.get(position).getIndex(), String.valueOf(optionPositionArraylist.get(position).getOption())));

                            Log.e("map", "====" + map);
                      /*  Toast.makeText(getApplicationContext(), "Answer and id is - " + optionPositionArraylist.get(position).getOption() +
                                " - " + optionPositionArraylist.get(position).getIndex(), Toast.LENGTH_SHORT).show();
*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

               /* itempos = position;
                //PreferencesManger.addIntFields(getApplicationContext(), String.valueOf(index) + itempos, itempos);
                Log.e("itempos", "====" + itempos);
                Log.e("key course", "---" + PreferencesManger.getIntFields(getApplicationContext(), String.valueOf(index) + itempos));
                //if (TextUtils.isEmpty(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME)))
                //adapter.setSelectedIndex(PreferencesManger.getIntFields(getApplicationContext(), String.valueOf(index) + itempos), position);
                //adapter.notifyDataSetChanged();
                map.put(index, String.valueOf(optionPositionArraylist.get(position).getId()));
                Log.e("map", "====" + map);
                Toast.makeText(getApplicationContext(), "Answer and id is - " + optionPositionArraylist.get(position).getAns() +
                        " - " + optionPositionArraylist.get(position).getId(), Toast.LENGTH_SHORT).show();*/


                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (optionArrayList.get(index).getQuestionType().equalsIgnoreCase("multiple_choice_one_answer")) {
            optionView.setVisibility(View.VISIBLE);
            speakView.setVisibility(View.GONE);
            try {
                //   optionsecondView.setVisibility(View.VISIBLE);
                //optionView.setVisibility(View.GONE);

                optionView.setVisibility(View.VISIBLE);
                optionPositionArraylist.clear();
                optionPositionArraylist.addAll(optionArrayList.get(index).getOptions());
                singleSelectAdapter = new SingleSelectAdapter(this, optionPositionArraylist);
                Log.e("normal itempos", "====" + PreferenceMangagerForTest.getStringFields(getApplicationContext(), String.valueOf(index)));
                optionView.setAdapter(singleSelectAdapter);
                singleSelectAdapter.notifyDataSetChanged();
                singleSelectAdapter.setSelectedIndex(PreferenceMangagerForTest.getStringFields(getApplicationContext(), String.valueOf(index)), itempos);

                singleSelectAdapter.SetOnItemClickListener(new SingleSelectAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }

                    @Override
                    public void ontopicClick(View view, int position) {
                        try {
                            itempos = position;
                            PreferenceMangagerForTest.addStringFields(getApplicationContext(), String.valueOf(index), String.valueOf(itempos));
                            Log.e("itempos", "====" + itempos);
                            // Log.e("key course", "---" + PreferencesManger.addStringFields(getApplicationContext(), String.valueOf(index)));
                            //if (TextUtils.isEmpty(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME)))
                            singleSelectAdapter.setSelectedIndex(PreferenceMangagerForTest.getStringFields(getApplicationContext(), String.valueOf(index)), position);
                            singleSelectAdapter.notifyDataSetChanged();
                            map.put("\"" + optionPositionArraylist.get(position).getIndex() + "\"", "\"" + (optionPositionArraylist.get(position).getOption()) + "\"");
                            Log.e("map", "====" + map);
                      /*  Toast.makeText(getApplicationContext(), "Answer and id is - " + optionPositionArraylist.get(position).getOption() +
                                " - " + optionPositionArraylist.get(position).getIndex(), Toast.LENGTH_SHORT).show();
*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            optionView.setVisibility(View.VISIBLE);
            speakView.setVisibility(View.GONE);
            try {
                //optionsecondView.setVisibility(View.GONE);
                //optionView.setVisibility(View.VISIBLE);

                optionView.setVisibility(View.VISIBLE);
                optionPositionArraylist.clear();
                optionPositionArraylist.addAll(optionArrayList.get(index).getOptions());
                multiSelectAdapter = new MultiSelectAdapter(this, optionPositionArraylist);
                optionView.setAdapter(multiSelectAdapter);
                multiSelectAdapter.notifyDataSetChanged();
                multiSelectAdapter.setSelectedIndex(String.valueOf(index), true);
                // multiSelectAdapter.setSelectedIndex(PreferenceMangagerForTest.getStringFields(getApplicationContext(), String.valueOf(index)), itempos);
                //  multiSelectAdapter.setSelectedStringIndex(String.valueOf(index), true);

                multiSelectAdapter.SetOnItemClickListener(new MultiSelectAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }

                    @Override
                    public void ontopicItemClick(View view, int position) {
                        try {
                            if (optionPositionArraylist.get(position).getCheck_box() == 2) {
                                try {
                                    optionPositionArraylist.get(position).setCheck_box(1);

                                    // map.put(index + position, optionPositionArraylist.get(position).getAns());

                                    //selectArray.add(optionPositionArraylist.get(position).getId().toString());
                                    selectItem += optionPositionArraylist.get(position).getIndex().toString() + ",";
                                    // selectItem = selectArray.toString();
                                    selectItem = selectItem.replace("[,", "").replace("]", "").replace("[", "");
                                    PreferenceMangagerForTest.addStringFields(getApplicationContext(), index + optionPositionArraylist.get(position).getIndex().toString(), "true");
                                    multiSelectAdapter.setSelectedIndex(String.valueOf(index), true);
                                    //PreferenceMangagerForTest.addStringFields(getApplicationContext(), String.valueOf(index), selectItem);

                                    //multiSelectAdapter.setSelectedIndex(PreferenceMangagerForTest.getStringFields(getApplicationContext(), String.valueOf(index)), position);
                                    Log.e("presentItemsize*", "===" + selectItem);
                                    Log.e("studentlistsize*", "===" + optionPositionArraylist.size());

                                    map.put("\"" + optionPositionArraylist.get(position).getIndex() + "\"", "\"" + optionPositionArraylist.get(position).getOption() + "\"");

                                    // map.put(optionPositionArraylist.get(position).getIndex(), PreferenceMangagerForTest.getStringFields(getApplicationContext(), String.valueOf(index)));
                                    multiSelectAdapter.notifyDataSetChanged();
                                    Log.e("map", "====" + map);
                                    Log.e("data1", "===" + selectItem);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    optionPositionArraylist.get(position).setCheck_box(2);

                                    //map.remove(index + position);

                                    //selectArray.remove(selectItem.contains(String.valueOf(optionPositionArraylist.get(position).getId())));
                                    //  selectItem = selectArray.toString();
                                    selectItem = selectItem.replace(optionPositionArraylist.get(position).getIndex().toString() + ",", "");
                                    // selectItem = selectItem.replace("[,", "").replace("]", "").replace("[", "");

                                    // selectItem = selectItem.replaceAll(", $", "");
                                    PreferenceMangagerForTest.addStringFields(getApplicationContext(), index + optionPositionArraylist.get(position).getIndex().toString(), "false");
                                    multiSelectAdapter.setSelectedIndex(String.valueOf(index), true);
                                    // PreferenceMangagerForTest.addStringFields(getApplicationContext(), String.valueOf(index), selectItem);
                                    // multiSelectAdapter.setSelectedIndex(PreferenceMangagerForTest.getStringFields(getApplicationContext(), String.valueOf(index)), position);
                                    multiSelectAdapter.notifyDataSetChanged();
                                    Log.e("data2", "===" + selectItem);
                                    map.remove("\"" + optionPositionArraylist.get(position).getIndex() + "\"");
                                    //  map.put(optionPositionArraylist.get(position).getIndex(), PreferenceMangagerForTest.getStringFields(getApplicationContext(), String.valueOf(index)));
                                    Log.e("map", "====" + map);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

               /* itempos = position;
                //PreferencesManger.addIntFields(getApplicationContext(), String.valueOf(index) + itempos, itempos);
                Log.e("itempos", "====" + itempos);
                Log.e("key course", "---" + PreferencesManger.getIntFields(getApplicationContext(), String.valueOf(index) + itempos));
                //if (TextUtils.isEmpty(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME)))
                //adapter.setSelectedIndex(PreferencesManger.getIntFields(getApplicationContext(), String.valueOf(index) + itempos), position);
                //adapter.notifyDataSetChanged();
                map.put(index, String.valueOf(optionPositionArraylist.get(position).getId()));
                Log.e("map", "====" + map);
                Toast.makeText(getApplicationContext(), "Answer and id is - " + optionPositionArraylist.get(position).getAns() +
                        " - " + optionPositionArraylist.get(position).getId(), Toast.LENGTH_SHORT).show();*/

                    }

                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Log.e("normal itempos", "====" + PreferenceMangagerForTest.getStringFields(

                getApplicationContext(), String.

                        valueOf(index)));


        //answer = qns.get(index).getCorrectIndex().toString();

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    next = false;
                    prev = true;
                    // progressBar.setVisibility(View.VISIBLE);
                    visibleView();
                    Log.e("totaltime", "---" + String.format("%02d:%02d:%02d", hour, min, sec));
                    int rhour = hour * 60 * 60;
                    int rmin = min * 60;
                    int rsec = sec;
                    int remaingtime = rhour + rmin + rsec;
                    int ttt = totaltime - remaingtime;
                    Log.e("remaingtime", "---" + remaingtime);
                    Log.e("ttt", "---" + ttt);
                    rsec = ttt % 60;
                    rmin = (ttt / 60) % 60;
                    rhour = (ttt / 60) / 60;

                    Log.e("takentime", "---" + String.format("%02d:%02d:%02d", rhour, rmin, rsec));

                    if (index <= mainResponseArrayList.size() - 1) {
                        try {
                            i = index;
                            //System.out.println("size "+qns.size()+", i: "+i);
                            i = i - 1;
                            question_number = question_number - 1;

                            nextqeustion(questionList, i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            countDownView.setVisibility(View.GONE);
                            optionView.setVisibility(View.GONE);
                            optionPositionArraylist.clear();
                            // tv_question.setText("end of quiz!!!");
                            nextBtn.setVisibility(View.GONE);
                            prevBtn.setVisibility(View.GONE);
                            submitBtn.setVisibility(View.VISIBLE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //startPracticeTest();

                if (last_question.equalsIgnoreCase("true")) {
                    dialogBoxForFinalSubmit();

                } else {

                    dialogBoxForOptionalSubmit();
                }
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //    progressBar.setVisibility(View.VISIBLE);
                try {
                    next = true;
                    prev = false;
                    // rgb.clearCheck();
                    visibleView();
                    Log.e("size", "====" + (mainResponseArrayList.size() - 1));
                    Log.e("index size", "====" + index);
                    Log.e(" questionList.size()", "====" + mainResponseArrayList.size());

                    if (index < mainResponseArrayList.size() - 1) {
                        try {
                            i = index;

                            i = i + 1;
                            question_number = question_number + 1;
                            nextqeustion(questionList, i);
                            if (index == (mainResponseArrayList.size() - 2)) {
                                nextBtn.setVisibility(View.GONE);
                                prevBtn.setVisibility(View.VISIBLE);
                                submitBtn.setVisibility(View.VISIBLE);
                                if (last_question.equalsIgnoreCase("true")) {
                                    submitBtn.setBackgroundResource(R.drawable.circle4);
                                } else {
                                    submitBtn.setBackgroundResource(R.drawable.halfcircle1);
                                }

                            } else {
                                nextBtn.setVisibility(View.VISIBLE);
                                prevBtn.setVisibility(View.VISIBLE);
                                submitBtn.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        //  nextqeustion(questionList, i);
                        submitTest();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void visibleView() {
        try {
            nextBtn.setVisibility(View.VISIBLE);
            prevBtn.setVisibility(View.VISIBLE);
            countDownView.setVisibility(View.VISIBLE);
            optionView.setVisibility(View.VISIBLE);
            //    showMore.setVisibility(View.VISIBLE);
            //endQuizTv.setVisibility(View.VISIBLE);
            // showless.setVisibility(View.VISIBLE);
            tv_question.setVisibility(View.VISIBLE);
            submitBtn.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void submitTest() {
        //submitPracticeTest();
        try {
            nextBtn.setVisibility(View.GONE);
            prevBtn.setVisibility(View.GONE);
            countDownView.setVisibility(View.GONE);
            optionView.setVisibility(View.GONE);
            tv_Section.setVisibility(View.GONE);
            descriptionBtn.setVisibility(View.GONE);
            linkView.setVisibility(View.GONE);
            header.setVisibility(View.GONE);
            //  optionPositionArraylist.clear();
            //  showMore.setVisibility(View.GONE);
            // endQuizTv.setVisibility(View.GONE);
            //  showless.setVisibility(View.GONE);
            tv_question.setVisibility(View.GONE);
            submitBtn.setVisibility(View.GONE);
            buttonView.setVisibility(View.GONE);
            sectionDescriptionBtn.setVisibility(View.GONE);
            sectionQuesBtn.setVisibility(View.GONE);
            SecionWiseView.setVisibility(View.GONE);
            audioView.setVisibility(View.GONE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void endQuiz() {
        try {
            time_taken = "";
            Log.e("totaltime", "---" + String.format("%02d:%02d:%02d", hour, min, sec));
            int rhour = hour * 60 * 60;
            int rmin = min * 60;
            int rsec = sec;
            int remaingtime = rhour + rmin + rsec;
            int ttt = totaltime - remaingtime;
            Log.e("remaingtime", "---" + remaingtime);
            Log.e("ttt", "---" + ttt);
            rsec = ttt % 60;
            rmin = (ttt / 60) % 60;
            rhour = (ttt / 60) / 60;

            Log.e("takentime", "---" + String.format("%02d:%02d:%02d", rhour, rmin, rsec));
            time_taken = String.format("%02d:%02d:%02d", rhour, rmin, rsec);
            time_taken = time_taken.replace("-", "");
            speakView.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            buttonView.setVisibility(View.GONE);
            linkView.setVisibility(View.GONE);
            resultview.setVisibility(View.VISIBLE);
            countDownView.setVisibility(View.GONE);
            header.setVisibility(View.GONE);
            optionView.setVisibility(View.GONE);
            SecionWiseView.setVisibility(View.GONE);
            sectionDescriptionBtn.setVisibility(View.GONE);
            sectionQuesBtn.setVisibility(View.GONE);
            optionPositionArraylist.clear();
            descriptionBtn.setVisibility(View.GONE);
            //  showMore.setVisibility(View.GONE);
            //  showless.setVisibility(View.GONE);
            tv_question.setVisibility(View.GONE);
            //  endQuizTv.setVisibility(View.VISIBLE);
            audioView.setVisibility(View.GONE);

            Log.e("time_take", "----" + time_taken);
            //endQuizTv.setText("End of quiz!!!" + "\n" + "  " + "\n" +
            //  String.format("%02d:%02d:%02d", rhour, rmin, rsec));
            nextBtn.setVisibility(View.GONE);
            submitBtn.setVisibility(View.GONE);
            prevBtn.setVisibility(View.GONE);
            // doneBtn.setVisibility(View.VISIBLE);
            tv_Section.setVisibility(View.GONE);
            //  showMoreforDesc.setVisibility(View.GONE);
            // showlessforDesc.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitPracticeTestForWriting() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");
                Map<String, String> params = new HashMap<String, String>();


                params.put("submission_id", submission_id);

                params.put("answer", mapstring);

                params.put("submission_duration", time_taken);


                //params.put("auth_token", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_TOKEN));

                RetrofitAPI.getInstance(this).getApi().submitPracticeTestForWriting(params, new Callback<PracticeTestResult>() {
                    @Override
                    public void success(PracticeTestResult object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {
                                try {
                                    UIUtil.stopProgressDialog(getApplicationContext());

                                    // totalQuestionView.setVisibility(View.GONE);
                                    //correctAnswerView.setVisibility(View.GONE);
                                    //percentageView.setVisibility(View.GONE);
                                    //timeTakeView.setVisibility(View.GONE);
                                    //responseView.setVisibility(View.VISIBLE);
                                    //responseView.setText(object.getMessage());

                              /*  arrayList.clear();
                                arrayList.addAll(object.getAssignedBatchesIds());
                                if (arrayList.size() > 0) {
                                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.ENROLL_STATUS, "true");
                                    //commonflag = "true";
                                }*/

                                    //  Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            if (error.getMessage().equalsIgnoreCase("401 Unauthorized")) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "Please check you must be signed into the some other device", Toast.LENGTH_SHORT).show();

                                logout();
                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "Internal Server Error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }

    }

    private void submitPracticeTest() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");
                Map<String, String> params = new HashMap<String, String>();

                params.put("id", mock_id);

                params.put("answer", mapstring);

                params.put("question_paper_submission_duration", timeleftformated);
                // params.put("question_paper_submission_duration", time_taken);
                params.put("submission_duration", time_taken);
                params.put("section_position", section_position);
                section_name = section_name.toLowerCase();
                params.put("section_name", section_name);

                params.put("question_paper_position", question_paper_position);
                if (timercount) {
                    params.put("question_number", String.valueOf(optionNo));
                } else {
                    params.put("question_number", String.valueOf(question_number));
                }
                params.put("question_paper_id", question_paper_id);

                //params.put("auth_token", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_TOKEN));

                RetrofitAPI.getInstance(this).getApi().submitMockTest(params, new Callback<MockTestSubmitResponse>() {
                    @Override
                    public void success(MockTestSubmitResponse object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {

                                try {
                                    PreferenceMangagerForTest.clearPreferences(MockActiveTestActivity.this);
                                    // generateDataList(response.body());
                                    map.clear();
                                    mapstring = "";
                                    next = true;
                                    qwe = true;
                                    temp = true;
                                    timercount = false;
                                    section_name = "";
                                    last_question = "";
                                    last_question = object.getLast_question().toString();
                                    submission_id = object.getSubmissionId().toString();
                                    question_number = object.getQuestionNumber();
                                    optionNo = object.getQuestionNumber();
                                    section_position = object.getSectionPosition().toString();
                                    section_name = object.getSectionName().toString();
                                    section_name = section_name.toUpperCase();
                                    question_paper_position = object.getQuestionPaperPosition().toString();
                                    mock_id = object.getId().toString();
                                    question_paper_id = object.getQuestion_paper_id();
                                    duration_taken = object.getSubmission_duration();
                                    duration = "";
                                    duration = object.getQuestion_paper_submission_duration();
                                    Log.e("duration_taken", "====" + duration_taken);
                                    Log.e("time_taken", "====" + time_taken);
                                    if (timeleftformated.equalsIgnoreCase(duration)) {
                                        timetakestatus = true;

                                    } else {
                                        time_taken = "";
                                        timetakestatus=false;
                                    }
                                    duration = duration.replace(" hrs", "");
                                    //String time = "01:02:30"; //mm:ss
                                    String[] units = duration.split(":"); //will break the string up into an array
                                    int hour = Integer.parseInt(units[0]);
                                    int minutes = Integer.parseInt(units[1]); //first element
                                    int seconds = Integer.parseInt(units[2]); //second element
                                    int time = (hour * 3600) + (60 * minutes) + seconds;
                                    Log.e("time", "====" + time);
                                    totaltime = Integer.parseInt(String.valueOf(time));
                                    timeleft = Integer.parseInt(String.valueOf(time)) * 1000;

                                    mtimeleftinmiles = timeleft;

                                    mainResponseArrayList.clear();
                                    optionArrayList.clear();
                                    mainResponseArrayList.addAll(object.getTests());

                                    //secondResponseArrayList.addAll(mainResponseArrayList);
                                    for (int i = 0; i < mainResponseArrayList.size(); i++) {
                                        try {
                                            optionArrayList.add(mainResponseArrayList.get(i));
                                            for (int j = 0; j < optionArrayList.size(); j++) {
                                                try {
                                                    String que = optionArrayList.get(j).getQuestion();
                                                    String sec = optionArrayList.get(j).getSectionName();
                                                    Integer id = optionArrayList.get(j).getId();
                                                    String que_type = optionArrayList.get(j).getQuestionType();
                                                    String dec = optionArrayList.get(j).getDescription();
                                                    String passage = optionArrayList.get(j).getPassage();
                                                    //  String aud = optionArrayList.get(j).get();

                                                    //String ans = optionArrayList.get(j).getCorrectIndex();
                                                    Log.e("getCorrectIndex", "---" + que_type);
                                                    temporaryOptionArrayList.clear();
                                                    temporaryOptionArrayList.addAll(optionArrayList.get(j).getOptions());
                                                    Log.e("temporaryOptionList", "---" + temporaryOptionArrayList);
                                                    PracticeTest qn = new PracticeTest(sec, id, que_type, que, temporaryOptionArrayList, dec, passage);
                                                    // questionList.clear();

                                                    questionList.add(qn);

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    if (duration.equalsIgnoreCase("00:00:00")) {
                                        if (last_question.equalsIgnoreCase("true")) {
                                            submitPracticeTest();
                                            mockTestFinalResult();
                                        } else {
                                       /* for (int i = 0; i <mainResponseArrayList.size() ; i++) {
                                            question_number=question_number+1;
                                        }*/
                                            timercount = true;
                                            Log.e("optionNobefor add SL", "===" + optionNo);
                                            if (mainResponseArrayList.size() <= 0) {
                                                optionNo = optionNo + 0;
                                            } else {
                                                optionNo = optionNo + mainResponseArrayList.size() - 1;
                                            }
                                            Log.e("optionNoafter SL", "===" + optionNo);
                                            Log.e("mainResponseList SL", "===" + mainResponseArrayList.size());
                                            submitPracticeTest();

                                            UIUtil.stopProgressDialog(MockActiveTestActivity.this);
                                        }
                                    } else {


                                        Log.e("mainResponsessss", "---" + optionArrayList.size());
                                        Log.e("questionListsssss", "---" + questionList.size());
                                        i = 0;
                                        valueforsection = i;
                                        valueforaudio = i;
                                        nextqeustion(questionList, i);
                                        if (i <= 0) {
                                            prevBtn.setVisibility(View.INVISIBLE);
                                        } else {
                                            prevBtn.setVisibility(View.VISIBLE);
                                        }

                                        countDownTimer = new CountDownTimer(mtimeleftinmiles, 1000) {
                                            public void onTick(long millisUntilFinished) {
                                                mtimeleftinmiles = millisUntilFinished;
                                                updateCountDownTimer();
                                            }

                                            public void onFinish() {
                                                // dialogBox();
                                                //nextqeustion(questionList, i);
                                            /*    for (int i = 0; i <mainResponseArrayList.size() ; i++) {
                                                    question_number=question_number+1;
                                                }*/
                                                timercount = true;
                                                Log.e("optionNobefor add LL", "===" + optionNo);
                                                if (mainResponseArrayList.size() <= 0) {
                                                    optionNo = optionNo + 0;
                                                } else {
                                                    optionNo = optionNo + mainResponseArrayList.size() - 1;
                                                }
                                                Log.e("optionNoafter LL", "===" + optionNo);
                                                Log.e("mainResponseList LL", "===" + mainResponseArrayList.size());

                                                if (last_question.equalsIgnoreCase("true")) {
                                                    submitPracticeTest();
                                                    mockTestFinalResult();
                                                    endQuiz();
                                                } else {
                                                    submitPracticeTest();
                                                }

                                            }
                                        }.start();

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            } else {
                                Toast.makeText(getApplicationContext(), "Please check you must be signed into the some other device", Toast.LENGTH_SHORT).show();
                                UIUtil.stopProgressDialog(getApplicationContext());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            if (error.getMessage().equalsIgnoreCase("401 Unauthorized")) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "Please check you must be signed into the some other device", Toast.LENGTH_SHORT).show();

                                logout();
                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "Internal Server Error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitSpeakingPracticeTest() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");
                Map<String, String> params = new HashMap<String, String>();

                // JsonObject jsonObject = new JsonObject();

                //jsonObject.addProperty("submission_id", submission_id);
                //jsonObject.addProperty("answer", mapstring.toString());

                params.put("submission_id", submission_id);

                // params.put("attachment", null);

                params.put("submission_duration", time_taken);

                //params.put("auth_token", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_TOKEN));

                RetrofitAPI.getInstance(this).getApi().submitSpeakingPracticeTest(params, new Callback<PracticeTestResult>() {
                    @Override
                    public void success(PracticeTestResult object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {
                                try {
                                    UIUtil.stopProgressDialog(getApplicationContext());

                                    //totalQuestionView.setText("Toatal Question : " + object.getTotalQuestions());
                                    // correctAnswerView.setText("Correct Answer : " + object.getCorrectAnswers());
                                    //percentageView.setText("Percentage : " + object.getPercentage());
                                    //timeTakeView.setText("Time Taken : " + object.getTimeTaken());
                                    //totalQuestionView.setVisibility(View.GONE);
                                    // correctAnswerView.setVisibility(View.GONE);
                                    //percentageView.setVisibility(View.GONE);
                                    //timeTakeView.setVisibility(View.GONE);

                                    // responseView.setText(object.getMessage());

                                    Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            if (error.getMessage().equalsIgnoreCase("401 Unauthorized")) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "Please check you must be signed into the some other device", Toast.LENGTH_SHORT).show();

                                logout();
                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "Internal Server Error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (testfinshed == true) {
            endQuiz();
            mediaPlayer.stop();
        } else {
            submitPracticeTest();
            mockTestFinalResult();
            //resultview.setVisibility(View.VISIBLE);
            endQuiz();
            mediaPlayer.stop();

        }

   /*     if (sectionName.equalsIgnoreCase("Speaking")) {

            submitSpeakingPracticeTest();

        } else if (sectionName.equalsIgnoreCase("Writing")) {

            submitPracticeTestForWriting();

        } else {

            submitPracticeTest();
        }*/
        Log.e("onPause", "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // mediaPlayer.stop();
        Log.e("onResume", "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //     mediaController.hide();
        //finish();
        Log.e("onStop", "onStop");
        //endQuiz();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mediaPlayer.stop();
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

    public void MediaRecorderReady() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(MockActiveTestActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(MockActiveTestActivity.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MockActiveTestActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

    private String getHumanTimeText(long milliseconds) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
    }

    private Runnable mUpdateTaskup = new Runnable() {
        public void run() {
            counter++;
            //textviewone.setText(String.valueOf(counter));
            Log.i("repeatBtn", "repeat click");
            mHandler.postAtTime(this, SystemClock.uptimeMillis() + 100);
        }//end run
    };// end runnable

    private void submitSpeakTest() {
        String token = PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_TOKEN);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Token token=" + token.replace("\"", ""));

        file = new File(AudioSavePathInDevice);

        progressDialog = new ProgressDialog(MockActiveTestActivity.this);
        progressDialog.setMessage("Uploading Audio..");
        progressDialog.setCancelable(false);
        progressDialog.show();
      /*  params.put("submission_id", submission_id);

        params.put("answer", mapstring);

        params.put("submission_duration", time_taken);*/
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part attachment =
                MultipartBody.Part.createFormData("attachment", file.getName(), requestFile);
    /*    MultipartBody.Part answer =
                MultipartBody.Part.createFormData("answer", AudioSavePathInDevice);*/
        MultipartBody.Part submissionid =
                MultipartBody.Part.createFormData("question_paper_id", question_paper_id);
       /* MultipartBody.Part submissionduration =
                MultipartBody.Part.createFormData("submission_duration", time_taken);*/
        MultipartBody.Part questionid =
                MultipartBody.Part.createFormData("question_id", question_id);
        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        RetrofitAPIInterface getResponse = RetrofitAPI.getRetrofit().create(RetrofitAPIInterface.class);
        Call<CommonResponse> resultCall = getResponse.uploadAudioForMoctTest(headers, attachment, submissionid, questionid);

        Log.e("body", "=====" + attachment);

        resultCall.enqueue(new retrofit2.Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, retrofit2.Response<CommonResponse> response) {
                try {
                    //progressDialog.dismiss();
                    // Log.e("response1", "==" + response.body().getLearner());
                    // Log.e("call1", "==" + call);
                    // Response Success or Fail
                    if (response.isSuccessful()) {
                        try {
                            if (response.body().getStatus().equals("success")) {
                                try {
                                    progressDialog.dismiss();
                    /*    totalQuestionView.setText("Toatal Question : " + object.getTotalQuestions());
                        correctAnswerView.setText("Correct Answer : " + object.getCorrectAnswers());
                        percentageView.setText("Percentage : " + object.getPercentage());
                        timeTakeView.setText("Time Taken : " + object.getTimeTaken());
                        Log.e("response", "==" + response.toString());*/
                                    Log.e("call", "==" + call);
                                    Toast.makeText(getApplicationContext(), "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "try some time latter", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "try some time latter", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                try {
                    if (t.getMessage().equalsIgnoreCase("401 Unauthorized")) {
                        logout();
                    }
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Internal server error", Toast.LENGTH_SHORT).show();
                    Log.e("error", "-----" + t.getCause());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
/*    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mScaleGestureDetector.onTouchEvent(event);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        // when a scale gesture is detected, use it to resize the image
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            imageFile.setScaleX(mScaleFactor);
            imageFile.setScaleY(mScaleFactor);
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));
            return true;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);
        mScaleGestureDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        return gestureDetector.onTouchEvent(event);
    }

//step 4: add private class GestureListener

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            // double tap fired.
            return true;
        }
    }*/


    public void descriptionDilogbox(int index) {
        LayoutInflater li = LayoutInflater.from(this);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.practice_description_view, null);

        descriptionView = confirmDialog.findViewById(R.id.descriptionView);
        descriptionView.loadData("", "text/html", "UTF-8");
        //descriptionView.setText(optionArrayList.get(index).getdescription);
        // cancelBtn = confirmDialog.findViewById(R.id.cancelBtn);

        descriptionView.loadData(optionArrayList.get(index).getDescription().toString(), "text/html; charset=utf-8", "UTF-8");


        //Creating an alertdialog builder
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(this);
        alert.setCancelable(false);
        //Adding our dialog box to the view of alert dialog
        alert.setTitle("Desciption");
        alert.setView(confirmDialog);


        alert.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        android.app.AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    public void passageDilogbox(int index) {
        LayoutInflater li = LayoutInflater.from(this);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.practice_description_view, null);
        descriptionView = confirmDialog.findViewById(R.id.descriptionView);
        descriptionView.loadData("", "text/html", "UTF-8");
        //descriptionView.setText(optionArrayList.get(index).getdescription);
        // cancelBtn = confirmDialog.findViewById(R.id.cancelBtn);

        descriptionView.loadData(optionArrayList.get(index).getPassage().toString(), "text/html; charset=utf-8", "UTF-8");


        //Creating an alertdialog builder
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(this);
        alert.setCancelable(false);
        //Adding our dialog box to the view of alert dialog
        alert.setTitle("Passage");
        alert.setView(confirmDialog);


        alert.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        android.app.AlertDialog alertDialog = alert.create();
        alertDialog.show();

    }

    public void sectionQuestionDilogbox(int index) {
        LayoutInflater li = LayoutInflater.from(this);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.practice_description_view, null);

        descriptionView = confirmDialog.findViewById(R.id.descriptionView);
        //descriptionView.setRequestCodeforFilepicker(5);
        descriptionView.loadData("", "text/html", "UTF-8");
        //   descriptionView.setText(optionArrayList.get(index).getpassage);
        // cancelBtn = confirmDialog.findViewById(R.id.cancelBtn);
        descriptionView.loadData(optionArrayList.get(index).getSectionQuestion().toString(), "text/html; charset=utf-8", "UTF-8");

        //Creating an alertdialog builder
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(this);
        alert.setCancelable(false);
        //Adding our dialog box to the view of alert dialog
        alert.setTitle("Section Question");
        alert.setView(confirmDialog);

        //Creating an alert dialog
        alert.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        android.app.AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    public void sectionDescriptionDilogbox(int index) {
        LayoutInflater li = LayoutInflater.from(this);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.practice_description_view, null);

        descriptionView = confirmDialog.findViewById(R.id.descriptionView);
        descriptionView.loadData("", "text/html", "UTF-8");
        //descriptionView.setText(optionArrayList.get(index).getdescription);
        //  cancelBtn = confirmDialog.findViewById(R.id.cancelBtn);

        descriptionView.loadData(optionArrayList.get(index).getSectionDescription().toString(), "text/html; charset=utf-8", "UTF-8");

        //Creating an alertdialog builder
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(this);
        alert.setCancelable(false);
        //Adding our dialog box to the view of alert dialog
        alert.setTitle("Section Desciption");
        alert.setView(confirmDialog);


        alert.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        android.app.AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    public void dialogBoxForFinalSubmit() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setCancelable(false);
        builder1.setTitle("Confirmation");
        builder1.setMessage("Are you sure you want to submit this test");
        // builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Submit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {

                            Log.e("map", "====" + map);
                            mediaPlayer.stop();
                            mapstring = map.toString();
                            mapstring = mapstring.replace("=", "=>");
                            Log.e("mapstring", "===" + mapstring);
                            //   Log.e("mainmap", "====" + mainmap);

                            fileString = mapfile.toString();
                            fileString = fileString.replace("=", "=>");
                            Log.e("mapstring", "===" + fileString);
                            //   Log.e("mainmap", "====" + mainmap);
                            //endQuiz();

                            //resultview.setVisibility(View.VISIBLE);
                            endQuiz();
                            Log.e("totaltime", "---" + String.format("%02d:%02d:%02d", hour, min, sec));
                            int rhour = hour * 60 * 60;
                            int rmin = min * 60;
                            int rsec = sec;
                            int remaingtime = rhour + rmin + rsec;
                            int ttt = totaltime - remaingtime;
                            Log.e("remaingtime", "---" + remaingtime);
                            Log.e("ttt", "---" + ttt);
                            rsec = ttt % 60;
                            rmin = (ttt / 60) % 60;
                            rhour = (ttt / 60) / 60;

                            Log.e("takentime", "---" + String.format("%02d:%02d:%02d", rhour, rmin, rsec));
                            time_taken = String.format("%02d:%02d:%02d", rhour, rmin, rsec);
                            time_taken = time_taken.replace("-", "");

                            if(timetakestatus==true){
                                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                                timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                                Date date1 = timeFormat.parse(time_taken);
                                Date date2 = timeFormat.parse(duration_taken);

                                long sum = date1.getTime() + date2.getTime();

                                time_taken = timeFormat.format(new Date(sum));
                                System.out.println("The sum is " + time_taken);
                            }
                            submitPracticeTest();
                            mockTestFinalResult();

                           /* if (sectionName.equalsIgnoreCase("Speaking")) {

                                submitSpeakingPracticeTest();
                            } else if (sectionName.equalsIgnoreCase("Writing")) {

                                submitPracticeTestForWriting();
                            } else {
                                submitPracticeTest();
                            }*/
                            //submitSpeakTest();
                            countDownTimer.cancel();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    public void dialogBoxForOptionalSubmit() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setCancelable(false);
        builder1.setTitle("Confirmation");
        builder1.setMessage("Are you sure you want to go to next page");
        // builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Submit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            Log.e("map", "====" + map);
                            mediaPlayer.stop();
                            mapstring = map.toString();
                            mapstring = mapstring.replace("=", "=>");
                            Log.e("mapstring", "===" + mapstring);
                            //   Log.e("mainmap", "====" + mainmap);

                            fileString = mapfile.toString();
                            fileString = fileString.replace("=", "=>");
                            Log.e("mapstring", "===" + fileString);
                            //   Log.e("mainmap", "====" + mainmap);
                            //endQuiz();
                            //time_taken = "";
                            Log.e("totaltime", "---" + String.format("%02d:%02d:%02d", hour, min, sec));
                            int rhour = hour * 60 * 60;
                            int rmin = min * 60;
                            int rsec = sec;
                            int remaingtime = rhour + rmin + rsec;
                            int ttt = totaltime - remaingtime;
                            Log.e("remaingtime", "---" + remaingtime);
                            Log.e("ttt", "---" + ttt);
                            rsec = ttt % 60;
                            rmin = (ttt / 60) % 60;
                            rhour = (ttt / 60) / 60;

                            Log.e("takentime", "---" + String.format("%02d:%02d:%02d", rhour, rmin, rsec));
                            time_taken = String.format("%02d:%02d:%02d", rhour, rmin, rsec);
                            time_taken = time_taken.replace("-", "");

                            if(timetakestatus==true){
                                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                                timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

                                Date date1 = timeFormat.parse(time_taken);
                                Date date2 = timeFormat.parse(duration_taken);

                                long sum = date1.getTime() + date2.getTime();

                                time_taken = timeFormat.format(new Date(sum));
                                System.out.println("The sum is " + time_taken);
                            }
                            submitPracticeTest();


                           /* if (sectionName.equalsIgnoreCase("Speaking")) {

                                submitSpeakingPracticeTest();
                            } else if (sectionName.equalsIgnoreCase("Writing")) {

                                submitPracticeTestForWriting();
                            } else {
                                submitPracticeTest();
                            }*/
                            //submitSpeakTest();
                            countDownTimer.cancel();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        dialogBoxForQuitTest();
    }

    public void dialogBoxForQuitTest() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setCancelable(false);
        builder1.setTitle("Confirmation");
        builder1.setMessage("Are you sure you want to quit this test?");
        // builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // mediaPlayer.stop();
                        //dialogBox();
                        submitPracticeTest();
                        mockTestFinalResult();
                        //resultview.setVisibility(View.VISIBLE);
                        //endQuiz();
                        mediaPlayer.stop();
                        //resultview.setVisibility(View.VISIBLE);
                        //endQuiz();
                       /* if (sectionName.equalsIgnoreCase("Speaking")) {

                            submitSpeakingPracticeTest();
                        } else if (sectionName.equalsIgnoreCase("Writing")) {

                            submitPracticeTestForWriting();
                        } else {
                            submitPracticeTest();
                        }*/
                        Intent intent = new Intent(getApplicationContext(), MockTestCountActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //intent.setAction(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.putExtra("mock_test_id", mock_test_cat_id);
                        //  intent.putExtra("section_name", sectionName);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
                    }
                });
        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    private void mockTestFinalResult() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");
                Map<String, String> params = new HashMap<String, String>();

                params.put("id", mock_id);


                RetrofitAPI.getInstance(this).getApi().getMockTestResult(params, new Callback<MockTestResultResponse>() {
                    @Override
                    public void success(MockTestResultResponse object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {
                                testfinshed = true;

                                UIUtil.stopProgressDialog(getApplicationContext());
                                mockTestResultViewArrayList.clear();
                                endQuiz();
                                mock_test_cat_id = object.getMock_test_category_id();
                                mockTestResultViewArrayList.addAll(object.getResult());
                                if (mockTestResultViewArrayList.size() <= 0) {
                                    mockTestView.setVisibility(View.GONE);
                                    mockTestView.setClickable(false);
                                    tv_nodata.setVisibility(View.VISIBLE);
                                    tv_nodata.setText("Your test is complete as it has been interrupted.");
                                } else {
                                    mockTestView.setVisibility(View.VISIBLE);
                                    mockTestView.setClickable(false);
                                    tv_nodata.setVisibility(View.GONE);
                                    tv_nodata.setText("");

                                }
                                mockTestResultAdapter.notifyDataSetChanged();
                                mockTestResultAdapter.SetOnItemClickListener(new MockTestResultAdapter.OnItemClickListener() {
                                    @Override
                                    public void onView(View view, MockResult position) {

                                    }
                                });
                                Log.e("data", "---" + mockTestResultViewArrayList.toString());

                              /*  arrayList.clear();
                                arrayList.addAll(object.getAssignedBatchesIds());
                                if (arrayList.size() > 0) {
                                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.ENROLL_STATUS, "true");
                                    //commonflag = "true";
                                }*/
                                //Log.e("arr", "===" + arrayList);

                                // Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                tv_nodata.setVisibility(View.VISIBLE);
                                mockTestView.setVisibility(View.GONE);
                                mockTestView.setClickable(false);
                                tv_nodata.setText(object.getMessage());
                                //Toast.makeText(getApplicationContext(), "No Data Available", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        try {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            if (error.getMessage().equalsIgnoreCase("401 Unauthorized")) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "Please check you must be signed into the some other device", Toast.LENGTH_SHORT).show();

                                logout();
                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "Internal Server Error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
