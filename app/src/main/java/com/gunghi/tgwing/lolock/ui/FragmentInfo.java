package com.gunghi.tgwing.lolock.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gunghi.tgwing.lolock.R;
import com.gunghi.tgwing.lolock.network.LoLockService;
import com.gunghi.tgwing.lolock.network.LoLockServiceGenarator;

/**
 * Created by joyeongje on 2017. 7. 20..
 */

public class FragmentInfo extends Fragment {

    LoLockService loLockService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_info, container, false);
        loLockService = LoLockServiceGenarator.createService(LoLockService.class);

        getWeartherInfo();
        getSchedule();
        return rootView;
    }

    private void getWeartherInfo() {

        // TODO: 2017. 7. 20. 날씨정보
    }

    private void getSchedule() {
        // TODO: 2017. 7. 20. 스케줄 정보 
    }


}
