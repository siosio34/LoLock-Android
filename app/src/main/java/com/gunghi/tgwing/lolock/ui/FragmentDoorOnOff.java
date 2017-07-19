package com.gunghi.tgwing.lolock.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.gunghi.tgwing.lolock.R;
import com.gunghi.tgwing.lolock.network.LoLockService;
import com.gunghi.tgwing.lolock.network.LoLockServiceGenarator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by joyeongje on 2017. 7. 1..
 */

public class FragmentDoorOnOff extends Fragment {

    private boolean openFlag = false;
    ImageButton imageButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_door_on_off, container, false);
        imageButton = (ImageButton) rootView.findViewById(R.id.fragmentDoorOnOffButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openDoor();
            }
        });
        return rootView;
    }

    private void openDoor() {

        LoLockService loLockService = LoLockServiceGenarator.createService(LoLockService.class);
        //Log.d("user token", UserInfo.getInstance().getRegisterUserPhoneId());
        //Call<ResponseBody> requestOpenDoor = loLockService.remoteOnOffLock(UserInfo.getInstance().getRegisterUserPhoneId());
        String temp = "testPhoneId";
        Call<ResponseBody> requestOpenDoor = loLockService.remoteOnOffLock(temp);
        requestOpenDoor.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG,"CALL " + call.toString());
                Log.d(TAG,"Response" + response.toString());

                if(response.isSuccessful()) {
                    Log.d(TAG,"door Response Success");
                    imageButton.setImageResource(R.drawable.ic_door_closed);
                    //3초후에 화면 자동변환
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imageButton.setImageResource(R.drawable.ic_door_open);
                        }
                    },2000);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                imageButton.setImageResource(R.drawable.ic_door_open);
            }

        });

    }

    private void closeDoor() {

    }

}
