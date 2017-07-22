package com.gunghi.tgwing.lolock.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gunghi.tgwing.lolock.R;
import com.gunghi.tgwing.lolock.Response.ResponseWeather;
import com.gunghi.tgwing.lolock.network.LoLockService;
import com.gunghi.tgwing.lolock.network.LoLockServiceGenarator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by joyeongje on 2017. 7. 20..
 */

public class FragmentInfo extends Fragment {

    LoLockService loLockService;

    ImageView weatherIconImageView;
    TextView currentTempatureTextView;
    TextView maxMinTempatureTextView;
    TextView currentLocaleTextView;
    TextView rainPercentTextView;
    TextView cloudAmounTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_info, container, false);

        initFragmentInfo(rootView);
        getWeartherInfo();
        getSchedule();
        return rootView;
    }

    private void initFragmentInfo(ViewGroup viewGroup) {
        loLockService = LoLockServiceGenarator.createService(LoLockService.class);

        weatherIconImageView = (ImageView)viewGroup.findViewById(R.id.fragmentInfoWeatherIcon);
        currentTempatureTextView = (TextView)viewGroup.findViewById(R.id.fragmentInfoCurrentTemparature);
        maxMinTempatureTextView = (TextView)viewGroup.findViewById(R.id.fragmentInfoCurrentMaxMinTemparature);
        currentLocaleTextView = (TextView)viewGroup.findViewById(R.id.fragmentInfoWeatherLocale);
        rainPercentTextView = (TextView)viewGroup.findViewById(R.id.fragmentInfoRainPercent);
        cloudAmounTextView = (TextView)viewGroup.findViewById(R.id.fragmentInfoCloudAmount);
    }

    private void weatherDataMappingUI() {


    }

    private void getWeartherInfo() {
        String loLockKey = "";
        Call<ResponseWeather> responseWeatherCall = loLockService.getWeatherData(loLockKey);
        responseWeatherCall.enqueue(new Callback<ResponseWeather>() {
            @Override
            public void onResponse(Call<ResponseWeather> call, Response<ResponseWeather> response) {
                weatherDataMappingUI();
            }

            @Override
            public void onFailure(Call<ResponseWeather> call, Throwable t) {

            }
        });

    }

    private void getSchedule() {
        // http://solokim.tistory.com/6
        // TODO: 2017. 7. 20. 스케줄 정보 
    }


}
