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
import com.sm_academy.Adapters.MySubscriptionAdapter;
import com.sm_academy.Adapters.PaymentHistoriesAdapter;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.PaymentHistories.PaymentHistories;
import com.sm_academy.ModelClass.PaymentHistories.PaymentHistoriesResponse;
import com.sm_academy.ModelClass.Subscription.Subscription;
import com.sm_academy.ModelClass.Subscription.SubscriptionResponse;
import com.sm_academy.R;
import com.sm_academy.Utility.Constants;
import com.sm_academy.Utility.UIUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PaymentHistoriesActivity extends AppCompatActivity {
    private PaymentHistoriesAdapter paymentHistoriesAdapter;
    private RecyclerView paymenthistoriesView;

    private ArrayList<PaymentHistories> paymentHistoriesArrayList;

    private Context context = this;
    private List<Integer> arrayList;
    private TextView tv_nodata;
    private String commonflag;
    private ActionBar actionBar;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_histories);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        paymenthistoriesView = findViewById(R.id.paymenthistoriesView);
        tv_nodata = findViewById(R.id.tv_nodata);
        paymentHistoriesArrayList = new ArrayList();


        // arrayList = new ArrayList<>();
        // arrayList.addAll(object.getAssignedBatchesIds());
      /*  arrayList.add(1);
        arrayList.add(29);
        arrayList.add(45);
        arrayList.add(44);
        Log.e("arr", "===" + arrayList);*/
        paymenthistoriesDetailsDetails();

        paymenthistoriesView.setHasFixedSize(true);
        paymenthistoriesView.setLayoutManager(new LinearLayoutManager(this));
        paymentHistoriesAdapter = new PaymentHistoriesAdapter(this, paymentHistoriesArrayList, context);
        paymenthistoriesView.setAdapter(paymentHistoriesAdapter);
        paymentHistoriesAdapter.notifyDataSetChanged();

        Log.e("Final course", "---" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME));
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                paymenthistoriesDetailsDetails();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        paymentHistoriesAdapter.SetOnItemClickListener(new PaymentHistoriesAdapter.OnItemClickListener() {
            @Override
            public void onView(View view, PaymentHistories position) {

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
                paymentHistoriesAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                paymentHistoriesAdapter.getFilter().filter(query);
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

    private void paymenthistoriesDetailsDetails() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");

                RetrofitAPI.getInstance(this).getApi().getpaymenthistoriesDetails(new Callback<PaymentHistoriesResponse>() {
                    @Override
                    public void success(PaymentHistoriesResponse object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                paymentHistoriesArrayList.clear();
                                paymentHistoriesArrayList.addAll(object.getPaymentHistories());
                                if (paymentHistoriesArrayList.size() <= 0) {
                                    paymenthistoriesView.setVisibility(View.GONE);
                                    //etSearchdefaulters.setVisibility(View.GONE);
                                    tv_nodata.setVisibility(View.VISIBLE);
                                    tv_nodata.setText("No Data Available");
                                } else {
                                    paymenthistoriesView.setVisibility(View.VISIBLE);
                                    //etSearchdefaulters.setVisibility(View.VISIBLE);
                                    tv_nodata.setVisibility(View.GONE);
                                    tv_nodata.setText("");
                                }
                                paymentHistoriesAdapter.notifyDataSetChanged();
                                Log.e("data", "---" + paymentHistoriesArrayList.toString());

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
                                Toast.makeText(getApplicationContext(), "Please check you must be signed into the some other device", Toast.LENGTH_SHORT).show();
                                UIUtil.stopProgressDialog(getApplicationContext());
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
