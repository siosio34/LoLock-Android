package com.gunghi.tgwing.lolock.network;

import com.gunghi.tgwing.lolock.Response.ResponseDaumAddressAPI;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by joyeongje on 2017. 7. 21..
 */

public interface DaumService {

    String daumAPIKey = "04b68611d624a48f5a37bf1ad4324600";
    String inputCoordSystem="WGS84";
    String outputType= "json";

    @GET("/local/geo/coord2addr")
    Call<ResponseDaumAddressAPI> changeCoordToAddress(
            @QueryMap Map<String, String> queryParams
        );


}
