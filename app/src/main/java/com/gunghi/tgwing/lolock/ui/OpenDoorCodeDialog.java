package com.gunghi.tgwing.lolock.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.gunghi.tgwing.lolock.R;
import com.gunghi.tgwing.lolock.network.LoLockService;
import com.gunghi.tgwing.lolock.network.LoLockServiceGenarator;

/**
 * Created by joyeongje on 2017. 7. 22..
 */

public class OpenDoorCodeDialog extends Dialog {

    private Button shareButton;
    private Button sendButton;
    private TextView textView;


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

            }
        });
        sendButton = (Button)findViewById(R.id.openCodeSendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        textView = (TextView) findViewById(R.id.openCodeTextView);

    }

    private void makeOpenDoorCode() {

        // TODO: 2017. 7. 22. 이거처리해야됨 텍스트뷰 자동채우는거랑
        LoLockService loLockService = LoLockServiceGenarator.createService(LoLockService.class);
       // Call<ResponseBody> requestOpenDoorCode = loLockService.getDoorOpenCode(temp);
       // requestOpenDoorCode.enqueue(new Callback<ResponseBody>() {
       //     @Override
       //     public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
       //         if(response.isSuccessful()) {
       //             // TODO: 2017. 7. 20. 텍스트뷰처리
//
       //         }
       //     }
//
       //     @Override
       //     public void onFailure(Call<ResponseBody> call, Throwable t) {
//
       //     }
       // });

    }







}
