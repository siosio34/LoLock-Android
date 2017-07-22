package com.gunghi.tgwing.lolock.network;

import com.gunghi.tgwing.lolock.Response.ResponseLoLockService;
import com.gunghi.tgwing.lolock.Response.ResponseMate;
import com.gunghi.tgwing.lolock.Response.ResponseOpenDoorKey;
import com.gunghi.tgwing.lolock.Response.ResponseUserInfo;
import com.gunghi.tgwing.lolock.Response.ResponseWeather;
import com.gunghi.tgwing.lolock.model.RegisterUserInfo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
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
    @GET("/ThingPlug/checkId/{number}")
    Call<ResponseLoLockService> checkLoraNumberId(
            @Path("number") String number);

    @POST("/ThingPlug/register")
    Call<ResponseBody> registLoLock(
        @Body RegisterUserInfo registerUserInfo);

    // 디바이스 아이디로 기존정보 조회
    @GET("/ThingPlug/userInfo/{phoneId}")
    Call<ResponseUserInfo> getUserInfo(
            @Path("phoneId") String phoneId
    );



    // 원격 문열림
    @FormUrlEncoded
    @PUT("/ThingPlug/remote-open")
    Call<ResponseBody> remoteOnOffLock(
            @Field("openDeviceId") String openDeviceId
    );

    // 문열림 링크 주기
    @GET("/ThingPlug/open-url/{deviceId}")
    Call<ResponseOpenDoorKey> getDoorOpenCode(
            @Path("deviceId") String deviceId);

    // 날씨 정보 가져오기
    @GET("/ThingPlug/weatherdata/{LTID}")
    Call<ResponseWeather> getWeatherData(
            @Path("LTID") String LTID);


    // 동거인 목록 가져오기.
    @GET("/ThingPlug/homemateslist/{LTID}")
    Call<ResponseMate> getHomeMateResponse(
            @Path("LTID") String LTID);

    // 출입기록 알람 가져오기





}
