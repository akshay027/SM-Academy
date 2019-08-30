package com.sm_academy.Activity.LearnerActivity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.EventLogTags;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.sm_academy.API.RetrofitAPI;
import com.sm_academy.Activity.DefaultActivity.MainLandingActivity;
/*import com.sm_academy.Activity.LabelFormatter;*/
import com.sm_academy.Adapters.ProgressBarAdapter;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.ProgressGraph.Batch;
import com.sm_academy.ModelClass.ProgressGraph.Course;
import com.sm_academy.ModelClass.ProgressGraph.CourseResponse;
import com.sm_academy.ModelClass.ProgressGraph.GraphMockTestSection;
import com.sm_academy.ModelClass.ProgressGraph.GraphMockTestSectionResponse;
import com.sm_academy.ModelClass.ProgressGraph.GraphResponse;
import com.sm_academy.ModelClass.ProgressGraph.GraphSection;
import com.sm_academy.ModelClass.ProgressGraph.NewBatchResponse;
import com.sm_academy.R;
import com.sm_academy.Utility.UIUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AssignmentMockProgressActivity extends AppCompatActivity {
    private BarChart barGraphForMockTest;
    ArrayList<GraphSection> graphArraylist;
    ArrayList<Integer> mockArraylist;
    ArrayList<String> dataArraylist;

    private String progress;
    LinearLayout assignmentProgressView, mockTestProgressView;

    private RecyclerView graphView;
    private Spinner spinnerCourse, spinnerBatch;

    private ArrayAdapter<String> courseAdapter;
    private ArrayAdapter<String> batchAdapter;

    private ArrayList<Batch> batchResponseArraylist;
    private List<String> batchArraylist;

    private ArrayList<Course> courseResponseArrayList;
    private ArrayList<String> courseArraylist;
    private String course, batch, depart, department;
    private TextView tv_nodata;
    private ProgressBarAdapter progressBarAdapter;
    private ActionBar actionBar;
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_progress);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar = getSupportActionBar();
        mockTestProgressView = findViewById(R.id.mockTestProgressView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        // assignmentProgressView = findViewById(R.id.assignmentProgressView);
        graphView = findViewById(R.id.graphView);
        Intent intent = getIntent();
        progress = intent.getStringExtra("progress");
        tv_nodata = findViewById(R.id.tv_nodata);
        barGraphForMockTest = findViewById(R.id.barGraphForMockTest);
        if (TextUtils.isEmpty(progress)) {
            actionBar.setTitle("Progress Chart ");
        } else {
            actionBar.setTitle(progress);
        }

        //  barGraphforReading = findViewById(R.id.barGraphforReading);
        //  barGraphForListening = findViewById(R.id.barGraphForListening);
        //  barGraphforWriting = findViewById(R.id.barGraphforWriting);

  /*      if (progress.equalsIgnoreCase("Assignment")) {
            assignmentProgressView.setVisibility(View.VISIBLE);
            mockTestProgressView.setVisibility(View.GONE);
            //barChartDetailsForAssignment();

        } else {
            // barChartDetailsForMockTest();
            assignmentProgressView.setVisibility(View.GONE);
            mockTestProgressView.setVisibility(View.VISIBLE);

        }*/
        graphArraylist = new ArrayList<>();
        mockArraylist = new ArrayList<Integer>();
        dataArraylist = new ArrayList<>();

        graphView.setHasFixedSize(true);
        graphView.setLayoutManager(new LinearLayoutManager(this));


        //ArrayList<BarEntry> barEntries = new ArrayList<>();

    /*    listendata = new ArrayList<String>();
        readdata = new ArrayList<String>();
        speakdata = new ArrayList<String>();
        writedata = new ArrayList<String>();


        listenarrayList = new ArrayList<Float>();
        readarrayList = new ArrayList<Float>();
        writearrayList = new ArrayList<Float>();
        speakarrayList = new ArrayList<Float>();*/


      /*  for (int i = 0; i < listenarrayList.size(); i++) {
            barEntries.addAll(Collections.singleton(new BarEntry(listenarrayList.get(i), i)));
        }
        BarDataSet barDataSetforSpeak = new BarDataSet(barEntries, "Date");

        BarDataSet barDataSetforRead = new BarDataSet(barEntries, "Date");

        BarDataSet barDataSetforWrite = new BarDataSet(barEntries, "Date");

        BarDataSet barDataSetforListen = new BarDataSet(barEntries, "Date");*/

    /*    BarData barDataforSpeak = new BarData(speakdata, barDataSetforSpeak);
        barGraphforSpeaking.setData(barDataforSpeak);

        BarData barDataforRead = new BarData(readdata, barDataSetforRead);
        barGraphforReading.setData(barDataforRead);

        BarData barDataWrite = new BarData(writedata, barDataSetforWrite);
        barGraphforWriting.setData(barDataWrite);

        BarData barDataListen = new BarData(listendata, barDataSetforListen);
        barGraphForListening.setData(barDataListen);*/
/*
        barGraphforSpeaking.setTouchEnabled(true);
        barGraphforSpeaking.setScaleEnabled(true);
        barGraphforSpeaking.setHorizontalScrollBarEnabled(true);
        barGraphforSpeaking.setVisibleXRangeMaximum(10);

        barGraphforReading.setTouchEnabled(true);
        barGraphforReading.setScaleEnabled(true);
        barGraphforReading.setHorizontalScrollBarEnabled(true);
        barGraphforReading.setVisibleXRangeMaximum(10);

        barGraphforWriting.setTouchEnabled(true);
        barGraphforWriting.setScaleEnabled(true);
        barGraphforWriting.setHorizontalScrollBarEnabled(true);
        barGraphforWriting.setVisibleXRangeMaximum(10);

        barGraphForListening.setTouchEnabled(true);
        barGraphForListening.setScaleEnabled(true);
        barGraphForListening.setHorizontalScrollBarEnabled(true);
        barGraphForListening.setVisibleXRangeMaximum(10);*/

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


        progressBarAdapter = new ProgressBarAdapter(this, graphArraylist);
        graphView.setAdapter(progressBarAdapter);
        progressBarAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCourse();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        progressBarAdapter.SetOnItemClickListener(new ProgressBarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void ontopicClick(View view, int position) {

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

    private void updateDepartmentToSpinner() {
        courseArraylist.clear();

        courseArraylist.add("Select Course");
        for (int i = 0; i < courseResponseArrayList.size(); i++) {
            Course course = courseResponseArrayList.get(i);
            courseArraylist.add(course.getUpcasedName());
        }

        courseAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_color, courseArraylist);
        courseAdapter.setDropDownViewResource(R.layout.spinnertext);

        spinnerCourse.setAdapter(courseAdapter);
        spinnerCourse.setSelection(getselection());
        courseAdapter.notifyDataSetChanged();

        spinnerCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (position == 0) {
                        depart = "0";
                        // courseSummariesView.setVisibility(View.GONE);
                    } else {
                        depart = courseArraylist.get(position);
                        //courseSummariesView.setVisibility(View.GONE);
                    }
                    Log.e("department_id", "-------" + getdepartCategory(depart));
                    if (position == 0) {
                        batchArraylist.clear();
                        graphView.setVisibility(View.GONE);
                        mockTestProgressView.setVisibility(View.GONE);
                        //courseSummariesView.setVisibility(View.GONE);
                        Log.e("department_id", "-------" + courseResponseArrayList.get(position).getId().toString());
                        course = "";
                        spinnerBatch.setClickable(false);
                        department = courseArraylist.get(position);
                        batchResponseArraylist.clear();
                        updateBatchToSpinner();


                    } else {
                        graphView.setVisibility(View.GONE);
                        mockTestProgressView.setVisibility(View.GONE);
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
                                graphView.setVisibility(View.GONE);
                                mockTestProgressView.setVisibility(View.GONE);
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
                        graphView.setVisibility(View.GONE);
                        mockTestProgressView.setVisibility(View.GONE);
                        batch = "";
                        Log.e("spinnerBatch position0", "-------" + position);
                        //batch = batchArraylist.get(position);


                    } else {
                        // courseSummariesView.setVisibility(View.VISIBLE);
                        batch = batchArraylist.get(position);
                        //courseSummariesDetails();
                        if (progress.equalsIgnoreCase("Assignment Progress")) {
                            // assignmentProgressView.setVisibility(View.VISIBLE);
                            // mockTestProgressView.setVisibility(View.GONE);
                            barChartDetailsForAssignment();

                        } else {
                            barChartDetailsForMockTest();
                            // assignmentProgressView.setVisibility(View.GONE);
                            // mockTestProgressView.setVisibility(View.VISIBLE);

                        }
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

    private void barChartDetailsForAssignment() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");
                Map<String, String> params = new HashMap<String, String>();
                params.put("batch_id", String.valueOf(getbatchCategory(batch)));
                RetrofitAPI.getInstance(this).getApi().getBarGraphDetails(params, new Callback<GraphResponse>() {
                    @Override
                    public void success(GraphResponse object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                graphArraylist.clear();
                                graphArraylist.addAll(object.getSections());
                                if (graphArraylist.size() <= 0) {
                                    tv_nodata.setVisibility(View.VISIBLE);
                                    graphView.setVisibility(View.GONE);
                                    // tv_nodata.setVisibility(View.VISIBLE);
                                    // tv_nodata.setText("No Data Available");
                                } else {
                                    graphView.setVisibility(View.VISIBLE);
                                    tv_nodata.setVisibility(View.GONE);
                                    // tv_nodata.setVisibility(View.GONE);
                                    // tv_nodata.setText("");
                                }
                                progressBarAdapter.notifyDataSetChanged();
                                Log.e("data", "---" + graphArraylist.toString());


                                // Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                tv_nodata.setVisibility(View.GONE);
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

    private void barChartDetailsForMockTest() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");
                Map<String, String> params = new HashMap<String, String>();
                params.put("batch_id", String.valueOf(getbatchCategory(batch)));
                RetrofitAPI.getInstance(this).getApi().getBarGraphForMockTestDetails(params, new Callback<GraphMockTestSectionResponse>() {
                    @Override
                    public void success(GraphMockTestSectionResponse object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                mockArraylist.clear();
                                //mockArraylist.addAll(object.getSections());
                                bindata(object.getSections());

                                progressBarAdapter.notifyDataSetChanged();
                                Log.e("data", "---" + graphArraylist.toString());
                            } else {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                tv_nodata.setVisibility(View.GONE);
                                // Toast.makeText(getApplicationContext(), "No Data Available", Toast.LENGTH_SHORT).show();
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

    public void bindata(GraphMockTestSection graphMockTestSection) {

        // dataArraylist.addAll(graphMockTestSection.getScores());
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        mockArraylist.clear();
        mockArraylist.addAll(graphMockTestSection.getScores());
        dataArraylist.addAll(graphMockTestSection.getTestNames());
        if (mockArraylist.size() <= 0) {
            graphView.setVisibility(View.GONE);
            tv_nodata.setVisibility(View.VISIBLE);
            mockTestProgressView.setVisibility(View.GONE);
            // tv_nodata.setVisibility(View.VISIBLE);
            // tv_nodata.setText("No Data Available");
        } else {
            graphView.setVisibility(View.GONE);
            tv_nodata.setVisibility(View.GONE);
            mockTestProgressView.setVisibility(View.VISIBLE);

            for (int i = 0; i < mockArraylist.size(); i++) {
                barEntries.addAll(Collections.singleton(new BarEntry(mockArraylist.get(i), i)));
            }
            BarDataSet barDataSet = new BarDataSet(barEntries, "Progress Chart");
            // barDataSet.setBarBorderWidth(50f);
            //barEntries.addAll(new BarEntry(arrayList));
            String[] str = dataArraylist.toArray(new String[dataArraylist.size()]);
            BarData barData = new BarData(str, barDataSet);
            ;
            barGraphForMockTest.setData(barData);
            barGraphForMockTest.setTouchEnabled(true);
            barGraphForMockTest.setDescription("");
            barGraphForMockTest.setScaleEnabled(true);
            barGraphForMockTest.setHorizontalScrollBarEnabled(true);
            barGraphForMockTest.setVisibleXRangeMaximum(10);
            barGraphForMockTest.moveViewToX(10);
            barGraphForMockTest.getLegend().setEnabled(false);
            barGraphForMockTest.setPinchZoom(true);
            XAxis xAxis = barGraphForMockTest.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setSpaceBetweenLabels(6);
            xAxis.setXOffset(12);
            xAxis.setTextSize(10);
            xAxis.setDrawGridLines(false);
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
                progress = "Assignment Progress";
                if (TextUtils.isEmpty(progress)) {
                    actionBar.setTitle("Progress Chart ");
                } else {
                    actionBar.setTitle(progress);
                }
                //assignmentProgressView.setVisibility(View.VISIBLE);
                graphView.setVisibility(View.GONE);
                mockTestProgressView.setVisibility(View.GONE);
                //barChartDetailsForAssignment();
                getCourse();
                return true;

            case R.id.mockTest:
                progress = "MockTest Progress";
                if (TextUtils.isEmpty(progress)) {
                    actionBar.setTitle("Progress Chart ");
                } else {
                    actionBar.setTitle(progress);
                }
                mockTestProgressView.setVisibility(View.GONE);
                graphView.setVisibility(View.GONE);
                // assignmentProgressView.setVisibility(View.GONE);
                //mockTestProgressView.setVisibility(View.VISIBLE);
                //barChartDetailsForMockTest();
                getCourse();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

