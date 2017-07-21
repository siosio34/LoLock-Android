package com.gunghi.tgwing.lolock.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gunghi.tgwing.lolock.R;

/**
 * Created by joyeongje on 2017. 7. 20..
 */

public class FragmentAlarm extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_door_on_off, container, false);
        return rootView;
    }



}
