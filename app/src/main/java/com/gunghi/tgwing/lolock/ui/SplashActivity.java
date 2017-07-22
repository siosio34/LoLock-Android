package com.gunghi.tgwing.lolock.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.gunghi.tgwing.lolock.R;
import com.gunghi.tgwing.lolock.Response.ResponseUserInfo;
import com.gunghi.tgwing.lolock.model.RegisterUserInfo;
import com.gunghi.tgwing.lolock.model.UserInfo;
import com.gunghi.tgwing.lolock.network.LoLockService;
import com.gunghi.tgwing.lolock.network.LoLockServiceGenarator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#FAAC42"));

        // 저장된 데이터 불러온다.!
        SharedPreferences sharedPreferences = getSharedPreferences("lolockLocalData", MODE_PRIVATE);
        final String deviceId = sharedPreferences.getString("deviceId", "");
        Log.d("deviceId",deviceId);

        if (deviceId.equals("")) {
            Intent registerIntent = new Intent(SplashActivity.this, RegisterActivity.class);
            SplashActivity.this.startActivity(registerIntent);
            SplashActivity.this.finish();
        } else {
            getUserInfo(deviceId);
        }

    }

    private void getUserInfo(final String deviceId) {
        RegisterUserInfo.getInstance().setRegisterUserPhoneId(deviceId);
        LoLockService lolockservice = LoLockServiceGenarator.createService(LoLockService.class);
        Call<ResponseUserInfo> responseUserInfo = lolockservice.getUserInfo(deviceId);
        responseUserInfo.enqueue(new Callback<ResponseUserInfo>() {
            @Override
            public void onResponse(Call<ResponseUserInfo> call, Response<ResponseUserInfo> response) {

                if (response.isSuccessful()) {
                    if (response.body().getCode().equals("REGISTRED")) {
                        UserInfo tempUserInfo = response.body().getUserInfo();
                        UserInfo.getInstance().setName(tempUserInfo.getName());
                        UserInfo.getInstance().setLolockLTID(tempUserInfo.getLolockLTID());
                        UserInfo.getInstance().setDevideId(deviceId);

                        Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                        SplashActivity.this.startActivity(mainIntent);
                        SplashActivity.this.finish();
                    } else {
                        Intent registerIntent = new Intent(SplashActivity.this, RegisterActivity.class);
                        SplashActivity.this.startActivity(registerIntent);
                        SplashActivity.this.finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseUserInfo> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"서버가 응답하지 않습니다.",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

}
