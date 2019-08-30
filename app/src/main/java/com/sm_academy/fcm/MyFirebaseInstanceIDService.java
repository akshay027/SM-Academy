package com.sm_academy.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.sm_academy.Database.PreferencesManger;
import com.sm_academy.Utility.Constants;

/**
 * Created by Nikhil on 9/12/2016.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "APP";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        PreferencesManger.addStringFields(getApplicationContext(), Constants.Pref.KEY_FCM_REG_ID, refreshedToken);
        sendRegistrationTokenToServer(refreshedToken);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
    }

    private void sendRegistrationTokenToServer(String token) {
        // TODO: Implement this method to send any registration to your app's servers.
    }
}