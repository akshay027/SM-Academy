package com.sm_academy.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import com.sm_academy.Activity.LearnerActivity.AssignmentTestCountActivity;
import com.sm_academy.Activity.LearnerActivity.LearnerDashBoardActivity;
import com.sm_academy.Activity.LearnerActivity.LiveSessionsActivity;
import com.sm_academy.Activity.LearnerActivity.MockTestCountActivity;
import com.sm_academy.Activity.LearnerActivity.MockTestListActivity;
import com.sm_academy.Activity.LearnerActivity.MockTestScoreActivity;
import com.sm_academy.Activity.LearnerActivity.StudyMaterialsSectionTopicActivity;
import com.sm_academy.ModelClass.PushNotifications;
import com.sm_academy.R;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e(TAG, "From: " + remoteMessage.getFrom());
        Log.e(TAG, "From: " + remoteMessage.getData());
        Log.e(TAG, "From: " + remoteMessage.getData());
        Log.e(TAG, "From: " + remoteMessage.getData());
        if (remoteMessage.getNotification() != null) {
            // Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            //  showNotification(remoteMessage.getNotification().getBody());

            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            PushNotifications notifications = new PushNotifications();
            Map<String, String> stringStringMap = remoteMessage.getData();
            notifications.setDate(stringStringMap.get("date"));
            notifications.setTime(stringStringMap.get("time"));
            notifications.setAssignment_id(stringStringMap.get("assignment_id"));
            notifications.setSection_id(stringStringMap.get("section_id"));
            notifications.setSection_name(stringStringMap.get("section_name"));
            notifications.setSection_topic_id(stringStringMap.get("section_topic_id"));
            notifications.setLive_session_id(stringStringMap.get("live_session_id"));
            notifications.setMock_test_id(stringStringMap.get("mock_test_id"));
            notifications.setMock_test_category_id(stringStringMap.get("mock_test_category_id"));

            sendNotification(notifications.getSection_name(),
                    notifications.getSection_id(),
                    notifications.getSection_topic_id(),
                    notifications.getDate(),
                    notifications.getTime(),
                    notifications.getAssignment_id(),
                    notifications.getLive_session_id(),
                    notifications.getMock_test_id(),
                    notifications.getMock_test_category_id(),
                    remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody());


            // showNotification(remoteMessage.getNotification().getBody());
        }
    }

    private void showNotification(String message) {

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_date)
                .setContentTitle("SM Academy Notification")
                .setAutoCancel(true)
                .setContentText(message);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(999, notificationBuilder.build());

    }

    private void sendNotification(String section_name, String section_id, String section_topic_id, String date, String time, String assignment_id,
                                  String live_session_id, String mock_test_id, String mock_test_category_id, String title, String messageBody) {
        Log.e(TAG, "section_name: " + section_name);
        Log.e(TAG, "section_id: " + section_id);
        Log.e(TAG, "section_topic_id: " + section_topic_id);
        Log.e(TAG, "date: " + date);
        Log.e(TAG, "time: " + time);
        Log.e(TAG, "assignment_id: " + assignment_id);
        Log.e(TAG, "live_session_id: " + live_session_id);
        Log.e(TAG, "Mock_test_id: " + mock_test_id);
        Log.e(TAG, "mock_test_category_id: " + mock_test_category_id);
        Log.e(TAG, "title: " + title);
        Log.e(TAG, "messageBody " + messageBody);

        if (title.equalsIgnoreCase("Assignment")) {
            Intent intent = new Intent(this, MockTestScoreActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("id", assignment_id);
            intent.putExtra("page", "Assignment");
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.sm_noti)
                    .setContentTitle("SM Academy")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setAutoCancel(true)

                    .setContentText(messageBody)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_VIBRATE);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        } else if (title.equalsIgnoreCase("Mock Test Evaluation")) {
            Intent intent = new Intent(this, MockTestScoreActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("id", mock_test_id);
            intent.putExtra("page", "test");
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.sm_noti)
                    .setContentTitle("SM Academy")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setAutoCancel(true).setContentText(messageBody)

                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_VIBRATE);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        } else if (title.equalsIgnoreCase("Live Session")) {
            Intent intent = new Intent(this, LiveSessionsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.sm_noti)
                    .setContentTitle("SM Academy")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setAutoCancel(true).setContentText(messageBody)

                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_VIBRATE);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        } else if (title.equalsIgnoreCase("Mock Test")) {
            Intent intent = new Intent(this, MockTestCountActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("mock_test_id", mock_test_category_id);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.sm_noti)
                    .setContentTitle("SM Academy")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setAutoCancel(true).setContentText(messageBody)

                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_VIBRATE);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());
        } else if (title.equalsIgnoreCase("Ongoing Session")) {
            Intent intent = new Intent(this, LearnerDashBoardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("live_session", "true");
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.sm_noti)
                    .setContentTitle("SM Academy")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setAutoCancel(true).setContentText(messageBody)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_VIBRATE);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());

        } else if (title.equalsIgnoreCase("Study Material")) {
            Intent intent = new Intent(this, StudyMaterialsSectionTopicActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("id", section_id);
            intent.putExtra("section_name", section_name);
            intent.putExtra("readingcomponent", "Study Materials");

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.sm_noti)
                    .setContentTitle("SM Academy")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setAutoCancel(true).setContentText(messageBody)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_VIBRATE);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 , notificationBuilder.build());

        }/* else if (title.equalsIgnoreCase("event")) {
            Intent intent = new Intent(this, AdminLeaveActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("name", name);
            intent.putExtra("id", id);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 *//* Request code *//*, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.sm_logo)
                    .setContentTitle("SM Academy")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setAutoCancel(true).setContentText(messageBody)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_VIBRATE);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(id *//* ID of notification *//*, notificationBuilder.build());

        } else if (title.equalsIgnoreCase("event")) {
            Intent intent = new Intent(this, AdminAttendanceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("name", name);
            intent.putExtra("id", id);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 *//* Request code *//*, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.sm_logo)
                    .setContentTitle("SM Academy")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setAutoCancel(true).setContentText(messageBody)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_VIBRATE);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(id *//* ID of notification *//*, notificationBuilder.build());

        } else if (title.equalsIgnoreCase("event")) {
            Intent intent = new Intent(this, AssignSubstituteTeacherActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("name", name);
            intent.putExtra("id", id);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 *//* Request code *//*, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.sm_logo)
                    .setContentTitle("SM Academy")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setAutoCancel(true).setContentText(messageBody)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_VIBRATE);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(id *//* ID of notification *//*, notificationBuilder.build());

        } else if (title.equalsIgnoreCase("event")) {
            Intent intent = new Intent(this, FeesPaymentMOdule.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("name", name);
            intent.putExtra("id", id);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 *//* Request code *//*, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.sm_logo)
                    .setContentTitle("SM Academy")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setAutoCancel(true).setContentText(messageBody)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_VIBRATE);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(id *//* ID of notification *//*, notificationBuilder.build());

        } else if (title.equalsIgnoreCase("event")) {
            Intent intent = new Intent(this, ParentLeaveActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("name", name);
            intent.putExtra("id", id);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 *//* Request code *//*, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.sm_logo)
                    .setContentTitle("SM Academy")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setAutoCancel(true).setContentText(messageBody)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_VIBRATE);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(id *//* ID of notification *//*, notificationBuilder.build());

        } else if (title.equalsIgnoreCase("event")) {
            Intent intent = new Intent(this, NewParentAttendanceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("name", name);
            intent.putExtra("id", id);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 *//* Request code *//*, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.sm_logo)
                    .setContentTitle("SM Academy")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setAutoCancel(true).setContentText(messageBody)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_VIBRATE);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(id *//* ID of notification *//*, notificationBuilder.build());

        } else if (title.equalsIgnoreCase("event")) {
            Intent intent = new Intent(this, AdminLandingActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("name", name);
            intent.putExtra("id", id);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 *//* Request code *//*, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.sm_logo)
                    .setContentTitle("SM Academy")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setAutoCancel(true).setContentText(messageBody)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_VIBRATE);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(id *//* ID of notification *//*, notificationBuilder.build());

        }*/
    }

}
