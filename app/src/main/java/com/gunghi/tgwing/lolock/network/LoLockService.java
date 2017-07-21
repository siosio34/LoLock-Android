package com.gunghi.tgwing.lolock.network;

import com.gunghi.tgwing.lolock.Response.ResponseLoLockService;
import com.gunghi.tgwing.lolock.Response.ResponseMate;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by joyeongje on 2017. 7. 15..
 */

public interface LoLockService {

    // 로라 일련번호 유효성 체크
    @GET("ThingPlug/checkId/{number}")
    Call<ResponseLoLockService> checkLoraNumberId(
            @Path("number") String number);

    // 로라등록
    @POST("/ThingPlug/register")
    Call<ResponseBody> registLoLock(
            @Field("registerDeviceId") String registerDeviceId,
            @Field("registerUserName") String registerUserName,
            @Field("registerUserPhoneId") String registerUserPhoneId,
            @Field("registerUserBluetoothId") String registerUserBluetoothId,
            @Field("registerUserGPS") String registerUserGPS
            //@Part("registerUserImage") String Re
    );

    // 원격 문열림
    @FormUrlEncoded
    @PUT("/ThingPlug/remote-open")
    Call<ResponseBody> remoteOnOffLock(
            @Field("openDeviceId") String openDeviceId
    );

    // 문열림 링크 주기
    @GET("/ThingPlug/open-url/{deviceId}")
    Call<ResponseBody> getDoorOpenCode(
            @Path("deviceId") String deviceId);

    // 날씨 정보 가져오기
    @GET("/ThingPlug/weatherData/{LTID}")
    Call<ResponseBody> getWeatherData(
            @Path("LTID") String LTID);


    // 동거인 목록 가져오기.
    @GET("/ThingPlug/homemateslist/{LTID}")
    Call<ResponseMate> getHomeMateResponse(
            @Path("LTID") String LTID);


}
