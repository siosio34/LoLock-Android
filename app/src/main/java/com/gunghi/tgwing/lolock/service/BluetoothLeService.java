package com.gunghi.tgwing.lolock.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.gunghi.tgwing.lolock.model.UserInfo;
import com.gunghi.tgwing.lolock.network.LoLockService;
import com.gunghi.tgwing.lolock.network.LoLockServiceGenarator;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by joyeongje on 2017. 7. 1..
 */


/**
 * Service for managing connection and data communication with a GATT server hosted on a
 * given Bluetooth LE device.
 */
public class BluetoothLeService extends Service {
    private final static String TAG = BluetoothLeService.class.getSimpleName();

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;

    private Handler mHandler;


    private static final int WAITING_TIME_FOR_START = 30;
    private static final int NUMBER_OF_GETTING_VALUE = 20;
    private static final float THRESHOLD_GRAVITY_HIGH = 1.25F;
    private static final float THRESHOLD_GRAVITY_LOW = 0.8F;

    // 가속도 센서 필요 멤버변수
    private int mShakeCount=0;
    private int delayCount=0;

    private SensorManager mSensorManager = null;
    private SensorEventListener mAccLis;
    private Sensor mAccelometerSensor = null;

    private float[] initAccelData = new float[25];

    private boolean isMoving = false;
    private float[] accelData = new float[20];

    private boolean bleCheck = false;
    private int     rssiBle = 0;

    private LoLockService loLockService;

    private static final long SCAN_PERIOD = 10000;
    private BluetoothLeScanner mLEScanner;
    private ScanSettings settings;
    private List<ScanFilter> filters;
    private BluetoothGatt mGatt;

    @Override
    public void onCreate() {
        Log.d("BluetoothLeService","onCreate");
        loLockService = LoLockServiceGenarator.createService(LoLockService.class);
        mHandler = new Handler();

        if(!initialize()) {
            this.stopSelf();
            Toast.makeText(getApplicationContext(),"현재 블루투스 기능을 사용할수 없습니다",Toast.LENGTH_SHORT).show();
        } else {
            if (Build.VERSION.SDK_INT >= 21) {
                mLEScanner = mBluetoothAdapter.getBluetoothLeScanner();
                settings = new ScanSettings.Builder()
                        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                        .build();
                filters = new ArrayList<ScanFilter>();
            }

            startLoraScan(true);
        }
      //  Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
      //  getApplicationContext().startActivity(enableBtIntent);

    }

    @Override
    public void onDestroy() {
        Log.d("BluetoothLeService","onDestroy");
        close();
        startLoraScan(false);
        if(mAccLis != null)
            mSensorManager.unregisterListener(mAccLis);
        // The service is no longer used and is being destroyed
    }

