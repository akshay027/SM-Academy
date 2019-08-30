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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.sm_academy.API.RetrofitAPI;
import com.sm_academy.Activity.DefaultActivity.MainLandingActivity;
import com.sm_academy.Activity.DefaultActivity.ProficiencyTestActivity;
import com.sm_academy.Adapters.AssignmentViewListAdapter;
import com.sm_academy.Adapters.MySubscriptionAdapter;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.PracticeTest.PreacticeTestSectionTopic;
import com.sm_academy.ModelClass.PracticeTest.PreacticeTestTopicsResponse;
import com.sm_academy.ModelClass.Subscription.Subscription;
import com.sm_academy.ModelClass.Subscription.SubscriptionResponse;
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

public class AssignmentViewListActivity extends AppCompatActivity {
    private AssignmentViewListAdapter assignmentViewListAdapter;
    private RecyclerView assignmentListView;

    private ArrayList<PreacticeTestSectionTopic> assignmentListViewArrayList;
    String course_id, sectionName;

    private Context context = this;
    private List<Integer> arrayList;
    private TextView tv_nodata;
    private String commonflag;
    private ActionBar actionBar;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_view_list);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        course_id = intent.getStringExtra("id");
        sectionName = intent.getStringExtra("section_name");


        assignmentListView = findViewById(R.id.assignmentListView);
        tv_nodata = findViewById(R.id.tv_nodata);
        assignmentListViewArrayList = new ArrayList();
        assignmentListViewDetails();
        assignmentListView.setHasFixedSize(true);
        assignmentListView.setLayoutManager(new LinearLayoutManager(this));
        assignmentViewListAdapter = new AssignmentViewListAdapter(this, assignmentListViewArrayList, context);
        assignmentListView.setAdapter(assignmentViewListAdapter);
        assignmentViewListAdapter.notifyDataSetChanged();

        Log.e("Final course", "---" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME));
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                assignmentListViewDetails();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        assignmentViewListAdapter.SetOnItemClickListener(new AssignmentViewListAdapter.OnItemClickListener() {

            @Override
            public void onView(View view, PreacticeTestSectionTopic position) {
                if (position.getLocked() == true) {

                } else {
                    Intent intent = new Intent(getApplicationContext(), AssignmentTestCountActivity.class);
                    intent.putExtra("id", course_id);

                    intent.putExtra("assignment_test_id", position.getId());
                    Log.e("id", "99999999" + position.getId());
                    // intent.putExtra("section_id", position.getSectionId().toString());
                    intent.putExtra("section_name", sectionName);
                    // intent.putExtra("readingcomponent", "Study Materials");
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
                assignmentViewListAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                assignmentViewListAdapter.getFilter().filter(query);
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

    private void assignmentListViewDetails() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");
                Map<String, String> params = new HashMap<String, String>();
                params.put("section_id", course_id);
                RetrofitAPI.getInstance(this).getApi().getAssignmentTopicsResponse(params, new Callback<PreacticeTestTopicsResponse>() {
                    @Override
                    public void success(PreacticeTestTopicsResponse object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                assignmentListViewArrayList.clear();
                                assignmentListViewArrayList.addAll(object.getSectionTopics());
                                if (assignmentListViewArrayList.size() <= 0) {
                                    assignmentListView.setVisibility(View.GONE);
                                    tv_nodata.setVisibility(View.VISIBLE);
                                    tv_nodata.setText("No Data Available");
                                } else {
                                    assignmentListView.setVisibility(View.VISIBLE);
                                    tv_nodata.setVisibility(View.GONE);
                                    tv_nodata.setText("");
                                }

                                assignmentViewListAdapter.notifyDataSetChanged();
                                Log.e("data", "---" + assignmentListViewArrayList.toString());

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
                                assignmentListView.setVisibility(View.GONE);
                                tv_nodata.setText(object.getMessage());
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
