package com.sm_academy.Activity.LearnerActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.sm_academy.API.RetrofitAPI;
import com.sm_academy.Activity.BaseActivity;
import com.sm_academy.Activity.DefaultActivity.AccountSettingActivity;
import com.sm_academy.Activity.DefaultActivity.MainLandingActivity;

import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.R;
import com.sm_academy.Utility.Constants;
import com.sm_academy.Utility.UIUtil;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Timer;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LearnerDashBoardActivity extends BaseActivity  {
    private int mSelectedItem;
    private static final String SELECTED_ITEM = "arg_selected_item";
    private BottomNavigationView bottomNavigationView;
    Fragment frag = null;
    ActionBar actionBar;
    boolean doubleBackToExitPressedOnce = false;
    boolean s1 = false;
    private Timer timer;
    private FirebaseAnalytics mFirebaseAnalytics;
    private boolean pte;
    private Boolean exit = false;
    private Toolbar toolbar;
    private String live_session ;
    private String notification = "false";

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dash_board);
        // actionBar = getSupportActionBar();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent intent = getIntent();
        live_session = intent.getStringExtra("live_session");
        Log.e("live_session_id", "---" + live_session);
        toolbar.setTitle("\uD83C\uDFE0 SM Academy");
        setSupportActionBar(toolbar);
        /*ColorDrawable colorDrawable = new ColorDrawable(R.color.commoncolor);
        actionBar.setBackgroundDrawable(colorDrawable);*/
    /*    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
*/
        //final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
       // navigationView.setNavigationItemSelectedListener(this);

        //View header = navigationView.getHeaderView(0);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(R.color.commoncolor));
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        getSupportActionBar();
        // actionBar.setTitle("SM Academy");
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainLandingActivity.class));
            }
        });

/*        if (actionBar != null) {
            // Disable the default and enable the custom
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            View customView = getLayoutInflater().inflate(R.layout.actionbar_title, null);
            // Get the textview of the title
            TextView customTitle = (TextView) customView.findViewById(R.id.actionbarTitle);
            customTitle.setText("SM Academy");

            // Change the font family (optional)
            // customTitle.setTypeface(Typeface.MONOSPACE);
            // Set the on click listener for the title
            customTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), MainLandingActivity.class));
                }
            });

            // Apply the custom view
            actionBar.setCustomView(customView);
        }*/

        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);
        if (live_session==null) {
            bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
            //  BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            bottomNavigationView.getMenu().getItem(0).setChecked(true);
            //bottomNavigationView.getMenu().getItem(mSelectedItem).setChecked(true);
            bottomNavigationView.setOnNavigationItemSelectedListener
                    (new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            selectFragment(item);
                            bottomNavigationView.getMenu().getItem(0).setChecked(true);
                            return true;
                        }
                    });

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, HomeNavigationTabActivity.newInstance());
            transaction.commit();
        } else if (live_session.equalsIgnoreCase("true")) {
            bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
            //  BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(R.color.commoncolor));
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            bottomNavigationView.getMenu().getItem(1).setChecked(true);
            //bottomNavigationView.getMenu().getItem(mSelectedItem).setChecked(true);
            bottomNavigationView.setOnNavigationItemSelectedListener
                    (new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            selectFragment(item);
                            bottomNavigationView.getMenu().getItem(1).setChecked(true);
                            return true;
                        }
                    });

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, OngoingLiveSessionFragment.newInstance());
            transaction.commit();

        }


    }

/*

        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        //  BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        //bottomNavigationView.getMenu().getItem(mSelectedItem).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        selectFragment(item);
                        bottomNavigationView.getMenu().getItem(0).setChecked(true);
                        return true;
                    }
                });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, HomeNavigationTabActivity.newInstance());
        transaction.commit();
*/

    //...
