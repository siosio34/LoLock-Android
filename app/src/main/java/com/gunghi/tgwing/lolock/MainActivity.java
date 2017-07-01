package com.gunghi.tgwing.lolock;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

public class MainActivity extends AppCompatActivity  {


    private BottomBar mBottomBar;
    private Fragment currentSelectedFragment;
    private TextView mainTitleTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainTitleTextView = (TextView) findViewById(R.id.mainActivityTitleTextView);

        final FragmentDoorOnOff fragmentDoorOnOff = new FragmentDoorOnOff();
        final FragmentMate fragmentMate = new FragmentMate();


       // mBottomBar = (BottomBar) findViewById(R.id.mainActivityBottomBar);
        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.useFixedMode();
        mBottomBar.setActiveTabColor("#FC7336");
        mBottomBar.setItems(R.menu.bottombar_menus);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {

               switch (menuItemId) {
                   case R.id.menu_home:
                       currentSelectedFragment = fragmentDoorOnOff;
                       mainTitleTextView.setText("출입문 Open/Closed");
                       break;
                   case R.id.menu_family:
                       currentSelectedFragment = fragmentMate;
                       mainTitleTextView.setText("동거인 현황");
                       break;
                   case R.id.menu_info:
                       mainTitleTextView.setText("날씨 및 일정");
                       break;
                   case R.id.menu_alarm:
                       mainTitleTextView.setText("출입기록");
                       break;
               }
                getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFragmentContainer,currentSelectedFragment).commit();
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }

}
