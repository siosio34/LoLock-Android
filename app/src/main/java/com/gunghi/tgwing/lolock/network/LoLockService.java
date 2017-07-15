package com.gunghi.tgwing.lolock.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by joyeongje on 2017. 7. 15..
 */

public interface LoLockService {

    @POST("/register")
    Call<ResponseBody> registLoLock(
            @Field("registerDeviceId") String registerDeviceId,
            @Field("registerUserName") String registerUserName,
            @Field("registerUserBluetoothId") String userName,
            @Field("registerUserGPS") String registerGps,
            @Part("registerUserImage") String Re
    );
}
