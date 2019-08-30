package com.sm_academy.Activity.DefaultActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sm_academy.API.RetrofitAPI;
import com.sm_academy.Activity.LearnerActivity.LearnerDashBoardActivity;
import com.sm_academy.Adapters.BatchTabAdapter;
import com.sm_academy.Adapters.LiveSessionsAdapter;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.BatchTabDetails.BatchResponse;
import com.sm_academy.ModelClass.BatchTabDetails.Batchlearnerbatch;
import com.sm_academy.R;
import com.sm_academy.Utility.Constants;
import com.sm_academy.Utility.UIUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BatchDetailsTabActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private BatchTabAdapter adapter;
    private LinearLayout leranerView;
    TextView batchView, startdateView, enddateView, seatsView, priceView, sessionHourView, header;
    ImageView registerIconView;
    LinearLayout deviderView;
    private Batchlearnerbatch batchlearnerbatch;
    String hour, seats, name, id, money;
    private View headeview;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_details_tab);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        actionBar = getSupportActionBar();
        if (TextUtils.isEmpty(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME))) {
            actionBar.setTitle("Batch Details ");
        } else {
            actionBar.setTitle(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME) + " " + "Batch Details");
        }

        // toolbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bookexam));
        deviderView = findViewById(R.id.deviderView);
        headeview = findViewById(R.id.headeview);
        header = findViewById(R.id.header);

        batchView = findViewById(R.id.batchView);
        startdateView = findViewById(R.id.startdateView);
        enddateView = findViewById(R.id.enddateView);
        seatsView = findViewById(R.id.seatsView);
        priceView = findViewById(R.id.priceView);
        sessionHourView = findViewById(R.id.sessionHourView);
        registerIconView = findViewById(R.id.registerIconView);

        leranerView = findViewById(R.id.LeranerView);
        leranerView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_scale_animation));
        //setSupportActionBar(toolbar);
        // this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        getSessionData();
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.addTab(tabLayout.newTab().setText("Upcoming Batch"));
                tabLayout.addTab(tabLayout.newTab().setText("Ongoing Batch"));
                tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

                adapter = new BatchTabAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
                mViewPager.setAdapter(adapter);

                mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        mViewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
            }
        });

    }


/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
                showLogoutConfirmation();
                return true;
     /*       case R.id.profile:
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                // showLogoutConfirmation();
                return true;*/
            case R.id.dashboard:
                if (id == R.id.dashboard) {
                    // alertBox();
                    if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.PT_TAKEN).equalsIgnoreCase("true")) {
                        startActivity(new Intent(getApplicationContext(), LearnerDashBoardActivity.class));
                    } else {
                        startActivity(new Intent(getApplicationContext(), PteTestStartActivity.class));
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

    private void getSessionData() {
        try {
            if (UIUtil.isInternetAvailable(getApplicationContext())) {
                UIUtil.startProgressDialog(getApplicationContext(), "Please Wait.. Getting Details");
                Map<String, String> params = new HashMap<String, String>();
                params.put("course_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_ID));
                params.put("type", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_TYPE));
                //Map<String, String> params = new HashMap<String, String>();
                //params.put("auth_token", PreferencesManger.getStringFields(getActivity(), Constants.Pref.KEY_TOKEN));

                RetrofitAPI.getInstance(getApplicationContext()).getApi().getBatchTabDetails(params, new Callback<BatchResponse>() {
                    @Override
                    public void success(BatchResponse defaulterResponseModel, Response response) {
                        if (defaulterResponseModel.getStatus().equals("success")) {
                            try {
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Log.e("jsonObject", "jsonObject --- " + defaulterResponseModel.toString());
                                if (defaulterResponseModel.getLearnerBatch() == null) {
                                    leranerView.setVisibility(View.GONE);
                                    header.setVisibility(View.GONE);
                                    headeview.setVisibility(View.GONE);
                                } else {
                                    leranerView.setVisibility(View.VISIBLE);
                                    header.setVisibility(View.VISIBLE);
                                    headeview.setVisibility(View.VISIBLE);
                                }
                                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.ENROLL_STATUS, defaulterResponseModel.getCanEnroll().toString());
                                bindDataForLeraner(defaulterResponseModel.getLearnerBatch());


                                // completedSessionAdapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            UIUtil.stopProgressDialog(getApplicationContext());
                            Toast.makeText(getApplicationContext(), defaulterResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
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
                 /*       listviewdefaulter.setVisibility(View.GONE);
                        searchView.setVisibility(View.GONE);
                        tv_nodata.setVisibility(View.VISIBLE);*/
                                UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), "Internal Server Error", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Please Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bindDataForLeraner(Batchlearnerbatch batchlearnerbatch) {

        hour = batchlearnerbatch.getLiveSessionHoursDisplay();
        seats = batchlearnerbatch.getSeatsLeft();
        name = batchlearnerbatch.getName();
        id = batchlearnerbatch.getId().toString();
        money = batchlearnerbatch.getPrice();

        sessionHourView.setText(hour);
        seatsView.setText(seats);
        Log.e("hourqqqq", "====" + sessionHourView.getText().toString());
        Log.e("seatsViewqqqqq", "====" + seatsView.getText().toString());
        batchView.setText(batchlearnerbatch.getName() + " ( " +
                batchlearnerbatch.getStartDateEndDateInRangeInReadableFormat() + " )");
        // holder.startdateView.setText(batchlearnerbatch.getStartDateEndDateInRangeInReadableFormat());
        //  holder.enddateView.setText(batchlearnerbatch.getEndDate());

        priceView.setText(batchlearnerbatch.getPrice().toString());

        leranerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BatchTimingActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("id", id);
                intent.putExtra("money", money);
                intent.putExtra("flag", "true");
                intent.putExtra("seats", seats);
                startActivity(intent);
            }
        });
    }
}

