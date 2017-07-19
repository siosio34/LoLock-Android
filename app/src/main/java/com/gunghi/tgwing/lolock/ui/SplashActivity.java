package com.gunghi.tgwing.lolock.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.Window;
import android.view.WindowManager;

import com.gunghi.tgwing.lolock.R;

/**
 * Created by InKyung on 2017-07-19.
 */

public class SplashActivity extends Activity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        //상단바 색깔 바꾸기
        Window window=getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#FAAC42"));

        //3초후에 화면 자동변환
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainintent=new Intent(SplashActivity.this,MainActivity.class);
                SplashActivity.this.startActivity(mainintent);
                SplashActivity.this.finish();
            }
        },2000);


    }


    @Override
    protected void onStop() {
        super.onStop();

    }

}
