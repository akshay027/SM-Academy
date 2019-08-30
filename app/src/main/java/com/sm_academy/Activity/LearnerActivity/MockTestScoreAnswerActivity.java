package com.sm_academy.Activity.LearnerActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sm_academy.API.RetrofitAPI;
import com.sm_academy.Activity.DefaultActivity.MainLandingActivity;
import com.sm_academy.Activity.DefaultActivity.ProficiencyTestActivity;
import com.sm_academy.Adapters.MockTestResultAdapter;
import com.sm_academy.Adapters.MockTestScoreSubAdapter;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.PracticeTest.MockTestFullScore;
import com.sm_academy.ModelClass.PracticeTest.MockTestFullScoreResponse;
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

public class MockTestScoreAnswerActivity extends AppCompatActivity {

    private MockTestScoreSubAdapter mockTestScoreSubAdapter;
    private ArrayList<MockTestFullScore> mockTestAnswerViewArrayList;
    private RecyclerView mockTestSubView;
    private String sectionName, id;
    private int pos;
    TextView feedBackView, scoreView, correctAnswerView, answerView;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_test_score_answer);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar = getSupportActionBar();
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        sectionName = intent.getStringExtra("section_name");

        if (sectionName.equalsIgnoreCase("Assignment")) {
            actionBar.setTitle("Assignment Answer ");
        } else {
            actionBar.setTitle("Mock Test Answer  ");
        }
        sectionName = sectionName.toLowerCase();

        mockTestAnswerViewArrayList = new ArrayList();

        correctAnswerView = findViewById(R.id.correctAnswerView);

        feedBackView = findViewById(R.id.feedBackView);

        answerView = findViewById(R.id.answerView);

        scoreView = findViewById(R.id.scoreView);

        feedBackView.setMovementMethod(new ScrollingMovementMethod());
        correctAnswerView.setMovementMethod(new ScrollingMovementMethod());
        answerView.setMovementMethod(new ScrollingMovementMethod());
        scoreView.setMovementMethod(new ScrollingMovementMethod());

        mockTestSubView = findViewById(R.id.mockTestSubView);
        if (sectionName.equalsIgnoreCase("assignment")) {
            mockTestScoreForAssignmentSubResult();
        } else {
            mockTestScoreSubResult();
        }

        //btnDoneTest = convertView.findViewById(R.id.btnDoneTest);
        //  cancelBtn = convertView.findViewById(R.id.cancelBtn);
        mockTestSubView.setHasFixedSize(true);
        mockTestSubView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mockTestScoreSubAdapter = new MockTestScoreSubAdapter(this, mockTestAnswerViewArrayList);
        mockTestSubView.setAdapter(mockTestScoreSubAdapter);


        mockTestScoreSubAdapter.SetOnItemClickListener(new MockTestScoreSubAdapter.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onView(View view, int position) {
                pos = position;
                // view.clearAnimation();
                //view.getFocusables(position);
                //view.setSelected(true);
                mockTestScoreSubAdapter.setSelectedIndex(position);
                mockTestScoreSubAdapter.notifyDataSetChanged();

                if (TextUtils.isEmpty(mockTestAnswerViewArrayList.get(position).getFeedback().toString())) {
                    feedBackView.setText("No FeedBack");
                } else {
                    feedBackView.setText(Html.fromHtml(mockTestAnswerViewArrayList.get(position).getFeedback().toString()));
                }
                if (TextUtils.isEmpty(mockTestAnswerViewArrayList.get(position).getAnswered())) {
                    answerView.setText("Not Answered");
                } else {
                    answerView.setText(Html.fromHtml(mockTestAnswerViewArrayList.get(position).getAnswered().toString()));
                }
                if (TextUtils.isEmpty(mockTestAnswerViewArrayList.get(position).getScore().toString())) {
                    scoreView.setText("No Score");
                } else {
                    scoreView.setText(Html.fromHtml(mockTestAnswerViewArrayList.get(position).getScore().toString()));
                }
                if (TextUtils.isEmpty(mockTestAnswerViewArrayList.get(position).getCorrectAnswer().toString())) {
                    correctAnswerView.setText("No Correct Answer");
                } else {
                    correctAnswerView.setText(Html.fromHtml(mockTestAnswerViewArrayList.get(position).getCorrectAnswer().toString()));
                }
                if (mockTestAnswerViewArrayList.get(position).getCorrectAnswerColor().equalsIgnoreCase("text-green")) {
                    answerView.setTextColor(getResources().getColor(R.color.green));
                } else {
                    answerView.setTextColor(getResources().getColor(R.color.red));
                }
            }
        });
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

        switch (item.getItemId()) {

            case R.id.logout:
                //   showLogoutConfirmation();
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

    private void mockTestScoreSubResult() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");
                Map<String, String> params = new HashMap<String, String>();

                params.put("id", id);
                params.put("section_name", sectionName);

                RetrofitAPI.getInstance(this).getApi().getMockTestSubResult(params, new Callback<MockTestFullScoreResponse>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void success(MockTestFullScoreResponse object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {

                                UIUtil.stopProgressDialog(getApplicationContext());
                                mockTestAnswerViewArrayList.clear();
                                mockTestAnswerViewArrayList.addAll(object.getScore());
                                if (mockTestAnswerViewArrayList.size() <= 0) {
                                    mockTestSubView.setVisibility(View.GONE);
                                    // tv_nodata.setVisibility(View.VISIBLE);
                                    // tv_nodata.setText("No Data Available");
                                } else {
                                    if (mockTestAnswerViewArrayList.get(0).getCorrectAnswerColor().equalsIgnoreCase("text-green")) {
                                        answerView.setTextColor(getResources().getColor(R.color.green));
                                    } else {
                                        answerView.setTextColor(getResources().getColor(R.color.red));
                                    }
                                    if (TextUtils.isEmpty(mockTestAnswerViewArrayList.get(0).getFeedback().toString())) {
                                        feedBackView.setText("No FeedBack");
                                    } else {
                                        feedBackView.setText(Html.fromHtml(mockTestAnswerViewArrayList.get(0).getFeedback().toString()));
                                    }
                                    if (TextUtils.isEmpty(mockTestAnswerViewArrayList.get(0).getAnswered())) {
                                        answerView.setText("Not Answered");
                                    } else {
                                        answerView.setText(Html.fromHtml(mockTestAnswerViewArrayList.get(0).getAnswered().toString()));
                                    }
                                    if (TextUtils.isEmpty(mockTestAnswerViewArrayList.get(0).getScore().toString())) {
                                        scoreView.setText("No Score");
                                    } else {
                                        scoreView.setText(Html.fromHtml(mockTestAnswerViewArrayList.get(0).getScore().toString()));
                                    }
                                    if (TextUtils.isEmpty(mockTestAnswerViewArrayList.get(0).getCorrectAnswer().toString())) {
                                        correctAnswerView.setText("No Correct Answer");
                                    } else {
                                        correctAnswerView.setText(Html.fromHtml(mockTestAnswerViewArrayList.get(0).getCorrectAnswer().toString()));
                                    }

                                    mockTestSubView.setVisibility(View.VISIBLE);
                                    // mockTestSubView.setVisibility(View.GONE);
                                    // tv_nodata.setText("");

                                }
                                mockTestScoreSubAdapter.notifyDataSetChanged();
                                Log.e("data", "---" + mockTestAnswerViewArrayList.toString());

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
                                mockTestSubView.setVisibility(View.GONE);
                                //tv_nodata.setText(object.getMessage());
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

    private void mockTestScoreForAssignmentSubResult() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");
                Map<String, String> params = new HashMap<String, String>();

                params.put("id", id);
                //params.put("section_name", sectionName);

                RetrofitAPI.getInstance(this).getApi().getMockTestSubResultForAssignment(params, new Callback<MockTestFullScoreResponse>() {
                    @Override
                    public void success(MockTestFullScoreResponse object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {

                                UIUtil.stopProgressDialog(getApplicationContext());
                                mockTestAnswerViewArrayList.clear();
                                mockTestAnswerViewArrayList.addAll(object.getScore());
                                if (mockTestAnswerViewArrayList.size() <= 0) {
                                    mockTestSubView.setVisibility(View.GONE);
                                    // tv_nodata.setVisibility(View.VISIBLE);
                                    // tv_nodata.setText("No Data Available");
                                } else {
                                    if (mockTestAnswerViewArrayList.get(0).getCorrectAnswerColor().equalsIgnoreCase("text-green")) {
                                        answerView.setTextColor(getResources().getColor(R.color.green));
                                    } else {
                                        answerView.setTextColor(getResources().getColor(R.color.red));
                                    }
                                    if (TextUtils.isEmpty(mockTestAnswerViewArrayList.get(0).getFeedback().toString())) {
                                        feedBackView.setText("No FeedBack");
                                    } else {
                                        feedBackView.setText(Html.fromHtml(mockTestAnswerViewArrayList.get(0).getFeedback().toString()));
                                    }
                                    if (TextUtils.isEmpty(mockTestAnswerViewArrayList.get(0).getAnswered())) {
                                        answerView.setText("Not Answered");
                                    } else {
                                        answerView.setText(Html.fromHtml(mockTestAnswerViewArrayList.get(0).getAnswered().toString()));
                                    }
                                    if (TextUtils.isEmpty(mockTestAnswerViewArrayList.get(0).getScore().toString())) {
                                        scoreView.setText("No Score");
                                    } else {
                                        scoreView.setText(Html.fromHtml(mockTestAnswerViewArrayList.get(0).getScore().toString()));
                                    }
                                    if (TextUtils.isEmpty(mockTestAnswerViewArrayList.get(0).getCorrectAnswer().toString())) {
                                        correctAnswerView.setText("No Correct Answer");
                                    } else {
                                        correctAnswerView.setText(Html.fromHtml(mockTestAnswerViewArrayList.get(0).getCorrectAnswer().toString()));
                                    }
                                    mockTestSubView.setVisibility(View.VISIBLE);
                                    // mockTestSubView.setVisibility(View.GONE);
                                    // tv_nodata.setText("");

                                }
                                mockTestScoreSubAdapter.notifyDataSetChanged();
                                Log.e("data", "---" + mockTestAnswerViewArrayList.toString());

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
                                mockTestSubView.setVisibility(View.GONE);
                                //tv_nodata.setText(object.getMessage());
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