//when you want to start the counting start the thread bellow.



    /* @Override
     protected void onPause() {
         super.onPause();

         timer = new Timer();
         Log.i("Main", "Invoking logout timer");
         LogOutTimerTask logoutTimeTask = new LogOutTimerTask();
         timer.schedule(logoutTimeTask, 3000);
         logout();//auto logout in 5 minutes
     }

     @Override
     protected void onResume() {
         super.onResume();
         if (timer != null) {
             timer.cancel();
             Log.i("Main", "cancel timer");
             timer = null;
         }
     }

     private class LogOutTimerTask extends TimerTask {

         @Override
         public void run() {

             //redirect user to login screen
             Intent i = new Intent(HodLandingActivity.this, LoginActivity.class);
             i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             startActivity(i);
             finish();
         }
     }*/
   /* public static class BottomNavigationViewHelper {
        public static void disableShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    //noinspection RestrictedApi
                    item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    //noinspection RestrictedApi
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("BNVHelper", "Unable to get shift mode field", e);
            } catch (IllegalAccessException e) {
                Log.e("BNVHelper", "Unable to change value of shift mode", e);
            }
        }
    }*/

    // @SuppressWarnings("StatementWithEmptyBody")
   /* @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

      *//*  if (id == R.id.attendance) {
            if (Constants.CLASS_TEACHER.equalsIgnoreCase(PreferencesManger.getStringFields(this, Constants.Pref.KEY_USER_TYPE))) {
                startActivity(new Intent(this, AttendanceActivity.class));
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
                Log.i("Class Teacher Value1---", Constants.CLASS_TEACHER);
                Log.i("Key User Type Value1---", Constants.Pref.KEY_USER_TYPE);

            } else {
                Log.i("Class Teacher Value2---", Constants.CLASS_TEACHER);
                Log.i("Key User Type Value2---", Constants.Pref.KEY_USER_TYPE);
                AlertForTeacher();
            }
        } else if (id == R.id.changeRole) {
            startActivity(new Intent(this, TeacherRollActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
        } else if (id == R.id.leave) {
            if (PreferencesManger.getStringFields(this, Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase(Constants.CLASS_TEACHER)) {
                startActivity(new Intent(this, ClassTeacherLeaveTabActivity.class));
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
            } else {
                startActivity(new Intent(this, LeaveActivity.class));
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
            }
        } else if (id == R.id.timeTable) {
            if (PreferencesManger.getStringFields(this, Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase(Constants.CLASS_TEACHER)) {
                startActivity(new Intent(this, TimeTableTabActivity.class));
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
            } else {
                startActivity(new Intent(this, TimeTableActivity.class));
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
            }
        } else if (id == R.id.academics) {
            startActivity(new Intent(this, NewTeachingPlanActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);

        } else if (id == R.id.ownat) {
            startActivity(new Intent(this, OwnAttendanceActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);

        } else if (id == R.id.profile) {
            startActivity(new Intent(this, ProfileActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);

        } else if (id == R.id.schoolInfo) {
            startActivity(new Intent(this, SchoolinfoActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);

        } else if (id == R.id.studentdetails) {
            if (PreferencesManger.getStringFields(this, Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase(Constants.CLASS_TEACHER)) {
                startActivity(new Intent(this, StudentTabDetailsActivity.class));
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
            } else {
                startActivity(new Intent(this, StudentDetailActivity.class));
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
            }
        } else if (id == R.id.assignment) {
            startActivity(new Intent(this, AssignmentActivity.class));
            overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);

        } else if (id == R.id.logout) {
            showLogoutConfirmation();
        }
*//*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }*/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
    }

    private void selectFragment(MenuItem item) {

        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.home:
               /* bottomNavigationView.getMenu().getItem(0).setCheckable(true);
                bottomNavigationView.getMenu().getItem(1).setCheckable(false);
                bottomNavigationView.getMenu().getItem(2).setCheckable(false);
                bottomNavigationView.getMenu().getItem(3).setCheckable(false);*/
                //  frag = HodDashBoardFragment.newInstance();
                frag = HomeNavigationTabActivity.newInstance();
                //actionBar.setTitle("SM Academy");
                toolbar.setTitle("\uD83C\uDFE0 SM Academy");
                break;

            case R.id.video:
               /* bottomNavigationView.getMenu().getItem(0).setCheckable(false);
                bottomNavigationView.getMenu().getItem(1).setCheckable(false);
                bottomNavigationView.getMenu().getItem(2).setCheckable(true);
                bottomNavigationView.getMenu().getItem(3).setCheckable(false);*/

                frag = OngoingLiveSessionFragment.newInstance();
                break;


            case R.id.account:
                frag = AccountSettingActivity.newInstance();
                //actionBar.setTitle("Account");
                toolbar.setTitle("\uD83C\uDFE0 SM Academy");
                break;
        }

        // update selected item
        mSelectedItem = item.getItemId();

        // uncheck the other items.
        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
            MenuItem menuItem = bottomNavigationView.getMenu().getItem(i);
            menuItem.setChecked(menuItem.getItemId() == item.getItemId());
        }

        //updateToolbarText(item.getTitle());

        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_layout, frag);
            ft.commit();
        }
    }

    public void setTitle(String title) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = new TextView(this);
        textView.setText(title);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
    }

    private void updateToolbarText(CharSequence text) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(text);
        }
    }

    private int getColorFromRes(@ColorRes int resId) {
        return ContextCompat.getColor(this, resId);
    }


/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_menu, menu);
        return true;
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home) {
            LearnerDashBoardActivity.this.finish();
            System.exit(0);
        }
        //noinspection SimplifiableIfStatement

        switch (item.getItemId()) {

            case R.id.logout:
                showLogoutConfirmation();
                return true;
            case R.id.profile:
                // showLogoutConfirmation();
                Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subscription:
                startActivity(new Intent(getApplicationContext(), MySubscriptionActivity.class));

                return true;
            case R.id.paymenthistory:
                startActivity(new Intent(getApplicationContext(), PaymentHistoriesActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
}
