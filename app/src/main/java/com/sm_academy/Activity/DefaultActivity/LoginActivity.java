package com.sm_academy.Activity.DefaultActivity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;

import com.sm_academy.API.RetrofitAPI;
import com.sm_academy.Activity.LearnerActivity.LearnerDashBoardActivity;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.BatchTiming;
import com.sm_academy.ModelClass.Photo;
import com.sm_academy.ModelClass.SigninResponse;
import com.sm_academy.ModelClass.SigninUser;
import com.sm_academy.ModelClass.StatusResponse;
import com.sm_academy.R;
import com.sm_academy.Utility.Constants;
import com.sm_academy.Utility.UIUtil;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUserName, etPassword, etEmail;
    private TextView btnSubmit, btnReset, btnGoBack, btnRegistration;
    private List<String> rolelist;
    private ImageView passvisible, passinvisible, fb_image, youtube_image, twitter_image, in_image;
    private TextView sitelink, forgetpassView;
    LinearLayout loginView, resetView;
    public static final int TIME_OUT = 10000;
    public Handler handler;
    private String playstoreVersion, currentAppVersionCode;
    private ImageView imageView2;
    private TextView footertext, inmeghtext;
    private ImageView image1;
    private SigninUser signinUser = new SigninUser();
    private ImageView ImageView01;
    private List<String> arrayList;
    private boolean isFirstImage = true;
    private static final int TIME_DELAY = 2000;
    private Boolean exit = false;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Context context = LoginActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);

        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);

        loginView = findViewById(R.id.loginView);
        resetView = findViewById(R.id.ResetView);

        sitelink = findViewById(R.id.sitelink);
        forgetpassView = findViewById(R.id.forgetpassView);

        rolelist = new ArrayList<String>();

        fb_image = findViewById(R.id.fb_image);
        youtube_image = findViewById(R.id.youtube_image);
        twitter_image = findViewById(R.id.twitter_image);
        in_image = findViewById(R.id.in_image);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnRegistration = findViewById(R.id.btnRegistration);
        btnReset = findViewById(R.id.btnReset);
        btnGoBack = findViewById(R.id.btnGoBack);

        Log.e("ACCOUNT FLAG", "---" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.ACCOUNT_FLAG));
        Log.e(" course", "---" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.COURSE_NAME));

        // ImageView01 = findViewById(R.id.ImageView01);

        passvisible = findViewById(R.id.passvisible);
        passinvisible = findViewById(R.id.passinvisible);

     /*   etPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);*/

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        btnSubmit.setOnClickListener(this);
        btnGoBack.setOnClickListener(this);
        btnRegistration.setOnClickListener(this);
        btnReset.setOnClickListener(this);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        ObjectAnimator animation = ObjectAnimator.ofFloat(ImageView01, "rotationY", 0.0f, 360f);
        animation.setDuration(5000);
        animation.setRepeatCount(ObjectAnimator.INFINITE);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.start();

        sitelink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                v.startAnimation(myAnim);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://exalogic.in/"));
                startActivity(intent);
            }
        });

        forgetpassView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                v.startAnimation(myAnim);*/
                loginView.setVisibility(View.GONE);
                resetView.setVisibility(View.VISIBLE);
            }
        });

        fb_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                v.startAnimation(myAnim);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.facebook.com/exalogicsolns/?fref=ts"));
                startActivity(intent);

            }
        });
        youtube_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                v.startAnimation(myAnim);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.youtube.com/channel/UCd1cJMDRK2y3phyi3NnDEFA"));
                startActivity(intent);
            }
        });
        twitter_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                v.startAnimation(myAnim);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://twitter.com/ExalogicSolns"));
                startActivity(intent);

            }
        });
        in_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation myAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                v.startAnimation(myAnim);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.linkedin.com/company/exalogic-solutions"));
                startActivity(intent);
            }
        });

        passinvisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int start, end;
                passvisible.setVisibility(View.VISIBLE);
                passinvisible.setVisibility(View.GONE);
                start = etPassword.getSelectionStart();
                end = etPassword.getSelectionEnd();
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                etPassword.setSelection(start, end);

            }
        });
        passvisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int start, end;
                passvisible.setVisibility(View.GONE);
                passinvisible.setVisibility(View.VISIBLE);
                start = etPassword.getSelectionStart();
                end = etPassword.getSelectionEnd();
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                etPassword.setSelection(start, end);
            }
        });

        etUserName.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button

                if (event.getAction() == KeyEvent.ACTION_DOWN)
                    if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) ||
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {

                        login();

                        // Toast.makeText(HelloFormStuff.this, edittext.getText(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                return false;
            }
        });
        etPassword.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                passinvisible.setVisibility(View.VISIBLE);
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                    if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) ||
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {

                        login();

                        //Toast.makeText(HelloFormStuff.this, edittext.getText(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                return false;
            }

        });
        etEmail.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button

                if (event.getAction() == KeyEvent.ACTION_DOWN)
                    if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) ||
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {

                        //reset();

                        // Toast.makeText(HelloFormStuff.this, edittext.getText(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                return false;
            }
        });
    }


   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            LoginActivity.this.finish();
            System.exit(0);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
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
    }*/

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

        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnSubmit:
                final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
                v.startAnimation(myAnim);
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                Bundle bundle = new Bundle();
                //bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "akshay");
                //bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                if (validateCredentials())
                    login();
                break;
            case R.id.btnRegistration:
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            case R.id.btnGoBack:
                // final Animation myAnim1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                // v.startAnimation(myAnim1);
                loginView.setVisibility(View.VISIBLE);
                resetView.setVisibility(View.GONE);
                break;

            case R.id.btnReset:
                final Animation myAnim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                v.startAnimation(myAnim2);
                reset();
                break;
        }
    }

    private boolean validateCredentials() {

        if (etUserName.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter username", Toast.LENGTH_SHORT).show();
            // etUserName.setError("Please enter username");
            return false;
        }

        if (etPassword.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter Password", Toast.LENGTH_SHORT).show();
            //etPassword.setError("Please enter Password");
            return false;
        }

        return true;
    }

    private boolean validateCredentialsforForgetPassword() {

        if (etEmail.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter Email/Mobile Number", Toast.LENGTH_SHORT).show();
            //etEmail.setError("Please enter Email");
            return false;
        }

        return true;
    }

    private void login() {
        try {
            if (validateCredentials()) {
                if (UIUtil.isInternetAvailable(this)) {
                    UIUtil.startProgressDialog(this, "Signing .....");

                    JsonObject jsonObject = new JsonObject();
                    JsonObject jsonObject1 = new JsonObject();
                    //jsonObject.addProperty("user", etUserName.getText().toString());
                    jsonObject1.addProperty("login", etUserName.getText().toString());
                    jsonObject1.addProperty("password", etPassword.getText().toString());
                    jsonObject.add("user", jsonObject1);
                    //jsonObject.addProperty("devise_id", ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId());
                    jsonObject.addProperty("registration_id", FirebaseInstanceId.getInstance().getToken());
                    //jsonObject.addProperty("mobile_os", "Android");

                    Log.e("jsonObject", "jsonObject : " + jsonObject.toString());

                    RetrofitAPI.getInstance(this).getApi().signIn(jsonObject, new Callback<SigninResponse>() {

                        @Override
                        public void success(SigninResponse jsonObject, Response response) {
                            try {
                                Log.e("sucessabc", "" + response.getStatus());

                                Log.e("Json ", "Hhd---" + jsonObject.toString());
                                Log.e("Json ", "response---" + response.getBody());

                                if (response.getStatus() == Constants.SUCCESS) {
                                    UIUtil.stopProgressDialog(getApplicationContext());

                                    bindData(jsonObject.getUser());

                                    //Log.e("KEY_ACTIVE_COURSE", "-=-=-=-=-=----=-=-=-=-=-=  " + PreferencesManger.getBooleanFields(getApplicationContext(), Constants.Pref.KEY_ACTIVE_COURSE));

                                    //mFirebaseAnalytics = FirebaseAnalytics.getInstance(LoginActivity.this);
                                    Log.e("Token", "-=-=-=-=-=----=-=-=-=-=-= jsonObject " + jsonObject.toString());
                                    Log.e("flag", "---" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Learner"));

                                    Log.e("trueorfalse", "---" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_ACTIVE_COURSE).equalsIgnoreCase("false"));
                                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_TOKEN, jsonObject.getAuthToken());
                                    PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE, jsonObject.getUserType());
                                    if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Learner")) {
                                        //  Toast.makeText(getApplicationContext(), "Login successful..", Toast.LENGTH_SHORT).show();
                                        Log.e("ACCOUNT_FLAG", "-==-= abc " + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.ACCOUNT_FLAG));
                                        Log.e("KEY_USER_TYPE", "-==-= abc " + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE));
                                        Log.e("DOB", "-==-= abc " + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_DATE_OF_BIRTH));

                                        Log.e("KEY_ACTIVE_COURSE", "-==-= abc " + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_ACTIVE_COURSE));
                                        if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_ACTIVE_COURSE).equalsIgnoreCase("true")) {
                                            if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.PT_TAKEN).equalsIgnoreCase("true")) {
                                                startActivity(new Intent(getApplicationContext(), LearnerDashBoardActivity.class));
                                                finishAffinity();
                                            } else {
                                                startActivity(new Intent(getApplicationContext(), PteTestStartActivity.class));
                                                finishAffinity();
                                            }

                                          /*  Intent intent = new Intent(getApplicationContext(), LearnerDashBoardActivity.class);
                                            startActivity(intent);
                                            Log.e("aaaa", "-=-=-=-=-=----=-=-=-=-=-= abc ");
                                            finishAffinity();*/
                                        } else {
                                            if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.ACCOUNT_FLAG).equalsIgnoreCase("1")) {
                                                Intent intent = new Intent(getApplicationContext(), MainLandingActivity.class);
                                                // PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.LOGIN_FLAG, "1");
                                                //intent.putExtra("Check", false);
                                                Log.e("bbbb", "-=-=-=-=-=----=-=-=-=-=-= abc ");
                                                startActivity(intent);
                                                finishAffinity();
                                            } else {
                                                Intent intent = new Intent(getApplicationContext(), BatchDetailsTabActivity.class);
                                                // PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.LOGIN_FLAG, "1");
                                                //intent.putExtra("Check", false);
                                                Log.e("ccccc", "-=-=-=-=-=----=-=-=-=-=-= abc ");
                                                startActivity(intent);
                                                finishAffinity();
                                            }
                                        }
                                    } else {
                                        UIUtil.stopProgressDialog(getApplicationContext());
                                        Toast.makeText(getApplicationContext(), jsonObject.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Admin")) {
                                        Toast.makeText(getApplicationContext(), "Login successful..", Toast.LENGTH_SHORT).show();
                                        Log.e("key course", "---" + PreferencesManger.getBooleanFields(getApplicationContext(), Constants.Pref.KEY_ACTIVE_COURSE));

                                        if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_ACTIVE_COURSE).equalsIgnoreCase("true")) {
                                            if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.PT_TAKEN).equalsIgnoreCase("true")) {
                                                Intent intent = new Intent(getApplicationContext(), LearnerDashBoardActivity.class);
                                                intent.putExtra("ptetaken", signinUser.getPteTaken());
                                                startActivity(intent);
                                                finishAffinity();
                                            } else {
                                                startActivity(new Intent(getApplicationContext(), PteTestStartActivity.class));
                                                finishAffinity();
                                            }
                                            /*Intent intent = new Intent(getApplicationContext(), LearnerDashBoardActivity.class);
                                            intent.putExtra("ptetaken", signinUser.getPteTaken());
                                            startActivity(intent);
                                            finishAffinity();*/
                                        } else {
                                            if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.ACCOUNT_FLAG).equalsIgnoreCase("1")) {
                                                Intent intent = new Intent(getApplicationContext(), MainLandingActivity.class);
                                                // PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.LOGIN_FLAG, "1");
                                                //intent.putExtra("Check", false);
                                                startActivity(intent);
                                                finishAffinity();
                                            } else {
                                                Intent intent = new Intent(getApplicationContext(), BatchDetailsTabActivity.class);
                                                // PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.LOGIN_FLAG, "1");
                                                //intent.putExtra("Check", false);
                                                startActivity(intent);
                                                finishAffinity();
                                            }
                                        }
                                    } else {
                                        UIUtil.stopProgressDialog(getApplicationContext());
                                        Toast.makeText(getApplicationContext(), jsonObject.getMessage(), Toast.LENGTH_SHORT).show();
                                    }


                                  /*  if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Teaching Staff")) {
                                        Toast.makeText(getApplicationContext(), "Login successfully..", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), StaffLandingActivity.class);

                                        //intent.putExtra("Check", false);
                                        startActivity(intent);
                                        finishAffinity();
                                    }*/
                                } else {
                                    UIUtil.stopProgressDialog(getApplicationContext());
                                    Toast.makeText(getApplicationContext(), jsonObject.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.e("error", "" + error.getCause());
                            Log.e("msg", "" + error.getMessage());
                            //error.getBody();
                            if (error.getMessage().toString().equals("401 Unauthorized")) {
                                Toast.makeText(getApplicationContext(), "Invalid username and password", Toast.LENGTH_SHORT).show();
                            }
                            UIUtil.stopProgressDialog(getApplicationContext());
                        }
                    });
                } else {
                    Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindData(SigninUser signinUser) {
        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_USERNAME, signinUser.getUsername());
        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_EMAIL, signinUser.getEmail());
        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_USER_ID, signinUser.getId().toString());
        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_ACTIVE_COURSE, signinUser.getActiveCourse().toString());
        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_DATE_OF_BIRTH, signinUser.getDateOfBirth());
        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.COMMON_NAME, signinUser.getFirstName() + " " + signinUser.getLastName());

        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.LOGIN_CONDITION_MESSAGE, signinUser.getLoginConditionMessage());

        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.FIRST_NAME, signinUser.getFirstName());
        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.LAST_NAME, signinUser.getLastName());
        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.MOBILE, signinUser.getMobileNumber());
        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.GENDER, signinUser.getGender());

        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.PT_TAKEN, signinUser.getPteTaken());
        bindPhoto(signinUser.getPhoto());

        //PreferencesManger.addBooleanFields(getApplicationContext(), Constants.Pref.KEY_ACTIVE_COURSE, signinUser.getActiveCourse());


   /*     if (signinUser.getActiveCourse().equals(true)) {
            PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_ACTIVE_COURSE, "False");
        } else {
            PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_ACTIVE_COURSE, "true");
        }*/
        Log.e("PT_TAKEN", PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.PT_TAKEN));

        if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_ACTIVE_COURSE).equals("true")) {
            Log.e("KEY_ACTIVE_COURSEhhh", "" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_ACTIVE_COURSE).equals("true"));

        }
    }

    private void bindPhoto(Photo photo) {
        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_PROFILE_IMAGE, photo.getAttachment());
        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_PROFILE_IMAGE_ID, photo.getId());
    }

    private void reset() {
        try {
            if (validateCredentialsforForgetPassword()) {
                if (UIUtil.isInternetAvailable(this)) {
                    //  UIUtil.startProgressDialog(this, "Signing .....");

                    JsonObject jsonObject = new JsonObject();
                    // JsonObject jsonObject1 = new JsonObject();
                    jsonObject.addProperty("login", etEmail.getText().toString());
                    //jsonObject1.add("user", jsonObject);

                    RetrofitAPI.getInstance(this).getApi().reset(jsonObject, new Callback<StatusResponse>() {

                        @Override
                        public void success(StatusResponse object, Response response) {
                            try {
                                if (object == null) {
                                    Toast.makeText(LoginActivity.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (response.getStatus() == Constants.SUCCESS) {
                                    etEmail.setText("");
                                    loginView.setVisibility(View.VISIBLE);
                                    resetView.setVisibility(View.GONE);
                                    Toast.makeText(LoginActivity.this, object.getMessage(), Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(getApplicationContext(), object.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(LoginActivity.this, "Something went wrong, try after sometime...", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {

                            Toast.makeText(getApplicationContext(), "Server is down", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}