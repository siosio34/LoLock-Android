package com.gunghi.tgwing.lolock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by joyeongje on 2017. 7. 1..
 */

public class FragmentDoorOnOff extends Fragment {

    private boolean openFlag = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_door_on_off, container, false);
        final ImageButton imageButton = (ImageButton) rootView.findViewById(R.id.fragmentDoorOnOffButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(openFlag) {
                    imageButton.setImageResource(R.drawable.ic_door_closed);
                } else{
                    imageButton.setImageResource(R.drawable.ic_door_open);
                }
                openFlag = !openFlag;

            }
        });
        return rootView;
    }
}
