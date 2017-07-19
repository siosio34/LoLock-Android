package com.gunghi.tgwing.lolock.network;

import com.gunghi.tgwing.lolock.Response.ResponseMate;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by joyeongje on 2017. 7. 15..
 */

public interface LoLockService {

    @POST("/ThingPlug/register")
    Call<ResponseBody> registLoLock(
            @Field("registerDeviceId") String registerDeviceId,
            @Field("registerUserName") String registerUserName,
            @Field("registerUserPhoneId") String registerUserPhoneId,
            @Field("registerUserBluetoothId") String registerUserBluetoothId,
            @Field("registerUserGPS") String registerUserGPS
            //@Part("registerUserImage") String Re
    );

    @FormUrlEncoded
    @PUT("/ThingPlug/remote-open")
    Call<ResponseBody> remoteOnOffLock(
            @Field("openDeviceId") String openDeviceId
    );

    @POST("/ThingPlug/homemateslist/{LTID}")
    Call<ResponseMate> getHomeMateResponse(
            @Path("LTID") String LTID);







}
