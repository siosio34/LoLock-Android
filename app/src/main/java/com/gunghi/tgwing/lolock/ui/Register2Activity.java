package com.gunghi.tgwing.lolock.ui;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gunghi.tgwing.lolock.R;
import com.gunghi.tgwing.lolock.Response.ResponseDaumAddressAPI;
import com.gunghi.tgwing.lolock.model.RegisterUserInfo;
import com.gunghi.tgwing.lolock.network.DaumService;
import com.gunghi.tgwing.lolock.network.DaumServiceGenerator;
import com.gunghi.tgwing.lolock.network.LoLockService;
import com.gunghi.tgwing.lolock.network.LoLockServiceGenarator;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register2Activity extends AppCompatActivity {

    EditText nameEditText;
    EditText addressEditText;
    private LocationManager locationManager;
    Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        nameEditText = (EditText) findViewById(R.id.editTextName);
        addressEditText = (EditText) findViewById(R.id.editTextAddress);

        button = (Button) findViewById(R.id.register2ActivityButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String name = nameEditText.getText().toString();
                    RegisterUserInfo.getInstance().setRegisterUserName(name);

                    String address = addressEditText.getText().toString();
                    RegisterUserInfo.getInstance().setRegisterDeviceAddr(address);

                    //final String[] deviceId = {RegisterUserInfo.getInstance().getDeviceId()};

                LoLockService loLockService = LoLockServiceGenarator.createService(LoLockService.class);
                Call<ResponseBody> callLoLockService = loLockService.registLoLock(RegisterUserInfo.getInstance());
                Log.d("callLoLockService",callLoLockService.toString());
                callLoLockService.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d("response" ,response.toString());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("response" ,call.toString());
                    }
                });

                }
        });

        getLocationInfo();
    }

    private LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            getAddressWithMyCoord(location.getLatitude(),location.getLongitude());
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {}

        public void onProviderEnabled(String provider) {}

        public void onProviderDisabled(String provider) {}
    };



    private void getLocationInfo() {

        locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Dexter.withActivity(this)
                    .withPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    .withListener(new PermissionListener() {
                        @Override public void onPermissionGranted(PermissionGrantedResponse response) {/* ... */}
                        @Override public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */}

                        @Override
                        public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {

                        }
                    }).check();
        }

        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(isNetworkEnabled) {
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(lastKnownLocation == null) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListener);
            } else {
                Log.d("나의 위치",String.valueOf(lastKnownLocation.getLatitude() +"," + lastKnownLocation.getLongitude()));
                getAddressWithMyCoord(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());

            }
        } else {
            onNetworkSetting();
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListener);
            //// 아니면 네트워크 사용여부를 토스트로 띄우던지 해야겠다..
            //// TODO: 2017. 7. 21. 로직처리.

        }
    }

    private void getAddressWithMyCoord(Double lat,Double lon) {

        Map<String, String> queryAddress = new HashMap<>();
        queryAddress.put("apikey","04b68611d624a48f5a37bf1ad4324600");
        queryAddress.put("latitude",String.valueOf(lat));
        queryAddress.put("longitude", String.valueOf(lon));
        queryAddress.put("inputCoordSystem","WGS84");
        queryAddress.put("output","json");

        RegisterUserInfo.getInstance().setRegisterDeviceGPS_lat(String.valueOf(lat));
        RegisterUserInfo.getInstance().setRegisterDeviceGPS_lon(String.valueOf(lon));


        DaumService daumService = DaumServiceGenerator.createService(DaumService.class);
        Call<ResponseDaumAddressAPI> responseDaumAddressAPICall = daumService.changeCoordToAddress(queryAddress);
        responseDaumAddressAPICall.enqueue(new Callback<ResponseDaumAddressAPI>() {
            @Override
            public void onResponse(Call<ResponseDaumAddressAPI> call, Response<ResponseDaumAddressAPI> response) {
                if(response.isSuccessful()) {
                    addressEditText.setText(response.body().getFullName());
                    locationManager.removeUpdates(locationListener);
                }
            }

            @Override
            public void onFailure(Call<ResponseDaumAddressAPI> call, Throwable t) {

            }
        });

    }

    private void onNetworkSetting(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("GPS 사용유무셋팅");
        alertDialog.setMessage("GPS 셋팅이 되지 않았을수도 있습니다.\n설정창으로 가시겠습니까?");
                // OK 를 누르게 되면 설정창으로 이동합니다.
                alertDialog.setPositiveButton("Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int which) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            }
                        });
        // Cancle 하면 종료 합니다.
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("승락 결과", "여기들어옴");
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

}
