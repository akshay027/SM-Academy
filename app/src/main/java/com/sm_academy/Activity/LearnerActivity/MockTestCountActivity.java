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
import com.sm_academy.Activity.DefaultActivity.BatchTimingActivity;
import com.sm_academy.Activity.DefaultActivity.MainLandingActivity;
import com.sm_academy.Activity.DefaultActivity.ProficiencyTestActivity;

import com.sm_academy.Adapters.CalenderEventAdapter;
import com.sm_academy.Adapters.MockTestCountAdapter;
import com.sm_academy.Adapters.MockTestResultAdapter;
import com.sm_academy.Adapters.MockTestScoreSubAdapter;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.PracticeTest.MockResult;
import com.sm_academy.ModelClass.PracticeTest.MockTestCount;
import com.sm_academy.ModelClass.PracticeTest.MockTestCountResponse;
import com.sm_academy.ModelClass.PracticeTest.MockTestFullScore;
import com.sm_academy.ModelClass.PracticeTest.MockTestFullScoreResponse;
import com.sm_academy.ModelClass.PracticeTest.MockTestResultResponse;
import com.sm_academy.ModelClass.PracticeTest.PracticeTestResult;
import com.sm_academy.ModelClass.PracticeTest.PracticeTestTopicWise;
import com.sm_academy.ModelClass.PracticeTest.PracticeTestTopicWiseResponse;
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

public class MockTestCountActivity extends AppCompatActivity {
    private MockTestCountAdapter mockTestCountAdapter;
    private RecyclerView mockTestCountView;

    private ArrayList<MockTestCount> mockTestCountViewArrayList;
    String course_id, mock_test_id, sectionName, assignment_test_id;

    private Context context = this;
    private List<Integer> arrayList;
    private TextView tv_nodata;
    private String commonflag, id, subSectionName, sub_id;
    private ActionBar actionBar;

    private TextView totalQuestionView, correctAnswerView, percentageView, timeTakenView;
    Button cancelBtn;

