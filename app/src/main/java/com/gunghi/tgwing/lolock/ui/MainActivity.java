package com.gunghi.tgwing.lolock.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gunghi.tgwing.lolock.R;
import com.gunghi.tgwing.lolock.util.ActivityResultEvent;
import com.gunghi.tgwing.lolock.util.EventBus;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.tsengvn.typekit.TypekitContextWrapper;


public class MainActivity extends AppCompatActivity  {

    private final static String TAG = MainActivity.class.getSimpleName();

    private BottomBar mBottomBar;
    private Fragment currentSelectedFragment;
    private TextView mainTitleTextView;
    private ImageButton settingButton;

    FragmentDoorOnOff fragmentDoorOnOff;
    FragmentMate      fragmentMate     ;
    FragmentInfo fragmentInfo;
    FragmentAlarm fragmentAlarm;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


    private void initPushEventReciever() {
        String freamgentFlag = getIntent().getStringExtra("viewFragment");

        Log.d("initPushEventReciever","연결");
        if(freamgentFlag != null) {
            Log.d("initPushEventReciever","들어오니니니");
            Log.d("initPushEventReciever",freamgentFlag + "ddd");

            switch (freamgentFlag) {
                case "weatherPlan":
                    currentSelectedFragment = fragmentInfo;
                    mainTitleTextView.setText("날씨 및 일정");
                    mBottomBar.selectTabAtPosition(2, true);
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.mainActivityFragmentContainer, currentSelectedFragment).
                            commit();
                    Log.d("freamgentFlag","weatherPlan");
                    break;
                case "inOutLog":
                    Log.d("freamgentFlag","inOutLog");
                case "strangeAlarm":
                    Log.d("freamgentFlag","strangeAlarm");
                    currentSelectedFragment = fragmentAlarm;
                    mainTitleTextView.setText("출입기록");
                    mBottomBar.selectTabAtPosition(3, true);
                    getSupportFragmentManager().
                            beginTransaction().
                            replace(R.id.mainActivityFragmentContainer, currentSelectedFragment).
                            commit();
                    break;

            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();
        initView(savedInstanceState);
        //checkBLE();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initPushEventReciever();
      //  registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());

    }
  //private void scanBLE() {
  //    if (!mBluetoothAdapter.isEnabled()) {
  //        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
  //        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
  //    } else {
  //        if (Build.VERSION.SDK_INT >= 21) {
  //            mLEScanner = mBluetoothAdapter.getBluetoothLeScanner();
  //            settings = new ScanSettings.Builder()
  //                    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
  //                    .build();
  //            filters = new ArrayList<ScanFilter>();
  //        }
  //       // scanLeDevice(true);
  //    }
  //}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        super.onActivityResult(requestCode, resultCode, data);
        EventBus.getInstance().post(new ActivityResultEvent(requestCode, resultCode, data));
//
    //    if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
    //        Toast.makeText(this,"블루투스를 사용여부를 체크해주세요.",Toast.LENGTH_SHORT).show();
    //    } else {
    //
    //    }
    }

    @Override
    protected void onPause() {
        super.onPause();
      //  if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
      //      scanLeDevice(false);
      //  }
      //  unregisterReceiver(mGattUpdateReceiver);

        //scanLeDevice(false);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void initView(Bundle savedInstanceState) {
        mainTitleTextView = (TextView) findViewById(R.id.mainActivityTitleTextView);

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
                        mainTitleTextView.setText("출입문 조작");
                        break;
                    case R.id.menu_family:
                        currentSelectedFragment = fragmentMate;
                        mainTitleTextView.setText("가족 현황");
                        break;
                    case R.id.menu_info:
                        currentSelectedFragment = fragmentInfo;
                        mainTitleTextView.setText("날씨 및 일정");
                        break;
                    case R.id.menu_alarm:
                        currentSelectedFragment = fragmentAlarm;
                        mainTitleTextView.setText("출입기록");
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFragmentContainer,currentSelectedFragment).commit();
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });

        //setting 버튼 누르면 들어가도록 바꿈
        settingButton=(ImageButton)findViewById(R.id.mainActivitySettingButton);
        final Intent intent=new Intent(this, SettingActivity.class);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    private void initFragment() {
        fragmentDoorOnOff = new FragmentDoorOnOff();
        fragmentMate      = new FragmentMate();
        fragmentInfo = new FragmentInfo();
        fragmentAlarm = new FragmentAlarm();

        currentSelectedFragment = fragmentDoorOnOff;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }



}
