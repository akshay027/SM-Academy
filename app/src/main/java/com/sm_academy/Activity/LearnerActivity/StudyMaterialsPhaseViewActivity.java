package com.sm_academy.Activity.LearnerActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.sm_academy.API.RetrofitAPI;
import com.sm_academy.Activity.DefaultActivity.MainLandingActivity;
import com.sm_academy.Activity.DefaultActivity.ProficiencyTestActivity;
import com.sm_academy.Adapters.MockTestViewListAdapter;
import com.sm_academy.Adapters.StudyMaterialsPhaseAdapter;
import com.sm_academy.Adapters.StudyMaterialsSectionAdapter;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.PracticeTest.MockTestList;
import com.sm_academy.ModelClass.PracticeTest.MockTestListResponse;
import com.sm_academy.ModelClass.PracticeTest.PracticeTestResult;
import com.sm_academy.ModelClass.StudyMaterialsSectionTopics.SectionTopicDetails;
import com.sm_academy.ModelClass.StudyMaterialsSectionTopics.StudyMaterialsPhase;
import com.sm_academy.ModelClass.StudyMaterialsSectionTopics.StudyMaterialsPhaseResponse;
import com.sm_academy.R;
import com.sm_academy.Utility.Constants;
import com.sm_academy.Utility.UIUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class StudyMaterialsPhaseViewActivity extends AppCompatActivity {
    private StudyMaterialsPhaseAdapter studyMaterialsPhaseAdapter;
    private RecyclerView studyMaterialsPhaseView;

    private ArrayList<StudyMaterialsPhase> studyMaterialsViewArrayList;
    String section_topic_id, section_id, sectionName, mock_test_id;

    private Context context = this;
    private List<Integer> arrayList;
    private TextView tv_nodata;
    private String list, commonflag;
    private ActionBar actionBar;

    Button cancelBtn;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studt_materials_phase_view);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        section_topic_id = intent.getStringExtra("id");
        section_id = intent.getStringExtra("section_id");
        list = intent.getStringExtra("list");

        studyMaterialsPhaseView = findViewById(R.id.studyMaterialsPhaseView);
        tv_nodata = findViewById(R.id.tv_nodata);
        studyMaterialsViewArrayList = new ArrayList();

        studyMaterialsPhaseViewDetails();

        studyMaterialsPhaseView.setHasFixedSize(true);
        studyMaterialsPhaseView.setLayoutManager(new LinearLayoutManager(this));
        studyMaterialsPhaseAdapter = new StudyMaterialsPhaseAdapter(this, studyMaterialsViewArrayList, context);
        studyMaterialsPhaseView.setAdapter(studyMaterialsPhaseAdapter);
        studyMaterialsPhaseAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                studyMaterialsPhaseViewDetails();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        Log.e("Final course", "---" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME));
        studyMaterialsPhaseAdapter.SetOnItemClickListener(new StudyMaterialsPhaseAdapter.OnItemClickListener() {

            @Override
            public void onView(View view, StudyMaterialsPhase position) {
                if (position.getLocked() == true) {

                } else {
                    Intent intent = new Intent(getApplicationContext(), StudyMaterialViewActivity.class);
                    intent.putExtra("id", position.getId().toString());
                    intent.putExtra("section_id", position.getSectionId().toString());
                    intent.putExtra("list", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_SECTION));
                    startActivity(intent);
                }
            }
        });
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
                studyMaterialsPhaseAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                studyMaterialsPhaseAdapter.getFilter().filter(query);
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
            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
            return true;
        }
/*        //noinspection SimplifiableIfStatement
        if (id == R.id.profile) {
            // alertBox();
        }
        if (id == R.id.dashboard) {
            // alertBox();
            startActivity(new Intent(getApplicationContext(), LearnerDashBoardActivity.class));
        }*/
        switch (item.getItemId()) {

            //noinspection SimplifiableIfStatement

            case R.id.logout:
                showLogoutConfirmation();
                return true;
            case R.id.profile:
                // showLogoutConfirmation();
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.dashboard:
                if (id == R.id.dashboard) {
                    // alertBox();
                    if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.PT_TAKEN).equalsIgnoreCase("true")) {
                        startActivity(new Intent(getApplicationContext(), LearnerDashBoardActivity.class));
                    } else {
                        startActivity(new Intent(getApplicationContext(), ProficiencyTestActivity.class));
                    }
                }
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

    private void studyMaterialsPhaseViewDetails() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");
                Map<String, String> params = new HashMap<String, String>();
                params.put("section_id", section_id);
                params.put("section_topic_id", section_topic_id);


                RetrofitAPI.getInstance(this).getApi().getstudyMaterialsPhaseResponse(params, new Callback<StudyMaterialsPhaseResponse>() {
                    @Override
                    public void success(StudyMaterialsPhaseResponse object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                studyMaterialsViewArrayList.clear();
                                studyMaterialsViewArrayList.addAll(object.getPhases());
                                if (studyMaterialsViewArrayList.size() <= 0) {
                                    studyMaterialsPhaseView.setVisibility(View.GONE);
                                    tv_nodata.setVisibility(View.VISIBLE);
                                    tv_nodata.setText("No Data Available");
                                } else {
                                    studyMaterialsPhaseView.setVisibility(View.VISIBLE);
                                    tv_nodata.setVisibility(View.GONE);
                                    tv_nodata.setText("");
                                }
                                studyMaterialsPhaseAdapter.notifyDataSetChanged();
                                Log.e("data", "---" + studyMaterialsViewArrayList.toString());

                              /*  arrayList.clear();
                                arrayList.addAll(object.getAssignedBatchesIds());
                                if (arrayList.size() > 0) {
                                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.ENROLL_STATUS, "true");
                                    //commonflag = "true";
                                }*/
                                Log.e("arr", "===" + arrayList);

                                // Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                tv_nodata.setVisibility(View.VISIBLE);
                                studyMaterialsPhaseView.setVisibility(View.GONE);
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
