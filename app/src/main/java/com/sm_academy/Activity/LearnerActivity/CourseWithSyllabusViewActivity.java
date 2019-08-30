package com.sm_academy.Activity.LearnerActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.sm_academy.API.RetrofitAPI;
import com.sm_academy.Activity.BaseActivity;
import com.sm_academy.Activity.DefaultActivity.MainLandingActivity;
import com.sm_academy.Adapters.CourseWithSyllabusAdapter;

import com.sm_academy.Adapters.StudymaterialViewAdapter;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.CourseWithSyallabus;
import com.sm_academy.ModelClass.CourseWithSyllabusDetails;
import com.sm_academy.ModelClass.CourseWithSyllabusDetailsResponse;
import com.sm_academy.ModelClass.StudyMaterialView.StudyMaterialsDetails;
import com.sm_academy.R;
import com.sm_academy.Utility.Constants;
import com.sm_academy.Utility.UIUtil;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import pub.devrel.easypermissions.EasyPermissions;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CourseWithSyllabusViewActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    private RecyclerView studyMaterialView;
    private ArrayList<String> studyMaterialResponseArraylist;
    private ArrayList<CourseWithSyllabusDetails> studyMaterialArraylist, mainArraylist;
    private ArrayList<CourseWithSyallabus> coursesArraylistWithSyallabus;
    private CourseWithSyallabus courseWithSyallabus = new CourseWithSyallabus();
    private CourseWithSyllabusAdapter courseWithSyllabusAdapter;
    private Context context = this;

    private CourseWithSyllabusDetails courseWithSyllabusDetails;
    private DownloadManager downloadManager;
    private long reference;
    private String url;
    private static final int WRITE_REQUEST_CODE = 300;
    private static final String TAG = CourseWithSyllabusViewActivity.class.getSimpleName();
    String course_id, header;
    private TextView tv_nodata;
    private ActionBar actionBar;
    private ArrayList<CourseWithSyllabusDetails> audioArraylist, videoArraylist, pdfArraylist, docArraylist, imageArraylist;
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_materials_view);

        actionBar = getSupportActionBar();

        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_DOC_CHECK, "false");
        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_IMAGE_CHECK, "false");
        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_VIDEO_CHECK, "false");
        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_AUDIO_CHECK, "false");
        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_ALL_CHECK, "false");

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        studyMaterialView = findViewById(R.id.studyMaterialView);
        tv_nodata = findViewById(R.id.tv_nodata);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        studyMaterialArraylist = new ArrayList();
        studyMaterialResponseArraylist = new ArrayList<>();

        audioArraylist = new ArrayList<>();
        videoArraylist = new ArrayList<>();
        pdfArraylist = new ArrayList<>();
        docArraylist = new ArrayList<>();
        imageArraylist = new ArrayList<>();
        mainArraylist = new ArrayList<>();

        Intent intent = getIntent();
        course_id = intent.getStringExtra("course_id");
        header = intent.getStringExtra("header");
        Log.e("course_id", "" + course_id);
        actionBar.setTitle("About " + header);
        courseWithSyllabusDetails();

        studyMaterialView.setHasFixedSize(true);
        studyMaterialView.setLayoutManager(new LinearLayoutManager(this));
        courseWithSyllabusAdapter = new CourseWithSyllabusAdapter(this, studyMaterialArraylist, context);
        studyMaterialView.setAdapter(courseWithSyllabusAdapter);
        courseWithSyllabusAdapter.notifyDataSetChanged();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                courseWithSyllabusDetails();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        courseWithSyllabusAdapter.SetOnItemClickListener(new CourseWithSyllabusAdapter.OnItemClickListener() {

            @Override
            public void onbtnDownLoadClick(View view, CourseWithSyllabusDetails position) {
                courseWithSyllabusDetails = position;
                alertBox();
            }

            @Override
            public void onItemClick(View view, CourseWithSyllabusDetails position) {
                courseWithSyllabusDetails = position;

                if (position.getExtension().equals("mp4")) {
                    //fileDialog();
                    Intent intent = new Intent(getApplicationContext(), VideoPlayerActivity.class);
                    intent.putExtra("pos", courseWithSyllabusDetails.getAttachment());
                    // intent.putExtra("list", "0");
                    startActivity(intent);
                } else if (position.getExtension().equals("mp3")) {
                    Intent intent = new Intent(getApplicationContext(), AudioPlayer.class);
                    intent.putExtra("pos", courseWithSyllabusDetails.getAttachment());
                    intent.putExtra("about", courseWithSyllabusDetails.getAbout());
                    startActivity(intent);
                    /*try {
                        Uri uri = Uri.parse(courseWithSyllabusDetails.getAttachment());
                        MediaPlayer player = new MediaPlayer();
                        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        player.setDataSource(getApplicationContext(), uri);
                        player.prepare();
                        player.start();
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }*/
                } else if (position.getExtension().equalsIgnoreCase("png") ||
                        position.getExtension().equalsIgnoreCase("jpg") ||
                        position.getExtension().equalsIgnoreCase("jpeg") ||
                        position.getExtension().equalsIgnoreCase("gig")
                ) {
                    Intent intent = new Intent(getApplicationContext(), ProfileImageviewActivity.class);
                    intent.putExtra("name", position.getFileName());
                    intent.putExtra("image", position.getAttachment());

                    startActivity(intent);
                } else {
                    String format = "https://drive.google.com/viewerng/viewer?embedded=true&url=%s";
                    String fullPath = String.format(Locale.ENGLISH, format, courseWithSyllabusDetails.getAttachment());
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fullPath));
                    startActivity(browserIntent);
                }
            }
        });
        Log.e("Final course", "---" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME));


        Log.e("Final course", "---" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME));

    }

    private AlertDialog fileDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        LayoutInflater inflater = this.getLayoutInflater();
        View promptView = inflater.inflate(R.layout.file_dialog, null);


        VideoView videoView = (VideoView) promptView.findViewById(R.id.videoView);

        //String path="http://www.ted.com/talks/download/video/8584/talk/761";
        videoView.setVisibility(View.VISIBLE);
        String path1 = courseWithSyllabusDetails.getAttachment();

        Uri uri = Uri.parse(path1);


        videoView.setVideoURI(uri);
        videoView.start();
        builder.setView(promptView)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return builder.create();

    }

