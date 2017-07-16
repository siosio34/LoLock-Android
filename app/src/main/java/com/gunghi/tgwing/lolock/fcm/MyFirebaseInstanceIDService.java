package com.gunghi.tgwing.lolock.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by joyeongje on 2017. 7. 16..
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: 2017. 7. 16. 서버에서 관리할거면 이 톸큰을 서버로 보내야됨!

        sendRegistrationToServer(refreshedToken);

    }
    // [END refresh_token]

    private void sendRegistrationToServer(String token) {

        // TODO: 2017. 7. 16. token을 현재 유저 정보에 넣어줘야됨.

    }
}