    private MockTestResultAdapter mockTestResultAdapter;
    private MockTestScoreSubAdapter mockTestScoreSubAdapter;
    private ArrayList mockTestResultViewArrayList;
    private ArrayList mockTestAnswerViewArrayList;
    private RecyclerView mockTestView, mockTestSubView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_test_count);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        //course_id = intent.getStringExtra("id");
        // sectionName = intent.getStringExtra("section_name");
        mock_test_id = intent.getStringExtra("mock_test_id");
        Log.e("Mock_Test_id","==="+mock_test_id);

        mockTestCountView = findViewById(R.id.mockTestCountView);
        tv_nodata = findViewById(R.id.tv_nodata);
        mockTestCountViewArrayList = new ArrayList();
        mockTestResultViewArrayList = new ArrayList();
        mockTestAnswerViewArrayList = new ArrayList();
        mockTestCountViewDetails();
        mockTestCountView.setHasFixedSize(true);
        mockTestCountView.setLayoutManager(new LinearLayoutManager(this));
        mockTestCountAdapter = new MockTestCountAdapter(this, mockTestCountViewArrayList, context);
        mockTestCountView.setAdapter(mockTestCountAdapter);
        mockTestCountAdapter.notifyDataSetChanged();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mockTestCountViewDetails();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        Log.e("Final course", "---" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME));

        mockTestCountAdapter.SetOnItemClickListener(new MockTestCountAdapter.OnItemClickListener() {

            @Override
            public void onButtonClick(View view, MockTestCount position) {
                if (position.getStatus().equalsIgnoreCase("locked")) {
                    //testScore(position.getId().toString());
                    // dilogbox();

                } else if (position.getStatus().equalsIgnoreCase("score")) {
                    //testScore(position.getId().toString());
                    id = position.getId().toString();
                    //mockTestFinalResult();
                    Intent intent = new Intent(getApplicationContext(), MockTestScoreActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("page", "test");
                    //intent.putExtra("section_name", position.getSectionName());
                    startActivity(intent);


                } else {
                    Intent intent = new Intent(getApplicationContext(), MockTestStartActivity.class);
                    // id=position.getId().toString();
                    //  intent.putExtra("id", course_id);
                    // intent.putExtra("section_name", sectionName);
                    // intent.putExtra("readingcomponent", "Study Materials");
                    intent.putExtra("mock_test_id", position.getId().toString());

                    startActivity(intent);
                }
            }

            @Override
            public void onView(View view, MockTestCount position) {

            }

            @Override
            public void onscoreClick(View view, MockTestCount position) {
                //dilogbox();
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
                mockTestCountAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mockTestCountAdapter.getFilter().filter(query);
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

    private void mockTestCountViewDetails() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");
                Map<String, String> params = new HashMap<String, String>();
                if (mock_test_id == null) {
                    //  params.put("section_id", course_id);
                    params.put("mock_test_category_id", " ");
                } else {
                    // params.put("section_id", course_id);
                    params.put("mock_test_category_id", mock_test_id);
                }
                RetrofitAPI.getInstance(this).getApi().getMockTestCountDetails(params, new Callback<MockTestCountResponse>() {
                    @Override
                    public void success(MockTestCountResponse object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                mockTestCountViewArrayList.clear();
                                mockTestCountViewArrayList.addAll(object.getMockTests());
                                if (mockTestCountViewArrayList.size() <= 0) {
                                    mockTestCountView.setVisibility(View.GONE);
                                    tv_nodata.setVisibility(View.VISIBLE);
                                    tv_nodata.setText("No Data Available");
                                } else {
                                    mockTestCountView.setVisibility(View.VISIBLE);
                                    tv_nodata.setVisibility(View.GONE);
                                    tv_nodata.setText("");
                                }
                                mockTestCountAdapter.notifyDataSetChanged();
                                Log.e("data", "---" + mockTestCountViewArrayList.toString());

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
                                mockTestCountView.setVisibility(View.GONE);
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

    public void dilogbox() {
        LayoutInflater li = LayoutInflater.from(this);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.score_card, null);

        totalQuestionView = confirmDialog.findViewById(R.id.totalQuestionView);
        correctAnswerView = confirmDialog.findViewById(R.id.correctAnswerView);
        percentageView = confirmDialog.findViewById(R.id.percentageView);
        timeTakenView = confirmDialog.findViewById(R.id.timeTakenView);

        cancelBtn = confirmDialog.findViewById(R.id.cancelBtn);


        //Creating an alertdialog builder
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(this);
        alert.setCancelable(false);
        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);

        //Creating an alert dialog
        final android.app.AlertDialog alertDialog = alert.create();

        //Displaying the alert dialog
        alertDialog.show();
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // countDownTimer.cancel();
                alertDialog.dismiss();
            }
        });

    }

    private void testScore(String id) {
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
                                totalQuestionView.setText("Total Question : " + object.getTotalQuestions());
                                correctAnswerView.setText("Correct Answer : " + object.getCorrectAnswers());
                                percentageView.setText("Percentage : " + object.getPercentage());
                                timeTakenView.setText("Time Taken : " + object.getTimeTaken());

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


    private void scoreDilogbox() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MockTestCountActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.activity_mock_result_dialog, null);
        //builder.setView(convertView);
        // builder.setTitle("List");
        builder.setView(convertView);
        mockTestView = convertView.findViewById(R.id.mockTestView);
        //btnDoneTest = convertView.findViewById(R.id.btnDoneTest);
        //  cancelBtn = convertView.findViewById(R.id.cancelBtn);
        mockTestView.setHasFixedSize(true);
        mockTestView.setLayoutManager(new LinearLayoutManager(this));
        mockTestResultAdapter = new MockTestResultAdapter(this, mockTestResultViewArrayList);
        mockTestView.setAdapter(mockTestResultAdapter);

        if (mockTestResultViewArrayList.size() <= 0) {
            mockTestView.setVisibility(View.GONE);
            //etSearchdefaulters.setVisibility(View.GONE);
            tv_nodata.setVisibility(View.VISIBLE);
        } else {
            mockTestView.setVisibility(View.VISIBLE);
            //etSearchdefaulters.setVisibility(View.VISIBLE);
            tv_nodata.setVisibility(View.GONE);
        }
        mockTestResultAdapter.notifyDataSetChanged();
        mockTestResultAdapter.SetOnItemClickListener(new MockTestResultAdapter.OnItemClickListener() {
            @Override
            public void onView(View view, MockResult position) {

                subSectionName = position.getSectionName();
                //mockTestScoreSubResult();
                Intent intent = new Intent(getApplicationContext(), MockTestScoreAnswerActivity.class);
                intent.putExtra("id", sub_id);

                intent.putExtra("section_name", position.getSectionName());
                startActivity(intent);

            }
        });

        // builder.show();


        //* AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
/*
      LayoutInflater inflater = this.getLayoutInflater();
        View promptView = inflater.inflate(R.layout.activity_calender_event, null);
        calenderView = promptView.findViewById(R.id.calenderView);
        calenderArraylist.clear();
        for (int j = 0; j < dates.size(); j++) {
            if (dates.get(j).getSessionDate().equalsIgnoreCase(curDate)) {
                calenderArraylist.add(dates.get(j));
            }
        }*/

     /*   calenderEventAdapter = new CalenderEventAdapter(this, calenderArraylist);
        calenderView.setAdapter(calenderEventAdapter);*/
        //  calenderEventAdapter.notifyAll();

        builder.setView(convertView)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // dialog.cancel();
                        Log.e("cancel", "--cancel-" + "cancel kilik");
                        dialog.dismiss();

                    }
                });

        final AlertDialog alert = builder.create();
        alert.show();

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
                                sub_id = object.getId().toString();
                                scoreDilogbox();
                                if (mockTestResultViewArrayList.size() <= 0) {
                                    mockTestView.setVisibility(View.GONE);
                                    // tv_nodata.setVisibility(View.VISIBLE);
                                    // tv_nodata.setText("No Data Available");
                                } else {
                                    mockTestView.setVisibility(View.VISIBLE);
                                    // tv_nodata.setVisibility(View.GONE);
                                    // tv_nodata.setText("");

                                }
                                mockTestResultAdapter.notifyDataSetChanged();
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

