package com.sm_academy.Activity;

import android.app.Application;

import java.util.Timer;
import java.util.TimerTask;

public class MyApp extends Application {
//900000
    private LogoutListener listener;
    private Timer timer;

    public void stratUserSession() {
        cancelTimer();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                listener.onSessionLogout();
            }
        }, 900000);
    }

    public void cancelTimer() {
        if (timer != null)
            timer.cancel();
    }

    public void registerSessionListener(LogoutListener logoutListener) {
        this.listener = logoutListener;
    }

    public void onUserInteracted() {
        stratUserSession();
    }
}
