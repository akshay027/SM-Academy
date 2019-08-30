package com.sm_academy.Activity.LearnerActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sm_academy.API.RetrofitAPI;
import com.sm_academy.Activity.DefaultActivity.MainLandingActivity;
import com.sm_academy.Adapters.CourseSummariesAdapter;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.ProgressGraph.Batch;
import com.sm_academy.ModelClass.ProgressGraph.CourseSummaries;
import com.sm_academy.ModelClass.ProgressGraph.CourseSummariesResponse;
import com.sm_academy.ModelClass.ProgressGraph.NewBatchResponse;
import com.sm_academy.ModelClass.ProgressGraph.Course;
import com.sm_academy.ModelClass.ProgressGraph.CourseResponse;
import com.sm_academy.ModelClass.PracticeTest.MockTestList;
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

public class CourseSummariesActivity extends AppCompatActivity {

    private RecyclerView courseSummariesView;

    private ArrayList<CourseSummaries> courseSummariesArrayList;
    private Context context = this;
    private List<Integer> arrayList;
    private TextView tv_nodata;

    private CourseSummariesAdapter courseSummariesAdapter;

    private Spinner spinnerCourse, spinnerBatch;

    private ArrayAdapter<String> courseAdapter;
    private ArrayAdapter<String> batchAdapter;

    private ArrayList<Batch> batchResponseArraylist;
    private List<String> batchArraylist;

