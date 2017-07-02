package com.gunghi.tgwing.lolock.util;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by young on 2017-07-02.
 */

public class TypekitSetting extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "NanumBarunpenR.ttf"))
                .addBold(Typekit.createFromAsset(this, "NanumBarunpenB.ttf"));

    }
}
