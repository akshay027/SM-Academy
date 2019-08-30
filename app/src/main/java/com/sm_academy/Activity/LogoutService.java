package com.sm_academy.Activity;

import android.app.Service;
import android.os.CountDownTimer;
import android.util.Log;

import com.sm_academy.Utility.Constants;

/*
public class LogoutService extends Service {
    public static CountDownTimer timer;
    @Override
    public void onCreate(){
        super.onCreate();
        timer = new CountDownTimer(1 *60 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                //Some code
               // Log.v(Constants.TAG, "Service Started");
            }

            public void onFinish() {
               // Log.v(Constants.TAG, "Call Logout by Service");
                // Code for Logout
                stopSelf();
            }
        };
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}*/
