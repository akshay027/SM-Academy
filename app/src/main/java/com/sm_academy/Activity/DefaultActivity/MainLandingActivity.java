package com.sm_academy.Activity.DefaultActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sm_academy.API.RetrofitAPI;

import com.sm_academy.Activity.LearnerActivity.LearnerDashBoardActivity;
/*import com.sm_academy.Activity.LearnerActivity.ProgressActivity;*/
import com.sm_academy.Adapters.CustomAdapter;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.MainCoursesDetails;
import com.sm_academy.ModelClass.MainCoursesDetailsResponse;
import com.sm_academy.R;
import com.sm_academy.Utility.Constants;
import com.sm_academy.Utility.UIUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainLandingActivity extends AppCompatActivity {
    private LinearLayout ieltsView, greView, tofelsView, pteView, linkView;
    private static final int TIME_DELAY = 2000;
    private Boolean exit = false;
    private TextView welcomeMsgView;
    private GridView simpleGrid;
    private CustomAdapter customAdapter;
    private CircleImageView imageView;
    private ArrayList<MainCoursesDetails> mainCoursesDetailsArrayList;
    ActionBar actionBar;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_landing);
        actionBar = getSupportActionBar();
        //actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bookexam));
        linkView = findViewById(R.id.linkView);

        ieltsView = findViewById(R.id.ieltsView);
        greView = findViewById(R.id.greView);
        tofelsView = findViewById(R.id.tofelsView);
        pteView = findViewById(R.id.pteView);
        imageView = findViewById(R.id.imageView);

        if (TextUtils.isEmpty(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COMMON_NAME))) {
            actionBar.setTitle("SM Academy ");
        } else {
            actionBar.setTitle(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COMMON_NAME));
        }

        mainCoursesDetailsArrayList = new ArrayList<>();

        Picasso.with(this).load(R.drawable.booktestimg).resize(80, 80).into(imageView);

        simpleGrid = findViewById(R.id.simpleGridView);
        customAdapter = new CustomAdapter(getApplicationContext(), mainCoursesDetailsArrayList);
        simpleGrid.setAdapter(customAdapter);

        // init GridView

        // Create an object of CustomAdapter and set Adapter to GirdView

        // customAdapter = new CustomAdapter(getApplicationContext(), mainCoursesDetailsArrayList);
        //simpleGrid.setAdapter(customAdapter);
        //customAdapter.notifyDataSetChanged();
        courseDetails();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                courseDetails();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        // implement setOnItemClickListener event on GridView
        simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.COURSE_ID, mainCoursesDetailsArrayList.get(position).getId().toString());
                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME, mainCoursesDetailsArrayList.get(position).getUpcasedName());
               /* if (mainCoursesDetailsArrayList.get(position).getUpcasedName().equals("IELTS")) {
                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME, "IELTS");
                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.COURSE_ID, mainCoursesDetailsArrayList.get(position).getId().toString());
                }
                if (mainCoursesDetailsArrayList.get(position).getUpcasedName().equals("GRE")) {
                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME, "GRE");
                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.COURSE_ID, mainCoursesDetailsArrayList.get(position).getId().toString());
                }
                if (mainCoursesDetailsArrayList.get(position).getUpcasedName().equals("TOFELS")) {
                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME, "TOFELS");
                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.COURSE_ID, mainCoursesDetailsArrayList.get(position).getId().toString());
                }
                if (mainCoursesDetailsArrayList.get(position).getUpcasedName().equals("PTE")) {
                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME, "PTE");
                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.COURSE_ID, mainCoursesDetailsArrayList.get(position).getId().toString());
                }*/

                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.ACCOUNT_FLAG, "0");

                startActivity(new Intent(getApplicationContext(), AboutCourseActivity.class));

            }
        });

        welcomeMsgView = findViewById(R.id.welcomeMsgView);
        welcomeMsgView.setSelected(true);

        linkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Comming Soon !", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getApplicationContext(), AccountSettingActivity.class));

            }
        });
       /* ieltsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME, "IELTS");
                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.ACCOUNT_FLAG, "0");
                Log.e("course1", "---" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME));
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Admin")) {
                    startActivity(new Intent(getApplicationContext(), BatchActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }
        });
        greView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME, "GRE");
                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.ACCOUNT_FLAG, "0");
                Log.e("course2", "---" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME));
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Admin")) {

                    startActivity(new Intent(getApplicationContext(), BatchActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }
        });
        tofelsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME, "TOFELS");
                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.ACCOUNT_FLAG, "0");
                Log.e("course3", "---" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME));
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Admin")) {
                    startActivity(new Intent(getApplicationContext(), BatchActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }
        });
        pteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME, "PTE");
                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.ACCOUNT_FLAG, "0");
                Log.e("course4", "---" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME));
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Admin")) {
                    startActivity(new Intent(getApplicationContext(), BatchActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }
        });*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Admin")) {
            menu.findItem(R.id.account).setVisible(false);
            menu.findItem(R.id.logout).setVisible(true);
            menu.findItem(R.id.dashboard).setVisible(true);
            // menu.findItem(R.id.profile).setVisible(true);
        } else if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Learner")) {
            menu.findItem(R.id.account).setVisible(false);
            menu.findItem(R.id.logout).setVisible(true);
            menu.findItem(R.id.dashboard).setVisible(true);
            //menu.findItem(R.id.profile).setVisible(true);
        } else {
            menu.findItem(R.id.account).setVisible(true);
            menu.findItem(R.id.logout).setVisible(false);
            menu.findItem(R.id.dashboard).setVisible(false);
            // menu.findItem(R.id.profile).setVisible(false);
        }

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.profile) {
            Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
            // alertBox();
        }*/
        if (id == R.id.dashboard) {
            // alertBox();
            if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_ACTIVE_COURSE).equalsIgnoreCase("true")) {
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.PT_TAKEN).equalsIgnoreCase("true")) {
                    startActivity(new Intent(getApplicationContext(), LearnerDashBoardActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), PteTestStartActivity.class));
                }

            } else {
                Toast.makeText(getApplicationContext(), PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.LOGIN_CONDITION_MESSAGE), Toast.LENGTH_LONG).show();
            }
        }
        if (id == android.R.id.home) {
            MainLandingActivity.this.finish();
            System.exit(0);
        }
        switch (item.getItemId()) {

            case R.id.account:
                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.ACCOUNT_FLAG, "1");
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                //showLogoutConfirmation();
                return true;
            case R.id.logout:
                //startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                showLogoutConfirmation();
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

    @Override
    public void onBackPressed() {
        if (exit) {
            finishAffinity(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);
        }
    }

    private void logout() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. logging out..");
                //Map<String, String> params = new HashMap<String, String>();
                // params.put("branch_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_BRANCH_ID));
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
                        try {
                            PreferencesManger.clearPreferences(getApplicationContext());
                            UIUtil.stopProgressDialog(getApplicationContext());
                            Toast.makeText(getApplicationContext(), "Logout successfully..", Toast.LENGTH_SHORT).show();
                            // PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.LOGIN_FLAG, "0");
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainLandingActivity.class));
                            overridePendingTransition(R.anim.left_to_center, R.anim.center_to_right);
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

    private void courseDetails() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");
                RetrofitAPI.getInstance(this).getApi().getCourseDetails(new Callback<MainCoursesDetailsResponse>() {
                    @Override
                    public void success(MainCoursesDetailsResponse object, Response response) {
                        try {

                            UIUtil.stopProgressDialog(getApplicationContext());
                            mainCoursesDetailsArrayList.clear();
                            mainCoursesDetailsArrayList.addAll(object.getCourses());
                            customAdapter = new CustomAdapter(getApplicationContext(), mainCoursesDetailsArrayList);
                            simpleGrid.setAdapter(customAdapter);
                            // customAdapter.notifyDataSetChanged();
                            Log.e("data", "---" + mainCoursesDetailsArrayList.toString());
                            // Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();

                              /*  UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();*/

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        try {
                            Log.e("error", "" + error.getCause());
                            Log.e("msg", "" + error.getMessage());
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
