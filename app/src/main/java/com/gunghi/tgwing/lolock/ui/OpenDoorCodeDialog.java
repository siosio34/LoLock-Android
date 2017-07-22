package com.gunghi.tgwing.lolock.ui;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gunghi.tgwing.lolock.R;
import com.gunghi.tgwing.lolock.Response.ResponseOpenDoorKey;
import com.gunghi.tgwing.lolock.model.UserInfo;
import com.gunghi.tgwing.lolock.network.LoLockService;
import com.gunghi.tgwing.lolock.network.LoLockServiceGenarator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by joyeongje on 2017. 7. 22..
 */

public class OpenDoorCodeDialog extends Dialog {

    private Button shareButton;
    private Button sendButton;
    private TextView textView;
    private Context context;


    public OpenDoorCodeDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.door_open_code_dialog);

        shareButton = (Button)findViewById(R.id.openCodePasteButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager)getContext().getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("label",textView.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getContext(),"일회용키가 복사되었습니다",Toast.LENGTH_SHORT).show();
            }
        });
        sendButton = (Button)findViewById(R.id.openCodeSendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.putExtra("sms_body",textView.getText().toString());
                intent.setType("vnd.android-dir/mms-sms");
                getContext().startActivity(intent);

            }
        });

        textView = (TextView) findViewById(R.id.openCodeTextView);
        makeOpenDoorCode();

    }

    private void makeOpenDoorCode() {

        // TODO: 2017. 7. 22. 이거처리해야됨 텍스트뷰 자동채우는거랑
        LoLockService loLockService = LoLockServiceGenarator.createService(LoLockService.class);
        Call<ResponseOpenDoorKey> requestOpenDoorCode = loLockService.getDoorOpenCode(UserInfo.getInstance().getDevideId());
        requestOpenDoorCode.enqueue(new Callback<ResponseOpenDoorKey>() {

            @Override
            public void onResponse(Call<ResponseOpenDoorKey> call, Response<ResponseOpenDoorKey> response) {
                Log.d("response",response.toString());
                if(response.isSuccessful() && response.body().getCode().equals("CREATED"))  {
                    textView.setText(response.body().getLink());
                }

            }

            @Override
            public void onFailure(Call<ResponseOpenDoorKey> call, Throwable t) {
                Log.d("response Failture",call.toString());
            }
        });

    }







}
