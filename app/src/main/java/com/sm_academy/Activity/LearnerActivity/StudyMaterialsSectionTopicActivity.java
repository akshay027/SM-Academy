package com.sm_academy.Activity.LearnerActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.sm_academy.API.RetrofitAPI;
import com.sm_academy.Activity.BaseActivity;
import com.sm_academy.Activity.DefaultActivity.MainLandingActivity;
import com.sm_academy.Adapters.CourseWithSyllabusAdapter;
import com.sm_academy.Adapters.StudyMaterialsSectionAdapter;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.CourseWithSyallabus;
import com.sm_academy.ModelClass.CourseWithSyllabusDetails;
import com.sm_academy.ModelClass.CourseWithSyllabusDetailsResponse;
import com.sm_academy.ModelClass.StudyMaterialsSectionTopics.SectionTopicDetails;
import com.sm_academy.ModelClass.StudyMaterialsSectionTopics.SectionTopicResponse;
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
import java.util.Map;

import pub.devrel.easypermissions.EasyPermissions;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class StudyMaterialsSectionTopicActivity extends BaseActivity implements StudyMaterialsSectionAdapter.ContactsAdapterListener {
    private RecyclerView studyMaterialView;
    private ArrayList<String> studyMaterialResponseArraylist;
    private ArrayList<SectionTopicDetails> studyMaterialArraylist;

    private StudyMaterialsSectionAdapter studyMaterialsSectionAdapter;
    private Context context = this;
    private TextView fielsName, fileCount;
    private LinearLayout fieldView;
    private ImageView openLockView;

    private long reference;

    String course_id;
    private ActionBar actionBar;
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_materials_section_topic);
        actionBar = getSupportActionBar();

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        studyMaterialView = findViewById(R.id.studyMaterialView);
        fielsName = findViewById(R.id.fielsName);
        openLockView = findViewById(R.id.openLockView);
        fileCount = findViewById(R.id.fileCount);
        fieldView = findViewById(R.id.fieldView);
        fieldView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_scale_animation));
        studyMaterialArraylist = new ArrayList();
        studyMaterialResponseArraylist = new ArrayList<>();

        Intent intent = getIntent();
        course_id = intent.getStringExtra("id");

        fielsName.setText(intent.getStringExtra("section_name") + " " + "Study Materials");
        Log.e("id", "" + course_id);

        actionBar.setTitle(intent.getStringExtra("readingcomponent") + " " + "Topics");

        studyMaterialDetails();

        studyMaterialView.setHasFixedSize(true);
        studyMaterialView.setLayoutManager(new LinearLayoutManager(this));
        studyMaterialsSectionAdapter = new StudyMaterialsSectionAdapter(this, studyMaterialArraylist, this, context);
        studyMaterialView.setAdapter(studyMaterialsSectionAdapter);
        studyMaterialsSectionAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                studyMaterialDetails();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        fieldView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_SECTION, "0");
                Intent intent = new Intent(getApplicationContext(), StudyMaterialViewActivity.class);

                intent.putExtra("id", course_id);
                intent.putExtra("section_id", "0");
                intent.putExtra("list", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SECTION));
                startActivity(intent);
            }
        });

        Log.e("Final course", "---" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.menusearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                studyMaterialsSectionAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                studyMaterialsSectionAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        //startActivity(new Intent(getApplicationContext(), LearnerDashBoardActivity.class));
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
        //noinspection SimplifiableIfStatement

        switch (item.getItemId()) {

            case R.id.logout:
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

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    private void studyMaterialDetails() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");
                Map<String, String> params = new HashMap<String, String>();
                params.put("section_id", course_id);
                //params.put("course_id", String.valueOf(getdepartCategory(depart)));
                RetrofitAPI.getInstance(this).getApi().getstudyMaterialDetails(params, new Callback<SectionTopicResponse>() {
                    @Override
                    public void success(SectionTopicResponse object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                studyMaterialArraylist.clear();
                                studyMaterialArraylist.addAll(object.getSectionTopics());
                                studyMaterialsSectionAdapter.notifyDataSetChanged();
                                fileCount.setText(object.getStudy_materials_count());
                                Log.e("data", "---" + studyMaterialArraylist.toString());
                                //  Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onContactSelected(SectionTopicDetails contact) {
        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_SECTION, "1");
        if (contact.getLocked() == true) {

        } else {
            Intent intent = new Intent(getApplicationContext(), StudyMaterialsPhaseViewActivity.class);
            intent.putExtra("id", contact.getId().toString());
            intent.putExtra("section_id", contact.getSectionId().toString());
            intent.putExtra("list", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SECTION));
            startActivity(intent);
        }

    }
}