    private void startLoraScan(boolean enable) {


        if (enable) {
          //   mHandler.postDelayed(new Runnable() {
          //       @Override
          //       public void run() {
          //           if (Build.VERSION.SDK_INT < 21) {
          //               mBluetoothAdapter.stopLeScan(mLeScanCallback);
          //           } else {
          //               mLEScanner.stopScan(mScanCallback);
          //             //  mLEScanner.startScan(mScanCallback);
          //           }
          //       }
          //   }, SCAN_PERIOD);
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

    }

    protected static double calculateDistance(int txPower, double rssi) {
        if (rssi == 0) {
            return -1.0; // if we cannot determine distance, return -1.
        }

        double ratio = rssi*1.0/txPower;
        if (ratio < 1.0) {
            return Math.pow(ratio,10);
        }
        else {
            double accuracy =  (0.89976)*Math.pow(ratio,7.7095) + 0.111;
            return accuracy;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private ScanCallback mScanCallback = new ScanCallback() {

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BluetoothDevice btDevice = result.getDevice();
            if(btDevice.getName()!= null) {
                String deviceName = btDevice.getName();
                if(deviceName.contains("LoLock")) {

                  //  Log.d("SearchLoLock",  calculateDistance(-59,result.getRssi()) + "");
              //      checkMoving();
              //      mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
              //      mAccelometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
              //      mAccLis = new AccelometerListener();
              //      mSensorManager.registerListener(mAccLis, mAccelometerSensor, SensorManager.SENSOR_DELAY_UI);


                   // Toast.makeText(getApplicationContext(),"추정거리 " +
                   //         calculateDistance(-59,result.getRssi()),Toast.LENGTH_SHORT).show();

                    // TODO: 2017. 7. 30. 체크무빙
                   // if(calculateDistance(4,result.getRssi()) < 1.5) {
                   //     Toast.makeText(getApplicationContext(),"자동 문 열림",Toast.LENGTH_SHORT).show();
                   //     if(UserInfo.getInstance().getDevideId() != null) {
                   //         loLockService.remoteOnOffLock(UserInfo.getInstance().getDevideId()).enqueue(new Callback<ResponseBody>() {
                   //             @Override
                   //             public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                   //                 Toast.makeText(getApplicationContext(),"자동 문 열림 성공",Toast.LENGTH_SHORT).show();
                   //         }
//
                   //             @Override
                   //             public void onFailure(Call<ResponseBody> call, Throwable t) {
//
                   //             }
                   //         });
                   //     }
                   // }


                   // int rssi = result.getRssi();
                   // checkMoving();
                    //WAITING_TIME_FOR_START(30) 이후 NUMBER_OF_GETTING_VALUE(20)개 데이터 수집
                    //bolean isMoving과 float accelData[0~19]에 저장

                    // TODO: 2017. 7. 25. 서버로 보낸다...(이건 내가한다!)
                    //서버로 보낸후 isMoving=false, accelData[0~19]=0 초기화 필요

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
                    Log.d("mScanCallbackTest","여기들어옴");

                    if(device.getName()!= null) {
                        String deviceName = device.getName();
                        Log.d("deviceName",deviceName);
                        if(deviceName.contains("LoLock")) {
                            rssiBle = rssi;
                            bleCheck = true;
                            //Toast.makeText(getApplicationContext(), "rssi신호세기" + rssiBle, Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("BluetoothLeService","onBind");
        return null;
    }


    public boolean initialize() {
        // For API level 18 and above, get a reference to BluetoothAdapter through
        // BluetoothManager.

        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }

        }
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
            Toast.makeText(this, "블루투스를 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }

        mBluetoothAdapter.enable();
        return true;
    }
    public void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt = null;
    }

    private void checkMoving(){


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

                    accX = accX / SensorManager.GRAVITY_EARTH;
                    accY = accY / SensorManager.GRAVITY_EARTH;
                    accZ = accZ / SensorManager.GRAVITY_EARTH;

                    double squaredD = accX * accX + accY * accY + accZ * accZ;
                    squaredD = Math.sqrt(squaredD);
                    float gForce = (float) squaredD;
                    accelData[delayCount-WAITING_TIME_FOR_START-1] = gForce;

                    if (gForce > THRESHOLD_GRAVITY_HIGH || gForce < THRESHOLD_GRAVITY_LOW) {
                        mShakeCount++;
                        isMoving = true;
                    }
                    Log.e("LOG", "Count: " + String.format("%d", mShakeCount) + "     gForce: " + String.format("%f", gForce));
                }
                if(delayCount == WAITING_TIME_FOR_START + NUMBER_OF_GETTING_VALUE + 1){
                    if(isMoving)
                        Log.e("LOG", "This phone is moving");
                    else
                        Log.e("LOG", "This phone is stopped");
                }

                if(delayCount == WAITING_TIME_FOR_START + NUMBER_OF_GETTING_VALUE + 2){
                    //해제하면서 변수들 초기화
                    mShakeCount = 0;
                    delayCount = 0;

                    if(isMoving) {
                        sendSensorDataToServer();
                    }

                    else {
                        initializeSensorValue();
                        if(mAccLis != null)
                            mSensorManager.unregisterListener(mAccLis);
                    }


                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    private void initializeSensorValue() {

        isMoving = false;
        bleCheck = false;
        rssiBle = 0;
    }


    private void sendSensorDataToServer() {

        Call<ResponseBody> responseBodyCall =
                MyFirebaseMessagingService.IN_OUT_CODE ?
                        loLockService.checkOutURL(UserInfo.getInstance().getDevideId())
                        :loLockService.checkInURL(UserInfo.getInstance().getDevideId());

       // Toast.makeText(getApplicationContext(),"사용자 데이터 전송",Toast.LENGTH_SHORT).show();

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                initializeSensorValue();
                if(mAccLis != null)
                    mSensorManager.unregisterListener(mAccLis);

                startLoraScan(false);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                initializeSensorValue();
            }
        });


        // TODO: 2017. 7. 28. 서버로 저장 서버로 다 처리후 스탑 서비스로 종료
    }


}
