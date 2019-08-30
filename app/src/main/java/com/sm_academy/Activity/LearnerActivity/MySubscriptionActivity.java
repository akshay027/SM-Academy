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
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.sm_academy.API.RetrofitAPI;
import com.sm_academy.Activity.DefaultActivity.BatchTimingActivity;
import com.sm_academy.Activity.DefaultActivity.MainLandingActivity;
import com.sm_academy.Activity.DefaultActivity.PaymentActivity;
import com.sm_academy.Activity.DefaultActivity.ProficiencyTestActivity;

import com.sm_academy.Adapters.CourseListViewAdapter;
import com.sm_academy.Adapters.MySubscriptionAdapter;
import com.sm_academy.Adapters.MySubscriptionCourseNameAdapter;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.BatchDetails;
import com.sm_academy.ModelClass.BatchDetailsResponse;
import com.sm_academy.ModelClass.Subscription.CourseExtension;
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

public class MySubscriptionActivity extends AppCompatActivity {
    private MySubscriptionAdapter mySubscriptionAdapter;
    private MySubscriptionCourseNameAdapter mySubscriptionCourseNameAdapter;
    private RecyclerView subscriptionView;

    private ArrayList<Subscription> subscriptionArrayList;

    private Context context = this;
    private List<Integer> arrayList;
    private TextView tv_nodata;
    private String commonflag, batch_user_id;
    private ActionBar actionBar;
    private RecyclerView courseView;
    private ArrayList<CourseExtension> courseArrayList;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_scubscription);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        subscriptionView = findViewById(R.id.subscriptionView);
        tv_nodata = findViewById(R.id.tv_nodata);
        courseView = findViewById(R.id.courseView);
        subscriptionArrayList = new ArrayList();
        courseArrayList = new ArrayList<>();


        // arrayList = new ArrayList<>();
        // arrayList.addAll(object.getAssignedBatchesIds());
      /*  arrayList.add(1);
        arrayList.add(29);
        arrayList.add(45);
        arrayList.add(44);
        Log.e("arr", "===" + arrayList);*/
        subscriptionViewDetails();

        courseView.setHasFixedSize(true);
        courseView.setLayoutManager(new LinearLayoutManager(this));

        //courseArrayList.addAll(subscriptionArrayList.get(0).getCourseExtensions());
        mySubscriptionCourseNameAdapter = new MySubscriptionCourseNameAdapter(this, courseArrayList);
        courseView.setAdapter(mySubscriptionCourseNameAdapter);

        subscriptionView.setHasFixedSize(true);
        subscriptionView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        mySubscriptionAdapter = new MySubscriptionAdapter(this, subscriptionArrayList, context);
        subscriptionView.setAdapter(mySubscriptionAdapter);
        mySubscriptionAdapter.notifyDataSetChanged();

        Log.e("Final course", "---" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME));
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                subscriptionViewDetails();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mySubscriptionAdapter.SetOnItemClickListener(new MySubscriptionAdapter.OnItemClickListener() {


            @Override
            public void onView(View view, int position) {
                mySubscriptionAdapter.setSelectedIndex(position);
                mySubscriptionAdapter.notifyDataSetChanged();
                courseArrayList.addAll(subscriptionArrayList.get(position).getCourseExtensions());
                batch_user_id = subscriptionArrayList.get(position).getBatchUserId().toString();
                mySubscriptionCourseNameAdapter = new MySubscriptionCourseNameAdapter(getApplicationContext(), courseArrayList);
                courseView.setAdapter(mySubscriptionCourseNameAdapter);
            }
        });

        mySubscriptionCourseNameAdapter.SetOnItemClickListener(new MySubscriptionCourseNameAdapter.OnItemClickListener() {
            @Override
            public void onExtendlick(View view, CourseExtension position) {
                Intent intent = new Intent(getApplicationContext(), SubscriptionPaymentActivity.class);
                intent.putExtra("batch_id", batch_user_id);
                intent.putExtra("id", position.getId().toString());
                intent.putExtra("money", position.getAmount().toString());
                startActivity(intent);
            }

            @Override
            public void onItemClick(View view, CourseExtension position) {

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
                mySubscriptionCourseNameAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mySubscriptionCourseNameAdapter.getFilter().filter(query);
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

    private void subscriptionViewDetails() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");

                RetrofitAPI.getInstance(this).getApi().getsubscriptionDetails(new Callback<SubscriptionResponse>() {
                    @Override
                    public void success(SubscriptionResponse object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                subscriptionArrayList.clear();
                                courseArrayList.clear();
                                subscriptionArrayList.addAll(object.getSubscriptions());

                                if (subscriptionArrayList.size() <= 0) {
                                    subscriptionView.setVisibility(View.GONE);
                                    tv_nodata.setVisibility(View.VISIBLE);
                                    tv_nodata.setText("No Data Available");
                                } else {
                                    subscriptionView.setVisibility(View.VISIBLE);
                                    tv_nodata.setVisibility(View.GONE);
                                    tv_nodata.setText("");
                                }
                                mySubscriptionAdapter.notifyDataSetChanged();
                                courseArrayList.addAll(subscriptionArrayList.get(0).getCourseExtensions());
                                batch_user_id = subscriptionArrayList.get(0).getBatchUserId().toString();
                                mySubscriptionCourseNameAdapter = new MySubscriptionCourseNameAdapter(getApplicationContext(), courseArrayList);
                                courseView.setAdapter(mySubscriptionCourseNameAdapter);
                                Log.e("data", "---" + subscriptionArrayList.toString());

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


}
