package com.sm_academy.Activity.DefaultActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.sm_academy.API.RetrofitAPI;
import com.sm_academy.Activity.LearnerActivity.LearnerDashBoardActivity;
import com.sm_academy.Adapters.AboutCourseFieldAdapter;
import com.sm_academy.Adapters.CustomAdapter;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.AboutCourse.AboutCourseResponse;
import com.sm_academy.ModelClass.AboutCourse.CourseData;
import com.sm_academy.ModelClass.AboutCourse.Section;
import com.sm_academy.ModelClass.MainCoursesDetailsResponse;
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

public class AboutCourseActivity extends AppCompatActivity {
    private TextView btnOne, btnTwo, btnMessage;
    private ReadMoreTextView aboutCourseView;
    private ActionBar actionBar;
    private ListView coursefieldView;
    private AboutCourseFieldAdapter aboutCourseFieldAdapter;
    private ArrayList<Section> mainCoursesDetailsArrayList;
    private CourseData courseData;
    private ArrayList<String> btnArrayList;
    TextView plus, minus;
    private LinearLayout descriptionMainView;
    private RelativeLayout btnView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_course);

        actionBar = getSupportActionBar();
        actionBar.setTitle(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME));

        descriptionMainView = findViewById(R.id.descriptionMainView);
        descriptionMainView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_scale_animation));
        coursefieldView = findViewById(R.id.coursefieldView);
        aboutCourseView = findViewById(R.id.aboutCourseView);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        btnView = findViewById(R.id.btnView);
        btnMessage = findViewById(R.id.btnMessage);

        plus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                plus.setVisibility(View.GONE);
                minus.setVisibility(View.VISIBLE);
                aboutCourseView.setMaxLines(Integer.MAX_VALUE);
                //btnView.setVisibility(View.GONE);

            }
        });

        minus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                minus.setVisibility(View.GONE);
                plus.setVisibility(View.VISIBLE);
                aboutCourseView.setMaxLines(5);
                //btnView.setVisibility(View.VISIBLE);
            }
        });
        btnOne = findViewById(R.id.btnOne);
        btnTwo = findViewById(R.id.btnTwo);

        btnArrayList = new ArrayList<>();
        mainCoursesDetailsArrayList = new ArrayList<>();

        aboutCourseFieldAdapter = new AboutCourseFieldAdapter(getApplicationContext(), mainCoursesDetailsArrayList);
        coursefieldView.setAdapter(aboutCourseFieldAdapter);

        courseFieldDetails();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                courseFieldDetails();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void courseFieldDetails() {
        try {
            if (UIUtil.isInternetAvailable(this)) {
                UIUtil.startProgressDialog(this, "Please Wait.. ");
                Map<String, String> params = new HashMap<String, String>();
                params.put("course_id", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_ID));
                //params.put("auth_token", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_TOKEN));

                RetrofitAPI.getInstance(this).getApi().getCourseFieldDetails(params, new Callback<AboutCourseResponse>() {
                    @Override
                    public void success(AboutCourseResponse object, Response response) {
                        try {
                            if (object.getStatus().equals("success")) {
                                descriptionMainView.setVisibility(View.VISIBLE);

                                UIUtil.stopProgressDialog(getApplicationContext());

                                //mainCoursesDetailsArrayList.addAll(object.getCourseData());
                                aboutCourseFieldAdapter = new AboutCourseFieldAdapter(getApplicationContext(), mainCoursesDetailsArrayList);
                                coursefieldView.setAdapter(aboutCourseFieldAdapter);
                                bindata(object.getCourseData());
                                // customAdapter.notifyDataSetChanged();
                                Log.e("data", "---" + mainCoursesDetailsArrayList.toString());
                                // Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();


                              /*  UIUtil.stopProgressDialog(getApplicationContext());
                                Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();*/
                            } else {
                                Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();
                                descriptionMainView.setVisibility(View.GONE);
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
                                descriptionMainView.setVisibility(View.GONE);
                                logout();
                            } else {
                                descriptionMainView.setVisibility(View.GONE);
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

    public void bindata(CourseData courseData) {
        mainCoursesDetailsArrayList.clear();
        // aboutCourseView.setText("pppppppppppppppppppppppppppppp\n\nkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
        aboutCourseView.setText(courseData.getDescription());
        // makeTextViewResizable(aboutCourseView, 3, "See More", true);
        mainCoursesDetailsArrayList.addAll(courseData.getSections());
        btnArrayList.addAll(courseData.getButtons());
        Log.e("size", "===" + courseData.getButtons().size());
        if (TextUtils.isEmpty(courseData.getButtonMessage())) {
            // btnOne.setVisibility(View.VISIBLE);
            // btnTwo.setVisibility(View.VISIBLE);
            btnMessage.setVisibility(View.GONE);
            if (courseData.getButtons().size() >= 2) {
                btnOne.setVisibility(View.VISIBLE);
                btnTwo.setVisibility(View.VISIBLE);
                btnOne.setText(btnArrayList.get(0));
                btnTwo.setText(btnArrayList.get(1));

            } else if (courseData.getButtons().size() < 2) {
                btnOne.setVisibility(View.VISIBLE);
                btnTwo.setVisibility(View.GONE);
                btnOne.setText(btnArrayList.get(0));
            } else {
                btnOne.setVisibility(View.GONE);
                btnTwo.setVisibility(View.GONE);
            }
        } else {
            btnOne.setVisibility(View.GONE);
            btnTwo.setVisibility(View.GONE);
            btnMessage.setVisibility(View.VISIBLE);
            btnMessage.setText(courseData.getButtonMessage());
        }

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.COURSE_TYPE, btnArrayList.get(0));

                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Admin")) {
                    startActivity(new Intent(getApplicationContext(), BatchDetailsTabActivity.class));
                } else if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Learner")) {
                    startActivity(new Intent(getApplicationContext(), BatchDetailsTabActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }
        });
        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.COURSE_TYPE, btnArrayList.get(1));
                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Admin")) {
                    startActivity(new Intent(getApplicationContext(), BatchDetailsTabActivity.class));
                } else if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Learner")) {
                    startActivity(new Intent(getApplicationContext(), BatchDetailsTabActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }
        });
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
            case R.id.account:
                PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.ACCOUNT_FLAG, "1");
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                //showLogoutConfirmation();
                return true;
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



/*
   public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {


            ssb.setSpan(new MySpannable(false) {
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -1, "See Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 3, ".. See More", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

}


class MySpannable extends ClickableSpan {

    private boolean isUnderline = true;




    public MySpannable(boolean isUnderline) {
        this.isUnderline = isUnderline;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(isUnderline);
        ds.setColor(Color.parseColor("#1b76d3"));
    }

    @Override
    public void onClick(View widget) {

    }*/
}

