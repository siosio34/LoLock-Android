package com.gunghi.tgwing.lolock.ui;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gunghi.tgwing.lolock.R;
import com.gunghi.tgwing.lolock.bluetooth.BluetoothLeService;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity  {

    private final static String TAG = MainActivity.class.getSimpleName();

    private BottomBar mBottomBar;
    private Fragment currentSelectedFragment;
    private TextView mainTitleTextView;
    private ImageButton settingButton;

    private BluetoothAdapter mBluetoothAdapter;
    private int REQUEST_ENABLE_BT = 1;
    private Handler mHandler;
    private static final long SCAN_PERIOD = 10000;
    private BluetoothLeScanner mLEScanner;
    private ScanSettings settings;
    private List<ScanFilter> filters;
    private BluetoothGatt mGatt;

    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

    private BluetoothGattCharacteristic writeAbleCharacteristic;

    private BluetoothGattCharacteristic writableChar;

    private String mDeviceAddress;
    private boolean mConnected = false;

    FragmentDoorOnOff fragmentDoorOnOff;
    FragmentMate      fragmentMate     ;
    FragmentInfo fragmentInfo;
    FragmentAlarm fragmentAlarm;

    // TODO: 2017-07-02 연결이 되었을때 끊어야함.
    private boolean mScanning;

    private BluetoothLeService mBluetoothLeService;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            Log.d("여기기기","드러러러ㅓ옴");
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                Log.d("mGattUpdateReceiver", "ACTION_GATT_CONNECTED 연결");
                Log.d("Service Size", String.valueOf(mBluetoothLeService.getSupportedGattServices().size()));

            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
              //  mConnected = false;
              //  updateConnectionState(R.string.disconnected);
              //  invalidateOptionsMenu();

            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {

                Log.d("BluetoothLeService","ACTION_GATT_SERVICES_DISCOVERED");
                Log.d("Service Size2", String.valueOf(mBluetoothLeService.getSupportedGattServices().size()));
                if(mBluetoothLeService.getSupportedGattServices() != null) {

                    String uuid = null;
                    String unknownServiceString = "";
                    String unknownCharaString = "";

                    ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
                    ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
                            = new ArrayList<ArrayList<HashMap<String, String>>>();

                    // supported Service안되면
                    // mBluetoothLeService.


                    for(BluetoothGattService gattService : mBluetoothLeService.getSupportedGattServices()) {
                        Log.d("gattService",gattService.toString());

                        for (BluetoothGattCharacteristic characteristic : gattService.getCharacteristics()) {

                            String mUUid = characteristic.getUuid().toString();
                            final int charaProp = characteristic.getProperties();

                            Log.d("mUUId, uuid",mUUid + "," + String.valueOf(charaProp));
                            // TODO: 2017. 7. 15. 특정 아이디에만쓰는것도 해야됨.

                            if (((charaProp & BluetoothGattCharacteristic.PROPERTY_WRITE) |
                                    (charaProp & BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE)) > 0) {

                                writeAbleCharacteristic = characteristic;
                                Log.d("쓰기 가능한 서비스",String.valueOf(writeAbleCharacteristic.getProperties()));
                                String temp = "jonggusibal" + '\0';


                                writeAbleCharacteristic.setValue(temp.getBytes());
                                writeAbleCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
                                mBluetoothLeService.writeCharacterisstic(writeAbleCharacteristic);
                                //mBluetoothLeService.setCharacteristicNotification(writeAbleCharacteristic,true);
                            }


                           // if(mUUid.equals(SampleGattAttributes.HEART_RATE_MEASUREMENT)) {
                           //     Log.d("HeartLateService",gattService.toString());
                           //     Log.d("characteristic",characteristic.toString());
                           //     mNotifyCharacteristic = characteristic;
                           //     mNotifyCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
                           //     mNotifyCharacteristic.setValue("jounggu");
                           //     mBluetoothLeService.writeCharacterisstic(mNotifyCharacteristic);
                           //     // Enable or disable notifications/indications for a given characteristic.
                           //     //characteristic.setValue(bytes);
                           //     //characteristic.setValue("testing");
                           //     //characteristic.setWriteType(BluetoothGattCharacteristic.PERMISSION_WRITE);
                           // }
                        }

//

                       // List<BluetoothGattCharacteristic> gattCharacteristics =
                       //         gattService.getCharacteristics();


                    }

                    //HashMap<String, String> currentServiceData = new HashMap<String, String>();
                    //uuid = gattService.getUuid().toString();
                }





                //
                //writableChar.setValue();
                //mBluetoothLeService.setCharacteristicNotification(writableChar,true);

                // mConnected = true;
                // updateConnectionState(R.string.connected);
                // invalidateOptionsMenu();
            //    // Show all the supported services and characteristics on the user interface.
            //    displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                Log.d("드루와", intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
          //      displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
            }


        }
    };

    // If a given GATT characteristic is selected, check for supported features.  This sample
    // demonstrates 'Read' and 'Notify' features.  See
    // http://d.android.com/reference/android/bluetooth/BluetoothGatt.html for the complete
    // list of supported characteristic features.
    //private final ExpandableListView.OnChildClickListener servicesListClickListner =
    //        new ExpandableListView.OnChildClickListener() {
    //            @Override
    //            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
    //                                        int childPosition, long id) {
    //                if (mGattCharacteristics != null) {
    //                    final BluetoothGattCharacteristic characteristic =
    //                            mGattCharacteristics.get(groupPosition).get(childPosition);
    //                    final int charaProp = characteristic.getProperties();
    //                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
    //                        // If there is an active notification on a characteristic, clear
    //                        // it first so it doesn't update the data field on the user interface.
    //                        if (mNotifyCharacteristic != null) {
    //                            mBluetoothLeService.setCharacteristicNotification(
    //                                    mNotifyCharacteristic, false);
    //                            mNotifyCharacteristic = null;
    //                        }
    //                        mBluetoothLeService.readCharacteristic(characteristic);
    //                    }
    //                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
    //                        mNotifyCharacteristic = characteristic;
    //                        mBluetoothLeService.setCharacteristicNotification(
    //                                characteristic, true);
    //                    }
    //                    return true;
    //                }
    //                return false;
    //            }
    //        };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();
        initView(savedInstanceState);

        checkBLE();

       // mBottomBar = (BottomBar) findViewById(R.id.mainActivityBottomBar);
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        scanBLE();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        // TODO: 2017-07-02  블루투스 자동 연결 하는거 해야됨.

        //if (mBluetoothLeService != null) {x
        //    final boolean result = mBluetoothLeService.connect(mDeviceAddress);
        //    Log.d(TAG, "Connect request result=" + result);
        //}
    }
    private void scanBLE() {
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            if (Build.VERSION.SDK_INT >= 21) {
                mLEScanner = mBluetoothAdapter.getBluetoothLeScanner();
                settings = new ScanSettings.Builder()
                        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                        .build();
                filters = new ArrayList<ScanFilter>();
            }
            scanLeDevice(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this,"블루투스를 사용여부를 체크해주세요.",Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
            scanLeDevice(false);
        }
        unregisterReceiver(mGattUpdateReceiver);
        //scanLeDevice(false);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            mBluetoothAdapter.startLeScan(mLeScanCallback);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);

        } else {
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }

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

    private void checkBLE() {
        mHandler = new Handler();

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE Not Supported",
                    Toast.LENGTH_SHORT).show();
        }
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

        mBluetoothAdapter = bluetoothManager.getAdapter();
        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "블루투스를 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
        }

        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);

    }

    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }


  // @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  // private ScanCallback mScanCallback = new ScanCallback() {

  //     @Override
  //     public void onScanResult(int callbackType, ScanResult result) {
  //         Log.d("mScanCallbackTest","여기들어옴");
  //         Log.i("callbackType", String.valueOf(callbackType));
  //         Log.i("result", result.toString());
  //         BluetoothDevice btDevice = result.getDevice();
  //         if(btDevice.getName()!= null) {
  //             String deviceName = btDevice.getName();
  //             if(deviceName.contains("LoLock")) {
  //                 Log.d("device 자동검색","성공");
  //                 mDeviceAddress = btDevice.getAddress();
  //                 mBluetoothLeService.connect(mDeviceAddress);
  //             }
  //         }



  //     }

  //     @Override
  //     public void onBatchScanResults(List<ScanResult> results) {
  //         for (ScanResult sr : results) {
  //             Log.i("ScanResult - Results", sr.toString());
  //         }
  //     }

  //     @Override
  //     public void onScanFailed(int errorCode) {
  //         Log.e("Scan Failed", "Error Code: " + errorCode);
  //     }
  // };

    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    Log.d("mScanCallbackTest","여기들어옴");
                    Log.i("callbackType", String.valueOf(rssi));
                    Log.i("result", scanRecord.toString());
                    if(device.getName()!= null) {
                        String deviceName = device.getName();
                        Log.d("deviceName",deviceName);
                        if(deviceName.contains("LoLock")) {
                            Log.d("device 자동검색","성공");
                            mDeviceAddress = device.getAddress();
                            mBluetoothLeService.connect(mDeviceAddress);
                        }
                    }
                }
            };

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }
}