    private ArrayList<Course> courseResponseArrayList;
    private ArrayList<String> courseArraylist;
    private String depart, department, course, batch;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_summaries);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courseSummariesView = findViewById(R.id.courseSummariesView);
        tv_nodata = findViewById(R.id.tv_nodata);
        courseSummariesArrayList = new ArrayList();
      //  mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        spinnerCourse = findViewById(R.id.spinnerCourse);
        spinnerBatch = findViewById(R.id.spinnerBatch);

        courseArraylist = new ArrayList();
        batchArraylist = new ArrayList();

        courseResponseArrayList = new ArrayList<>();
        batchResponseArraylist = new ArrayList<>();


        courseArraylist.clear();
        courseArraylist.add("Select Course");
        courseAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_color, courseArraylist);
        courseAdapter.setDropDownViewResource(R.layout.spinnertext);
        spinnerCourse.setAdapter(courseAdapter);
        courseAdapter.notifyDataSetChanged();

        batchArraylist.clear();
        batchArraylist.add("Select Batch");
        batchAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_color, batchArraylist);
        batchAdapter.setDropDownViewResource(R.layout.spinnertext);
        spinnerBatch.setAdapter(batchAdapter);
        batchAdapter.notifyDataSetChanged();

        courseSummariesView.setHasFixedSize(true);
        courseSummariesView.setLayoutManager(new LinearLayoutManager(this));
        courseSummariesAdapter = new CourseSummariesAdapter(this, courseSummariesArrayList, context);
        courseSummariesView.setAdapter(courseSummariesAdapter);
        courseSummariesAdapter.notifyDataSetChanged();
        getCourse();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCourse();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        //courseSummariesDetails();
        Log.e("Final course", "---" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME));

        courseSummariesAdapter.SetOnItemClickListener(new CourseSummariesAdapter.OnItemClickListener() {
            @Override
            public void onView(View view, CourseSummaries position) {

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

    private void getCourse() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                // UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                RetrofitAPI.getInstance(getApplicationContext()).getApi().getCourse(new Callback<CourseResponse>() {
                    @Override
                    public void success(CourseResponse departmentResponse, Response response) {
                        try {
                            if (departmentResponse.getStatus().equalsIgnoreCase("success")) {
                                courseResponseArrayList.clear();
                                courseResponseArrayList.addAll(departmentResponse.getCourses());
                                updateDepartmentToSpinner();

                            } else {
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(), "Internal Server Error", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {

                Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDepartmentToSpinner() {
        courseArraylist.clear();

        courseArraylist.add("Select Course");
        for (int i = 0; i < courseResponseArrayList.size(); i++) {
            Course department = courseResponseArrayList.get(i);
            courseArraylist.add(department.getUpcasedName());
        }

        courseAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_color, courseArraylist);
        courseAdapter.setDropDownViewResource(R.layout.spinnertext);

        spinnerCourse.setAdapter(courseAdapter);
        // spinnerCourse.setSelection(getselection());
        courseAdapter.notifyDataSetChanged();

        spinnerCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (position == 0) {
                        depart = "0";
                        courseSummariesView.setVisibility(View.GONE);
                    } else {
                        depart = courseArraylist.get(position);
                        courseSummariesView.setVisibility(View.GONE);
                    }
                    Log.e("department_id", "-------" + getdepartCategory(depart));
                    if (position == 0) {
                        batchArraylist.clear();
                        courseSummariesView.setVisibility(View.GONE);
                        Log.e("department_id", "-------" + courseResponseArrayList.get(position).getId().toString());
                        course = "";
                        spinnerBatch.setClickable(false);
                        department = courseArraylist.get(position);
                        batchResponseArraylist.clear();
                        updateBatchToSpinner();


                    } else {
                        courseSummariesView.setVisibility(View.GONE);
                        // Log.e("department_id", "-------" + courseResponseArrayList.get(position).getId().toString());
                        batchArraylist.clear();
                        spinnerBatch.setClickable(true);
                        department = courseArraylist.get(position);
                        getBatch();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public int getselection() {
        for (int i = 0; i < courseResponseArrayList.size(); i++) {

            if (courseResponseArrayList.get(i).getName().equalsIgnoreCase(course)) {

                return i;
            }
        }
        return 0;
    }

    private int getdepartCategory(String cat) {
        for (int i = 0; i < courseResponseArrayList.size(); i++) {
            if (cat.equalsIgnoreCase(courseResponseArrayList.get(i).getName())) {
                return courseResponseArrayList.get(i).getId();
            }
        }
        return 0;
    }

    private void getBatch() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("course_id", String.valueOf(getdepartCategory(depart)));

                // UIUtil.startProgressDialog(this, "Please Wait.. Getting Details");
                RetrofitAPI.getInstance(getApplicationContext()).getApi().getBatch(params, new Callback<NewBatchResponse>() {
                    @Override
                    public void success(NewBatchResponse newBatchResponse, Response response) {
                        try {
                            if (newBatchResponse.getStatus().equalsIgnoreCase("success")) {
                                batchResponseArraylist.clear();
                                batchResponseArraylist.addAll(newBatchResponse.getBatches());
                                updateBatchToSpinner();

                            } else {
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(), "Internal Server Error", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {

                Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateBatchToSpinner() {
        batchArraylist.clear();

        batchArraylist.add("Select Batch");
        for (int i = 0; i < batchResponseArraylist.size(); i++) {
            Batch batches = batchResponseArraylist.get(i);
            batchArraylist.add(batches.getName());
        }

        batchAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_color, batchArraylist);
        batchAdapter.setDropDownViewResource(R.layout.spinnertext);
        spinnerBatch.setAdapter(batchAdapter);
        batchAdapter.notifyDataSetChanged();

        spinnerBatch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {

                    Log.e("department_id", "-------" + getbatchCategory(batch));
                    if (position == 0) {
                        courseSummariesView.setVisibility(View.GONE);
                        batch = "";
                        Log.e("spinnerBatch position0", "-------" + position);
                        //batch = batchArraylist.get(position);


                    } else {
                        courseSummariesView.setVisibility(View.VISIBLE);
                        batch = batchArraylist.get(position);
                        courseSummariesDetails();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // ondepartmentSelect();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private int getbatchCategory(String cat) {
        for (int i = 0; i < batchResponseArraylist.size(); i++) {
            if (cat.equalsIgnoreCase(batchResponseArraylist.get(i).getName())) {
                return batchResponseArraylist.get(i).getId();
            }
        }
        return 0;
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

    private void courseSummariesDetails() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");
                Map<String, String> params = new HashMap<String, String>();
                params.put("batch_id",String.valueOf(getbatchCategory(batch)));

                RetrofitAPI.getInstance(this).getApi().getCourseSummariesResponse(params, new Callback<CourseSummariesResponse>() {
                    @Override
                    public void success(CourseSummariesResponse object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                courseSummariesArrayList.clear();
                                courseSummariesArrayList.addAll(object.getSections());
                                if (courseSummariesArrayList.size() <= 0) {
                                    courseSummariesView.setVisibility(View.GONE);
                                    tv_nodata.setVisibility(View.VISIBLE);
                                    tv_nodata.setText("No Data Available");
                                } else {
                                    courseSummariesView.setVisibility(View.VISIBLE);
                                    tv_nodata.setVisibility(View.GONE);
                                    tv_nodata.setText("");
                                }
                                courseSummariesAdapter.notifyDataSetChanged();
                                Log.e("data", "---" + courseSummariesArrayList.toString());

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
                                courseSummariesView.setVisibility(View.GONE);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.graphmenu, menu);
        return true;
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

            case R.id.assignmentGraph:

                Intent intent = new Intent(getApplicationContext(), AssignmentMockProgressActivity.class);
                intent.putExtra("progress", "Assignment Progress");
                startActivity(intent);
                return true;

            case R.id.mockTest:
                Intent intent1 = new Intent(getApplicationContext(), AssignmentMockProgressActivity.class);
                intent1.putExtra("progress", "MockTest Progress");
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