/*
    private void scoreSubDilogbox() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MockTestCountActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = (View) inflater.inflate(R.layout.activity_mock_sub_result_dialog, null);
        //builder.setView(convertView);
        // builder.setTitle("List");
        builder.setView(convertView);
        mockTestSubView = convertView.findViewById(R.id.mockTestSubView);
        //btnDoneTest = convertView.findViewById(R.id.btnDoneTest);
        //  cancelBtn = convertView.findViewById(R.id.cancelBtn);
        mockTestSubView.setHasFixedSize(true);
        mockTestSubView.setLayoutManager(new LinearLayoutManager(this));
        mockTestScoreSubAdapter = new MockTestScoreSubAdapter(this, mockTestAnswerViewArrayList);
        mockTestSubView.setAdapter(mockTestScoreSubAdapter);

        if (mockTestAnswerViewArrayList.size() <= 0) {
            mockTestSubView.setVisibility(View.GONE);
            //etSearchdefaulters.setVisibility(View.GONE);
            // tv_nodata.setVisibility(View.VISIBLE);
        } else {
            mockTestSubView.setVisibility(View.VISIBLE);
            //etSearchdefaulters.setVisibility(View.VISIBLE);
            //tv_nodata.setVisibility(View.GONE);
        }
        mockTestScoreSubAdapter.notifyDataSetChanged();
        mockTestScoreSubAdapter.SetOnItemClickListener(new MockTestScoreSubAdapter.OnItemClickListener() {

            @Override
            public void onView(View view, MockTestFullScore position) {

            }
        });

        // builder.show();


        //* AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
*//*
      LayoutInflater inflater = this.getLayoutInflater();
        View promptView = inflater.inflate(R.layout.activity_calender_event, null);
        calenderView = promptView.findViewById(R.id.calenderView);
        calenderArraylist.clear();
        for (int j = 0; j < dates.size(); j++) {
            if (dates.get(j).getSessionDate().equalsIgnoreCase(curDate)) {
                calenderArraylist.add(dates.get(j));
            }
        }*//*

     *//*   calenderEventAdapter = new CalenderEventAdapter(this, calenderArraylist);
        calenderView.setAdapter(calenderEventAdapter);*//*
        //  calenderEventAdapter.notifyAll();

        builder.setView(convertView)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // dialog.cancel();
                        Log.e("cancel", "--cancel-" + "cancel kilik");
                        dialog.dismiss();

                    }
                });

        final AlertDialog alert = builder.create();
        alert.show();

    }*/

  /*  private void mockTestScoreSubResult() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");
                Map<String, String> params = new HashMap<String, String>();

                params.put("id", sub_id);
                params.put("section_name", subSectionName);

                RetrofitAPI.getInstance(this).getApi().getMockTestSubResult(params, new Callback<MockTestFullScoreResponse>() {
                    @Override
                    public void success(MockTestFullScoreResponse object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {

                                UIUtil.stopProgressDialog(getApplicationContext());
                                mockTestAnswerViewArrayList.clear();
                                mockTestAnswerViewArrayList.addAll(object.getScore());
                                scoreSubDilogbox();
                                if (mockTestAnswerViewArrayList.size() <= 0) {
                                    mockTestSubView.setVisibility(View.GONE);
                                    // tv_nodata.setVisibility(View.VISIBLE);
                                    // tv_nodata.setText("No Data Available");
                                } else {
                                    mockTestSubView.setVisibility(View.VISIBLE);
                                    // mockTestSubView.setVisibility(View.GONE);
                                    // tv_nodata.setText("");

                                }
                                mockTestScoreSubAdapter.notifyDataSetChanged();
                                Log.e("data", "---" + mockTestAnswerViewArrayList.toString());

                              *//*  arrayList.clear();
                                arrayList.addAll(object.getAssignedBatchesIds());
                                if (arrayList.size() > 0) {
                                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.ENROLL_STATUS, "true");
                                    //commonflag = "true";
                                }*//*
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
    }*/

}
