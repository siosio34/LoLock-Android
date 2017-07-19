package com.gunghi.tgwing.lolock.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.gunghi.tgwing.lolock.model.UserInfo;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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
        final String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "Refreshed token: " + refreshedToken);
        saveToken(refreshedToken);
    }
    // [END refresh_token]

    private void saveToken(final String token) {
        Realm mRealm;
        Realm.init(getApplicationContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        mRealm = Realm.getInstance(realmConfiguration);
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserInfo.getInstance().setRegisterUserPhoneId(token);
                realm.copyToRealmOrUpdate(UserInfo.getInstance());
            }
        });
        mRealm.close();
    }
}