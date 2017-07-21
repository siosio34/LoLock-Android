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
import com.gunghi.tgwing.lolock.network.LoLockService;
import com.gunghi.tgwing.lolock.network.LoLockServiceGenarator;

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

    private void getWeartherInfo() {

        // TODO: 2017. 7. 20. 날씨정보


    }

    private void getSchedule() {
        // http://solokim.tistory.com/6
        // TODO: 2017. 7. 20. 스케줄 정보 
    }


}
