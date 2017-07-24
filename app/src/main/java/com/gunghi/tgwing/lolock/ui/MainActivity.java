package com.gunghi.tgwing.lolock.ui;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gunghi.tgwing.lolock.R;
import com.gunghi.tgwing.lolock.bluetooth.BluetoothLeService;
import com.gunghi.tgwing.lolock.util.ActivityResultEvent;
import com.gunghi.tgwing.lolock.util.EventBus;
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
    private int REQUEST_SCHELUAR = 2;

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


    // 가속도 센서 필요 멤버변수
    private int mShakeCount=0;
    private int delayCount=0;
    private boolean moving = false;
    private static final int WAITING_TIME_FOR_START = 30;
    private static final int NUMBER_OF_GETTING_VALUE = 20;
    private static final float THRESHOLD_GRAVITY_HIGH = 1.25F;
    private static final float THRESHOLD_GRAVITY_LOW = 0.8F;
    private SensorManager mSensorManager = null;
    private SensorEventListener mAccLis;
    private Sensor mAccelometerSensor = null;


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

        //checkMoving();  <-- 작동기재가 필요, 작동시 moving 변수에 true or false 를 잠깐 남겼다가 false로 초기화됨
        //원본 test src URL = https://github.com/Loloara/AndroidStudy/tree/master/AccelometerSensorTest

        String freamgentFlag = getIntent().getStringExtra("viewFragment");
        if(freamgentFlag != null) {
            Log.d("여기에에에","들어오니니니");

            switch (freamgentFlag) {
                case "weatherPlan" :
                    currentSelectedFragment = fragmentInfo;
                    mainTitleTextView.setText("날씨 및 일정");
                    mBottomBar.selectTabAtPosition(2,true);
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.mainActivityFragmentContainer,currentSelectedFragment).
                            commit();
                    break;
                case "inOutLog":
                    currentSelectedFragment = fragmentAlarm;
                    mainTitleTextView.setText("출입기록");
                    mBottomBar.selectTabAtPosition(3,true);
                    getSupportFragmentManager().
                            beginTransaction().
                            replace(R.id.mainActivityFragmentContainer,currentSelectedFragment).
                            commit();
                    break;
                case "checkOutUser":
                case "checkInUser":
                    checkInOrOutUser(freamgentFlag);
                    break;
            }


        }


    }


    @Override
    protected void onResume() {
        super.onResume();
      //  registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());

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
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this,"블루투스를 사용여부를 체크해주세요.",Toast.LENGTH_SHORT).show();
        } else {
            EventBus.getInstance().post(new ActivityResultEvent(requestCode, resultCode, data));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
      //  if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
      //      scanLeDevice(false);
      //  }
      //  unregisterReceiver(mGattUpdateReceiver);
        if(mAccLis != null)
         mSensorManager.unregisterListener(mAccLis);
        //scanLeDevice(false);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
        if(mAccLis != null)
            mSensorManager.unregisterListener(mAccLis);
    }

    private void scanLeDevice(final boolean enable) {

              if (enable) {
                  mHandler.postDelayed(new Runnable() {
                      @Override
                      public void run() {
                          if (Build.VERSION.SDK_INT < 21) {
                              mBluetoothAdapter.stopLeScan(mLeScanCallback);
                          } else {
                              mLEScanner.stopScan(mScanCallback);
                          }
                      }
                  }, SCAN_PERIOD);
                  if (Build.VERSION.SDK_INT < 21) {
                      mBluetoothAdapter.startLeScan(mLeScanCallback);
                  } else {
                      mLEScanner.startScan(filters, settings, mScanCallback);
                  }
              } else {
                  if (Build.VERSION.SDK_INT < 21) {
                      mBluetoothAdapter.stopLeScan(mLeScanCallback);
                  } else {
                      mLEScanner.stopScan(mScanCallback);
                  }
              }
      //   if (enable) {
      //       mHandler.postDelayed(new Runnable() {
      //           @Override
      //           public void run() {
      //               mBluetoothAdapter.stopLeScan(mLeScanCallback);
      //           }
      //       }, SCAN_PERIOD);
      //
      //       mBluetoothAdapter.startLeScan(mLeScanCallback);
      //
      //   } else {
      //       mBluetoothAdapter.stopLeScan(mLeScanCallback);
      //   }

    }

    private void checkInOrOutUser(String flag) {

        scanBLE();
        // 가속도 센서 값 읽어옴.
        // 블루투스 값 읽어옴.



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

    private void checkMoving(){
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccLis = new AccelometerListener();
        mSensorManager.registerListener(mAccLis, mAccelometerSensor, SensorManager.SENSOR_DELAY_UI);
        //WAITING_TIME_FOR_START(30) 만큼 기다렸다가 NUMBER_OF_GETTING_VALUE(20) 만큼 동안 움직임을 측정 후 Listener를 자동 해제한다.
    }

    private class AccelometerListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                delayCount++;
                if(delayCount > WAITING_TIME_FOR_START && delayCount <= WAITING_TIME_FOR_START + NUMBER_OF_GETTING_VALUE) {
                    double accX = event.values[0];
                    double accY = event.values[1];
                    double accZ = event.values[2];

                    double gravityX = accX / SensorManager.GRAVITY_EARTH;
                    double gravityY = accY / SensorManager.GRAVITY_EARTH;
                    double gravityZ = accZ / SensorManager.GRAVITY_EARTH;

                    double f = gravityX * gravityX + gravityY * gravityY + gravityZ * gravityZ;
                    double squaredD = Math.sqrt(f);
                    float gForce = (float) squaredD;

                    if (gForce > THRESHOLD_GRAVITY_HIGH || gForce < THRESHOLD_GRAVITY_LOW) {
                        mShakeCount++;
                        moving = true;
                    }
                    Log.e("LOG", "Count: " + String.format("%d", mShakeCount) + "     gForce: " + String.format("%f", gForce));
                }
                if(delayCount == WAITING_TIME_FOR_START + NUMBER_OF_GETTING_VALUE + 1){
                    if(moving)
                        Log.e("LOG", "This phone is moving");
                    else
                        Log.e("LOG", "This phone is stopped");
                }

                if(delayCount == WAITING_TIME_FOR_START + NUMBER_OF_GETTING_VALUE + 2){
                    //해제하면서 moving을 포함한 변수들 초기화
                    mSensorManager.unregisterListener(mAccLis);
                    moving = false;
                    mShakeCount = 0;
                    delayCount = 0;
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }

     @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
     private ScanCallback mScanCallback = new ScanCallback() {

         @Override
         public void onScanResult(int callbackType, ScanResult result) {
             BluetoothDevice btDevice = result.getDevice();
             if(btDevice.getName()!= null) {
                 String deviceName = btDevice.getName();
                 if(deviceName.contains("LoLock")) {
                     Log.d("device 자동검색","성공");

                     int rssi = result.getRssi();
                     // TODO 종구: 2017. 7. 25.  ble 가 lolock device 가 잡혔을대 동작하는 함수이다
                     // 누가 나갔는지 알기 위해서는 여기서 일정시간동안 움직인 가속도 센서의 총데이터와 움직였는지 여부를 판단하는게 중요하다.!
                     // 따라서 종구는 여기서 변수 boolen checkisMoiving에 움직였는지 안움직였느지 판단하는 변수를 넣고
                     // 몇초간 동안 수집된 가속도 센서 데이터를 저장해줘야된다. 여기에 변수를 만들어 저장해줘야한다...!
                     // checkMoving();

                     // TODO: 2017. 7. 25. 서버로 보낸다...(이건 내가한다!)
                     
                   //  mDeviceAddress = btDevice.getAddress();
                    // mBluetoothLeService.connect(mDeviceAddress);
                 }
             }
         }

         @Override
         public void onBatchScanResults(List<ScanResult> results) {
             for (ScanResult sr : results) {
                 Log.i("ScanResult - Results", sr.toString());
             }
         }

         @Override
         public void onScanFailed(int errorCode) {
             Log.e("Scan Failed", "Error Code: " + errorCode);
         }
     };

    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    //Log.d("mScanCallbackTest","여기들어옴");
                    //Log.i("callbackType", String.valueOf(rssi));
                    //Log.i("result", scanRecord.toString());

                    if(device.getName()!= null) {
                        String deviceName = device.getName();
                        Log.d("deviceName",deviceName);
                        if(deviceName.contains("LoLock")) {
                            Log.d("device 자동검색","성공");

                            // TODO: 2017. 7. 25. 이때 스캔


                            mDeviceAddress = device.getAddress();
                          //  mBluetoothLeService.connect(mDeviceAddress);
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
