package com.gunghi.tgwing.lolock.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by joyeongje on 2017. 7. 21..
 */

public class DaumServiceGenerator {
    private static final String DAUM_API_BASE_URL = "https://apis.daum.net/";
    //private static final String DAUM_API_KEY = "04b68611d624a48f5a37bf1ad4324600/";


    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(DAUM_API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();


    public static <S> S createService(
            Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
