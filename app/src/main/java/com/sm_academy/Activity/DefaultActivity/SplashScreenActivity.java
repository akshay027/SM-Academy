package com.sm_academy.Activity.DefaultActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import com.sm_academy.Activity.LearnerActivity.LearnerDashBoardActivity;
import com.sm_academy.Activity.LearnerActivity.PracticeTestStartActivity;
import com.sm_academy.R;

import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.ModelClass.VersionChecker;
import com.sm_academy.Utility.Constants;
import com.sm_academy.Utility.UIUtil;

import java.util.concurrent.ExecutionException;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.sm_academy.Activity.LearnerActivity.PracticeActiveTestActivity.RequestPermissionCode;


public class SplashScreenActivity extends AppCompatActivity {
    public static final int TIME_OUT = 2000;
    public Handler handler;
    private String playstoreVersion, currentAppVersionCode;
    private ImageView imageView2;
    private TextView footertext, inmeghtext;
    private ImageView image1;

    private ImageView image2;

    private boolean isFirstImage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //imageView2 = (ImageView) findViewById(R.id.imageView2);
        //Animation animation = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        Animation startRotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        //imageView2.startAnimation(startRotateAnimation);
        //PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_TOKEN, "e70039dbff086a2f1db1fb80b983c481");
        //imageView2.startAnimation(animation);

        Log.e("token asdas", "---" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_TOKEN));
        Log.e("token PT_TAKEN", "---" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.PT_TAKEN));

        Log.e("keytype asdas", "---" + PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE));
        VersionChecker versionChecker = new VersionChecker();
        try {
            playstoreVersion = versionChecker.execute().get();
            Log.e("play store versionCode", "==========" + playstoreVersion);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            currentAppVersionCode = String.valueOf(pInfo.versionName);
            Log.e("App versionCode", "==========" + currentAppVersionCode);
            //  Log.e("versionCode", "==========" + playStoreVersionCode);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        handler = new Handler();
        if (checkPermission()) {


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (UIUtil.isInternetAvailable(getApplicationContext())) {
                        if (playstoreVersion == null) {
                            Intent intent = new Intent(getApplicationContext(), InternetConnectionActivity.class);

                            startActivity(intent);
                            SplashScreenActivity.this.finish();

                        } else {
                            if (playstoreVersion.compareTo(currentAppVersionCode) > 0) {
//Show update popup or whatever best for you
                                startActivity(new Intent(getApplicationContext(), UpdatePopupActivity.class));
                                SplashScreenActivity.this.finish();

                            } else {
                                //startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
//                  startActivity(new Intent(SplashScreenActivity.this, ClassTeacherLeaveTabActivity.class));
                                if (TextUtils.isEmpty(PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_TOKEN))) {

                                    Intent intent = new Intent(SplashScreenActivity.this, MainLandingActivity.class);

                                    // intent.putExtra("Check", false);
                                    startActivity(intent);
                                    SplashScreenActivity.this.finish();

                                } /*else if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Admin")) {

                            Intent intent = new Intent(SplashScreenActivity.this, HodLandingActivity.class);

                            //intent.putExtra("Check", false);
                            startActivity(intent);
                        } */ else if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Admin")) {
                                    if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_ACTIVE_COURSE).equalsIgnoreCase("true")) {
                                        if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.PT_TAKEN).equalsIgnoreCase("true")) {
                                            startActivity(new Intent(getApplicationContext(), LearnerDashBoardActivity.class));
                                            finishAffinity();
                                        } else {
                                            startActivity(new Intent(getApplicationContext(), PteTestStartActivity.class));
                                            finishAffinity();
                                        }
                               /* Intent intent = new Intent(getApplicationContext(), LearnerDashBoardActivity.class);
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


                                } else if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Learner")) {

                                    if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_ACTIVE_COURSE).equalsIgnoreCase("true")) {
                            /*    Intent intent = new Intent(getApplicationContext(), LearnerDashBoardActivity.class);
                                startActivity(intent);
                                finishAffinity();*/
                                        if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.PT_TAKEN).equalsIgnoreCase("true")) {
                                            startActivity(new Intent(getApplicationContext(), LearnerDashBoardActivity.class));
                                            finishAffinity();
                                        } else {
                                            startActivity(new Intent(getApplicationContext(), PteTestStartActivity.class));
                                            finishAffinity();
                                        }
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

                                }
                                if (PreferencesManger.getStringFields(getApplicationContext(), Constants.Pref.KEY_USER_TYPE).equalsIgnoreCase("Teaching Staff")) {

                                  /*  Intent intent = new Intent(SplashScreenActivity.this, StaffLandingActivity.class);
                                    startActivity(intent);
                                    SplashScreenActivity.this.finish();*/

                                }
                            }
                        }
                    } else {
                        Intent intent = new Intent(getApplicationContext(), InternetConnectionActivity.class);
                        startActivity(intent);
                        SplashScreenActivity.this.finish();
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
                SplashScreenActivity.this.finish();
            }
        }, TIME_OUT);
        } else {
            requestPermission();
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(SplashScreenActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }
}