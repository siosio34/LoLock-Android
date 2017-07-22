package com.gunghi.tgwing.lolock.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.gunghi.tgwing.lolock.R;
import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by InKyung on 2017-07-19.
 */

public class SettingActivity extends Activity {
    ImageButton backButton;
    Button lolockInfoButton,changeInfoButton,changeGps;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        backButton=(ImageButton)findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //3가지 버튼
        changeInfoButton=(Button)findViewById(R.id.ChangeInfoButton);
        lolockInfoButton=(Button)findViewById(R.id.LolockInfoButton);
        changeGps=(Button)findViewById(R.id.ChangeGps);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

}
