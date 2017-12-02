package com.gunghi.tgwing.lolock.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.gunghi.tgwing.lolock.R;
import com.gunghi.tgwing.lolock.network.LoLockService;
import com.gunghi.tgwing.lolock.ui.SplashActivity;

import java.util.Map;

/**
 * Created by joyeongje on 2017. 7. 16..
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    public static final String PUSH_WEATHER_PLAN = "0";
    public static final String PUSH_IN_OUT_LOG = "1";
    public static final String PUSH_STRANGE_ALARM = "2";
    public static final String PUSH_OUT_CHECK_USER = "3";
    public static final String PUSH_IN_CHECK_USER = "4";

    public static boolean IN_OUT_CODE = false;

    private LoLockService lolockService;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app.

        // Messages containing both notification and data payloads are treated as notification messages.
        //  The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        // 백그라운드 포그라운드 둘다됨.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Map<String, String> getRemoteMessageHash = remoteMessage.getData();
            sendNotification(getRemoteMessageHash);
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
           // sendNotification(remoteMessage.getNotification().get);
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
    private void scheduleJob() {
        // [START dispatch_job]
        //FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        //Job myJob = dispatcher.newJobBuilder()
        //        .setService(MyJobService.class)
        //        .setTag("my-job-tag")
        //        .build();
        //dispatcher.schedule(myJob);
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");

    }


    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */

    private void sendNotification(Map<String,String> messageBody) {


        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Log.d("FirebaseMessage Service", messageBody.toString());

        String title = "";
        String content = messageBody.get("message");
        String pushCode = messageBody.get("pushCode");
        String pushExtra = "inOutLog";


        switch (pushCode) {
            // 액티비티 전환
            case PUSH_WEATHER_PLAN:
                title = "날씨 및 일정";
                pushExtra = "weatherPlan";
                break;
            case PUSH_IN_OUT_LOG:
                title = "출입 로그";
                pushExtra = "inOutLog";
                break;
            case PUSH_STRANGE_ALARM:
                title = "위험 감지";
                pushExtra = "strangeAlarm";
                break;
            // 걍 인터넷 통신만 해야됨...
            case PUSH_OUT_CHECK_USER:
                IN_OUT_CODE = true;
                break;
            case PUSH_IN_CHECK_USER:
                IN_OUT_CODE = false;
                break;
        }

        if (pushCode.equals(PUSH_OUT_CHECK_USER) || pushCode.equals(PUSH_IN_CHECK_USER)) {
            Intent bleIntent = new Intent(this, BluetoothLeService.class);
            startService(bleIntent);
            Log.d("start ble scan servcie", "시작");
        } else {
            Intent intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("viewFragment", pushExtra);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher_lolock)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }
    }
}
