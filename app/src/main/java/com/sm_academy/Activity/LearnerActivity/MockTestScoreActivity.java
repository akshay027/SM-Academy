package com.sm_academy.Activity.LearnerActivity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sm_academy.API.RetrofitAPI;
import com.sm_academy.Activity.DefaultActivity.MainLandingActivity;
import com.sm_academy.Activity.DefaultActivity.ProficiencyTestActivity;
import com.sm_academy.Adapters.MockTestResultAdapter;
import com.sm_academy.Adapters.MockTestScoreSubAdapter;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.PracticeTest.MockResult;
import com.sm_academy.ModelClass.PracticeTest.MockTestResultResponse;
import com.sm_academy.ModelClass.PracticeTest.PracticeTestResult;
import com.sm_academy.R;
import com.sm_academy.Utility.Constants;
import com.sm_academy.Utility.UIUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MockTestScoreActivity extends AppCompatActivity {
    private MockTestResultAdapter mockTestResultAdapter;
    private ArrayList mockTestResultViewArrayList;
    private RecyclerView mockTestView;
    private String id, page, SectionName;
    private TextView tv_nodata;
    private LinearLayout resultview;
    private TextView totalQuestionView, correctAnswerView, percentageView, delayView, timeTakenView;
    private Button btnView;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_test_score);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar = getSupportActionBar();
        resultview = findViewById(R.id.resultview);
        btnView = findViewById(R.id.btnView);

        Intent intent = getIntent();
        //course_id = intent.getStringExtra("id");
        // sectionName = intent.getStringExtra("section_name");
        totalQuestionView = findViewById(R.id.totalQuestionView);
        correctAnswerView = findViewById(R.id.correctAnswerView);
        percentageView = findViewById(R.id.percentageView);
        timeTakenView = findViewById(R.id.timeTakenView);

        delayView = findViewById(R.id.delayView);
        page = intent.getStringExtra("page");
        id = intent.getStringExtra("id");

        tv_nodata = findViewById(R.id.tv_nodata);
        mockTestView = findViewById(R.id.mockTestView);
        mockTestResultViewArrayList = new ArrayList();
        if (page.equalsIgnoreCase("Assignment")) {
            actionBar.setTitle("Assignment Score ");
            resultview.setVisibility(View.VISIBLE);
            mockTestView.setVisibility(View.GONE);
            tv_nodata.setVisibility(View.GONE);
            testScore(id);
        } else {
            actionBar.setTitle("Mock Test Score ");
            //resultview.setVisibility(View.GONE);
            // mockTestView.setVisibility(View.VISIBLE);
            mockTestFinalResult();
        }
        //btnDoneTest = convertView.findViewById(R.id.btnDoneTest);
        //  cancelBtn = convertView.findViewById(R.id.cancelBtn);
        mockTestView.setHasFixedSize(true);
        mockTestView.setLayoutManager(new LinearLayoutManager(this));
        mockTestResultAdapter = new MockTestResultAdapter(this, mockTestResultViewArrayList);
        mockTestView.setAdapter(mockTestResultAdapter);

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MockTestScoreAnswerActivity.class);
                intent.putExtra("id", id);

                intent.putExtra("section_name", "Assignment");
                startActivity(intent);
            }
        });


    }

    private void mockTestFinalResult() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");
                Map<String, String> params = new HashMap<String, String>();

                params.put("id", id);

                RetrofitAPI.getInstance(this).getApi().getMockTestResult(params, new Callback<MockTestResultResponse>() {
                    @Override
                    public void success(MockTestResultResponse object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {

                                UIUtil.stopProgressDialog(getApplicationContext());
                                mockTestResultViewArrayList.clear();
                                mockTestResultViewArrayList.addAll(object.getResult());
                                if (mockTestResultViewArrayList.size() <= 0) {
                                    mockTestView.setVisibility(View.GONE);
                                    tv_nodata.setVisibility(View.VISIBLE);
                                    resultview.setVisibility(View.GONE);
                                    tv_nodata.setText("No Data Available");
                                } else {
                                    mockTestView.setVisibility(View.VISIBLE);
                                    resultview.setVisibility(View.GONE);
                                    tv_nodata.setVisibility(View.GONE);
                                }
                                mockTestResultAdapter.notifyDataSetChanged();
                                mockTestResultAdapter.SetOnItemClickListener(new MockTestResultAdapter.OnItemClickListener() {
                                    @Override
                                    public void onView(View view, MockResult position) {

                                        SectionName = position.getSectionName();
                                        //mockTestScoreSubResult();
                                        Intent intent = new Intent(getApplicationContext(), MockTestScoreAnswerActivity.class);
                                        intent.putExtra("id", id);

                                        intent.putExtra("section_name", position.getSectionName());
                                        startActivity(intent);

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
                                //tv_nodata.setVisibility(View.VISIBLE);
                                mockTestView.setVisibility(View.GONE);
                                resultview.setVisibility(View.GONE);
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
                // showLogoutConfirmation();
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

    private void testScore(final String id) {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");
               /* JsonObject jsonObject = new JsonObject();

                jsonObject.addProperty("id", id);*/
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);

                //params.put("auth_token", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_TOKEN));

                RetrofitAPI.getInstance(this).getApi().gettestScore(params, new Callback<PracticeTestResult>() {
                    @Override
                    public void success(PracticeTestResult object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {
                                UIUtil.stopProgressDialog(getApplicationContext());

                                totalQuestionView.setText("Total Question :      " + object.getTotalQuestions());
                                correctAnswerView.setText("Correct Answer :     " + object.getCorrectAnswers());
                                percentageView.setText("Percentage :           " + object.getPercentage());
                                timeTakenView.setText("Time Taken :          " + object.getTimeTaken());

                                if (object.getTo_be_evaluated().toString().equalsIgnoreCase("false")) {
                                    totalQuestionView.setVisibility(View.VISIBLE);
                                    correctAnswerView.setVisibility(View.VISIBLE);
                                    percentageView.setVisibility(View.VISIBLE);
                                    timeTakenView.setVisibility(View.VISIBLE);
                                    // answerLvView.setVisibility(View.VISIBLE);*/
                                    delayView.setVisibility(View.GONE);
                                    Log.e("false", "====" + object.getTo_be_evaluated());
                                } else {
                                    totalQuestionView.setVisibility(View.GONE);
                                    correctAnswerView.setVisibility(View.GONE);
                                    percentageView.setVisibility(View.GONE);
                                    timeTakenView.setVisibility(View.GONE);
                                    delayView.setVisibility(View.VISIBLE);
                                    delayView.setText(object.getMessage());
                                    Log.e("true", "====" + object.getTo_be_evaluated());
                                }

                              /*  arrayList.clear();
                                arrayList.addAll(object.getAssignedBatchesIds());
                                if (arrayList.size() > 0) {
                                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.ENROLL_STATUS, "true");
                                    //commonflag = "true";
                                }*/

                                // Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();
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
}