/*    @Override
    public void onContactSelected(CourseWithSyllabusDetails contact) {
        courseWithSyllabusDetails = contact;
        alertBox();
        //Intent intent = new Intent(getApplicationContext(), CourseWithSyllabusViewActivity.class);
        //intent.putExtra("id", contact.getId().toString());
        //startActivity(intent);
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_sort_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.menusearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                courseWithSyllabusAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                courseWithSyllabusAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    private void searchdilogbox() {

        AlertDialog.Builder builder = new AlertDialog.Builder(CourseWithSyllabusViewActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.activity_search_sort, null);
        //builder.setView(convertView);
        // builder.setTitle("List");

        builder.setView(convertView);

        //Displaying the alert dialog

        final CheckBox audioCb = convertView.findViewById(R.id.audioCb);
        final CheckBox videoCb = convertView.findViewById(R.id.videoCb);
        final CheckBox imageCb = convertView.findViewById(R.id.imageCb);
        final CheckBox docCb = convertView.findViewById(R.id.docCb);
        final CheckBox allCb = convertView.findViewById(R.id.allCb);

        if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_IMAGE_CHECK).equalsIgnoreCase("true")) {
            imageCb.setChecked(true);
        } else {
            imageCb.setChecked(false);
        }
        if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_DOC_CHECK).equalsIgnoreCase("true")) {
            docCb.setChecked(true);
        } else {
            docCb.setChecked(false);
        }
        if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_AUDIO_CHECK).equalsIgnoreCase("true")) {
            audioCb.setChecked(true);
        } else {
            audioCb.setChecked(false);
        }
        if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_VIDEO_CHECK).equalsIgnoreCase("true")) {
            videoCb.setChecked(true);
        } else {
            videoCb.setChecked(false);
        }
        if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_ALL_CHECK).equalsIgnoreCase("true")) {
            allCb.setChecked(true);
        } else {
            allCb.setChecked(false);
        }
        imageCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageCb.isChecked()) {
                    allCb.setChecked(false);
                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_ALL_CHECK, "false");
                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_IMAGE_CHECK, "true");

                    imageArraylist.clear();
                    studyMaterialArraylist.clear();
                    for (int j = 0; j < mainArraylist.size(); j++) {
                        if (mainArraylist.get(j).getExtension().contains("png") ||
                                mainArraylist.get(j).getExtension().contains("gif") ||
                                mainArraylist.get(j).getExtension().contains("jpg")
                                || mainArraylist.get(j).getExtension().contains("jpeg")) {
                            studyMaterialArraylist.add(mainArraylist.get(j));
                            imageArraylist.add(mainArraylist.get(j));
                        }

                        /*Intent intent = new Intent(getApplicationContext(), CalenderEventActivity.class);
                        intent.putExtra("list", calenderArraylist);
                        startActivity(intent);*/
                    }
                    courseWithSyllabusAdapter.notifyDataSetChanged();

                    if (videoCb.isChecked() == true) {
                        Log.e("videocheck", "------");
                        studyMaterialArraylist.addAll(videoArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("video no check", "------");
                        videoArraylist.clear();
                        studyMaterialArraylist.addAll(videoArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    }

                    if (audioCb.isChecked() == true) {
                        Log.e("videocheck", "------");
                        studyMaterialArraylist.addAll(audioArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("video no check", "------");
                        audioArraylist.clear();
                        studyMaterialArraylist.addAll(audioArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    }

                    if (docCb.isChecked() == true) {
                        Log.e("videocheck", "------");
                        studyMaterialArraylist.addAll(docArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("video no check", "------");
                        docArraylist.clear();
                        studyMaterialArraylist.addAll(docArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.e("aud notrche", "------");
                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_IMAGE_CHECK, "false");
                    studyMaterialArraylist.removeAll(imageArraylist);
                    courseWithSyllabusAdapter.notifyDataSetChanged();

                    if (imageCb.isChecked() == false && docCb.isChecked() == false && videoCb.isChecked() == false && allCb.isChecked() == false
                            && audioCb.isChecked() == false) {
                        studyMaterialArraylist.clear();
                        studyMaterialArraylist.addAll(mainArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    }
                }
            }
        });


        docCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (docCb.isChecked()) {
                    allCb.setChecked(false);
                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_ALL_CHECK, "false");
                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_DOC_CHECK, "true");
                    studyMaterialArraylist.clear();
                    docArraylist.clear();
                    for (int j = 0; j < mainArraylist.size(); j++) {
                        if (mainArraylist.get(j).getExtension().contains("docs") ||
                                mainArraylist.get(j).getExtension().contains("doc") ||
                                mainArraylist.get(j).getExtension().contains("xls") ||
                                mainArraylist.get(j).getExtension().contains("docx") ||
                                mainArraylist.get(j).getExtension().contains("xlsx") ||
                                mainArraylist.get(j).getExtension().contains("pdf")) {
                            studyMaterialArraylist.add(mainArraylist.get(j));
                            docArraylist.add(mainArraylist.get(j));
                        }


                        /*Intent intent = new Intent(getApplicationContext(), CalenderEventActivity.class);
                        intent.putExtra("list", calenderArraylist);
                        startActivity(intent);*/
                    }
                    courseWithSyllabusAdapter.notifyDataSetChanged();
                    if (videoCb.isChecked() == true) {
                        Log.e("videocheck", "------");
                        studyMaterialArraylist.addAll(videoArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("video no check", "------");
                        videoArraylist.clear();
                        studyMaterialArraylist.addAll(videoArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    }
                    if (audioCb.isChecked() == true) {
                        Log.e("videocheck", "------");
                        studyMaterialArraylist.addAll(audioArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("video no check", "------");
                        audioArraylist.clear();
                        studyMaterialArraylist.addAll(audioArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    }
                    if (imageCb.isChecked() == true) {
                        Log.e("videocheck", "------");
                        studyMaterialArraylist.addAll(imageArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("video no check", "------");
                        imageArraylist.clear();
                        studyMaterialArraylist.addAll(imageArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.e("aud notrche", "------");
                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_DOC_CHECK, "false");
                    studyMaterialArraylist.removeAll(docArraylist);
                    courseWithSyllabusAdapter.notifyDataSetChanged();

                    if (imageCb.isChecked() == false && docCb.isChecked() == false && videoCb.isChecked() == false && allCb.isChecked() == false
                            && audioCb.isChecked() == false) {
                        studyMaterialArraylist.clear();
                        studyMaterialArraylist.addAll(mainArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        allCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allCb.isChecked()) {

                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_ALL_CHECK, "true");

                    videoCb.setChecked(false);
                    imageCb.setChecked(false);
                    audioCb.setChecked(false);
                    docCb.setChecked(false);

                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_DOC_CHECK, "false");
                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_IMAGE_CHECK, "false");
                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_VIDEO_CHECK, "false");
                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_AUDIO_CHECK, "false");

                    studyMaterialArraylist.clear();
                    studyMaterialArraylist.addAll(mainArraylist);
                    courseWithSyllabusAdapter.notifyDataSetChanged();
                } else {
                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_ALL_CHECK, "false");
                    if (imageCb.isChecked() == false && docCb.isChecked() == false && videoCb.isChecked() == false && allCb.isChecked() == false
                            && audioCb.isChecked() == false) {
                        studyMaterialArraylist.clear();
                        studyMaterialArraylist.addAll(mainArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        audioCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audioCb.isChecked()) {
                    allCb.setChecked(false);
                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_ALL_CHECK, "false");
                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_AUDIO_CHECK, "true");
                    Log.e("audche", "------");
                    studyMaterialArraylist.clear();
                    audioArraylist.clear();
                    for (int j = 0; j < mainArraylist.size(); j++) {
                        if (mainArraylist.get(j).getExtension().contains("mp3")) {
                            studyMaterialArraylist.add(mainArraylist.get(j));
                            audioArraylist.add(mainArraylist.get(j));
                        }

                        /*Intent intent = new Intent(getApplicationContext(), CalenderEventActivity.class);
                        intent.putExtra("list", calenderArraylist);
                        startActivity(intent);*/
                    }
                    courseWithSyllabusAdapter.notifyDataSetChanged();
                    if (videoCb.isChecked() == true) {
                        Log.e("videocheck", "------");
                        studyMaterialArraylist.addAll(videoArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("video no check", "------");
                        videoArraylist.clear();
                        studyMaterialArraylist.addAll(videoArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    }

                    if (docCb.isChecked() == true) {
                        Log.e("doccheck", "------");
                        studyMaterialArraylist.addAll(docArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("doc no check", "------");
                        docArraylist.clear();
                        studyMaterialArraylist.addAll(docArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    }

                    if (imageCb.isChecked() == true) {
                        Log.e("imagecheck", "------");
                        studyMaterialArraylist.addAll(imageArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("image no check", "------");
                        imageArraylist.clear();
                        studyMaterialArraylist.addAll(imageArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    }

                } else {
                    Log.e("aud notrche", "------");
                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_AUDIO_CHECK, "false");
                    studyMaterialArraylist.removeAll(audioArraylist);
                    courseWithSyllabusAdapter.notifyDataSetChanged();

                    if (imageCb.isChecked() == false && docCb.isChecked() == false && videoCb.isChecked() == false && allCb.isChecked() == false
                            && audioCb.isChecked() == false) {
                        studyMaterialArraylist.clear();
                        studyMaterialArraylist.addAll(mainArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        videoCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (videoCb.isChecked()) {
                    allCb.setChecked(false);
                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_ALL_CHECK, "false");
                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_VIDEO_CHECK, "true");
                    Log.e("videocheck", "------");
                    videoArraylist.clear();
                    studyMaterialArraylist.clear();
                    for (int j = 0; j < mainArraylist.size(); j++) {
                        if (mainArraylist.get(j).getExtension().contains("mp4")) {
                            studyMaterialArraylist.add(mainArraylist.get(j));
                            videoArraylist.add(mainArraylist.get(j));
                        }

                        /*Intent intent = new Intent(getApplicationContext(), CalenderEventActivity.class);
                        intent.putExtra("list", calenderArraylist);
                        startActivity(intent);*/
                    }
                    courseWithSyllabusAdapter.notifyDataSetChanged();
                    if (audioCb.isChecked() == true) {
                        studyMaterialArraylist.addAll(audioArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                        Log.e("audche", "------");
                    } else {
                        audioArraylist.clear();
                        Log.e("aud notcheck", "------");
                        studyMaterialArraylist.removeAll(audioArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    }
                    if (docCb.isChecked() == true) {
                        Log.e("videocheck", "------");
                        studyMaterialArraylist.addAll(docArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("video no check", "------");
                        docArraylist.clear();
                        studyMaterialArraylist.addAll(docArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    }

                    if (imageCb.isChecked() == true) {
                        Log.e("videocheck", "------");
                        studyMaterialArraylist.addAll(imageArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("video no check", "------");
                        imageArraylist.clear();
                        studyMaterialArraylist.addAll(imageArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.e("videoNocheck", "------");
                    // videoArraylist.clear();
                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_VIDEO_CHECK, "false");
                    studyMaterialArraylist.removeAll(videoArraylist);
                    courseWithSyllabusAdapter.notifyDataSetChanged();

                    if (imageCb.isChecked() == false && docCb.isChecked() == false && videoCb.isChecked() == false && allCb.isChecked() == false
                            && audioCb.isChecked() == false) {
                        studyMaterialArraylist.clear();
                        studyMaterialArraylist.addAll(mainArraylist);
                        courseWithSyllabusAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        builder.setCancelable(false);

        builder.setView(convertView)
                .setNegativeButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // dialog.cancel();
                        Log.e("cancel", "--cancel-" + "cancel kilik");
                        dialog.dismiss();

                    }
                });

        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        // startActivity(new Intent(getApplicationContext(), LearnerDashBoardActivity.class));
        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            // startActivity(new Intent(getApplicationContext(), LearnerDashBoardActivity.class));
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                searchdilogbox();
                return true;
           /* case R.id.logout:
                showLogoutConfirmation();
                return true;
            case R.id.profile:
                // showLogoutConfirmation();
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.dashboard:
                if (id == R.id.dashboard)
                    // alertBox();
                    startActivity(new Intent(getApplicationContext(), LearnerDashBoardActivity.class));

                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void alertBox() {
        // builder.setTitle("Do you want to download this file ");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Setting message manually and performing action on button click
        builder.setTitle("Confirmation");
        builder.setMessage("Do you want to download this file ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                 /*       if (CheckForSDCard.isSDCardPresent()) {

                            //check if app has permission to write to the external storage.
                            if (EasyPermissions.hasPermissions(CourseWithSyllabusViewActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                //Get the URL entered
                                url = courseWithSyllabusDetails.getAttachment();
                                new DownloadFile().execute(url);

                            } else {
                                //If permission is not present request for the same.
                                EasyPermissions.requestPermissions(CourseWithSyllabusViewActivity.this, getString(R.string.write_file), WRITE_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
                            }


                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "SD Card not found", Toast.LENGTH_LONG).show();

                        }*/

                        startDownloadPdf(courseWithSyllabusDetails.getAttachment(), courseWithSyllabusDetails.getFileName());
                        // finish();
                        //Toast.makeText(getApplicationContext(), "you choose yes action for alertbox", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        // Toast.makeText(getApplicationContext(), "you choose no action for alertbox", Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually

        alert.show();

    }

    private void startDownloadPdf(String path, String subject) {
        if (downloadManager == null)
            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        Uri uri = Uri.parse(path);
        //http://www.axmag.com/download/pdfurl-guide.pdf
//        Uri uri = Uri.parse("http://www.axmag.com/download/pdfurl-guide.pdf");
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(courseWithSyllabusDetails.getFileName());
        //request.setDestinationInExternalPublicDir("/folder", "FileName.extention");
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "Course with Syllabus" + subject + courseWithSyllabusDetails.getFileName());
        request.setVisibleInDownloadsUi(true);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        reference = downloadManager.enqueue(request);
        Toast.makeText(getApplicationContext(), "Started Downloading ..", Toast.LENGTH_SHORT).show();
    }

    private void showLogoutConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

//        AlertDialog.Builder builder =
//                new AlertDialog.Builder(this, R.style.AppTheme_AppBarOverlay);

        builder.setTitle("Confirmation");
        String message = "Do you want to logout?";
        builder.setMessage(message);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();

            }
        });
        builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
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
                            //SugarContext.terminate();
                            //SchemaGenerator schemaGenerator = new SchemaGenerator(getApplicationContext());
                            //schemaGenerator.deleteTables(new SugarDb(getApplicationContext()).getDB());
                            //SugarContext.init(getApplicationContext());
                            // schemaGenerator.createDatabase(new SugarDb(getApplicationContext()).getDB());
                            //finish();
                            //startActivity(new Intent(TeacherLandingActivity.this, LoginActivity.class));
                            //overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
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
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainLandingActivity.class));
                        overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
                    }
                });
            } else {
                Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void courseWithSyllabusDetails() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");
                Map<String, String> params = new HashMap<String, String>();
                params.put("course_id", course_id);
                //params.put("course_id", String.valueOf(getdepartCategory(depart)));
                RetrofitAPI.getInstance(this).getApi().getcourseWithSyllabusDetails(params, new Callback<CourseWithSyllabusDetailsResponse>() {
                    @Override
                    public void success(CourseWithSyllabusDetailsResponse object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                bindData(object.getCourse());

                                Log.e("data", "---" + studyMaterialArraylist.toString());
                                //Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "No Data Available", Toast.LENGTH_SHORT).show();
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

    public void bindData(CourseWithSyallabus courseWithSyallabus) {
        studyMaterialArraylist.clear();
        mainArraylist.clear();
        mainArraylist.addAll(courseWithSyallabus.getAttachments());
        studyMaterialArraylist.addAll(courseWithSyallabus.getAttachments());
        if (studyMaterialArraylist.size() <= 0) {
            studyMaterialView.setVisibility(View.GONE);
            tv_nodata.setVisibility(View.VISIBLE);
            tv_nodata.setText("No Data Available");
            // Toast.makeText(getApplicationContext(), "No Data Available", Toast.LENGTH_SHORT).show();
            Log.e("zero size", "---" + studyMaterialArraylist);
        } else {
            tv_nodata.setVisibility(View.GONE);
            tv_nodata.setText("");
            studyMaterialView.setVisibility(View.VISIBLE);
        }
        courseWithSyllabusAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, CourseWithSyllabusViewActivity.this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //Download the file once permission is granted
        url = courseWithSyllabusDetails.getAttachment();
        new DownloadFile().execute(url);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");
    }

    private class DownloadFile extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        private String fileName;
        private String folder;
        private boolean isDownloaded;

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = new ProgressDialog(CourseWithSyllabusViewActivity.this);
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }


        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                // getting file length
                int lengthOfFile = connection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

                //Extract file name from URL
                fileName = f_url[0].substring(f_url[0].lastIndexOf('/') + 1, f_url[0].length());

                //Append timestamp to file name
                fileName = timestamp + "_" + fileName;

                //External directory path to save file
                folder = Environment.getExternalStorageDirectory() + File.separator + "androiddeft/";

                //Create androiddeft folder if it does not exist
                File directory = new File(folder);

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Output stream to write file
                OutputStream output = new FileOutputStream(folder + fileName);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lengthOfFile));
                    Log.d(TAG, "Progress: " + (int) ((total * 100) / lengthOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();
                return "Downloaded at: " + folder + fileName;

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return "Something went wrong";
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }


        @Override
        protected void onPostExecute(String message) {
            // dismiss the dialog after the file was downloaded
            this.progressDialog.dismiss();

            // Display File path after downloading
            Toast.makeText(getApplicationContext(),
                    message, Toast.LENGTH_LONG).show();
        }
    }
}

