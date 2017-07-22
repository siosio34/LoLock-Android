package com.gunghi.tgwing.lolock.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gunghi.tgwing.lolock.R;
import com.gunghi.tgwing.lolock.Response.ResponseLoLockService;
import com.gunghi.tgwing.lolock.model.RegisterUserInfo;
import com.gunghi.tgwing.lolock.network.LoLockService;
import com.gunghi.tgwing.lolock.network.LoLockServiceGenarator;
import com.tsengvn.typekit.TypekitContextWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText inputIdText;
    TextView checkIdButton;

    LoLockService lockService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputIdText = (EditText) findViewById(R.id.registerEditText);
        checkIdButton = (TextView) findViewById(R.id.checkLoraIdButton);
        checkIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLoraId(inputIdText.getText().toString());
            }
        });
        //f0103d
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    private void checkLoraId(final String number) {
        Log.d("number",number);
        lockService = LoLockServiceGenarator.createService(LoLockService.class);
        //Call<ResponseBody> requestOpenDoor = lockService.remoteOnOffLock(number);
        Call<ResponseLoLockService> checkLoraIdService = lockService.checkLoraNumberId(number);
        checkLoraIdService.enqueue(new Callback<ResponseLoLockService>() {
            @Override
            public void onResponse(Call<ResponseLoLockService> call, Response<ResponseLoLockService> response) {
                Log.d("CheckLoraId API", String.valueOf(response.code()));

                if(response.isSuccessful() || response.body() != null) {
                    if (response.body().getCode().equals("DEVICE_ID_AVAILABLE")) {

                        Toast.makeText(getApplicationContext(),
                                "인증이 완료되었습니다",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(RegisterActivity.this,Register2Activity.class);
                        startActivity(intent);
                        RegisterActivity.this.finish();
                        RegisterUserInfo.getInstance().setRegisterDeviceId(number);

                    } else {
                        Toast.makeText(getApplicationContext(),
                                "잘못된 일련번호가 입력되었습니다. 다시 입력해주세요",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLoLockService> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        "서버에서 응답이 오지 않습니다. 서버를 확인해주세요.",Toast.LENGTH_SHORT).show();
            }
        });
    }



}
