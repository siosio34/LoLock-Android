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
import android.widget.EditText;
import android.widget.Toast;

import com.gunghi.tgwing.lolock.R;
import com.gunghi.tgwing.lolock.Response.ResponseDaumAddressAPI;
import com.gunghi.tgwing.lolock.network.DaumService;
import com.gunghi.tgwing.lolock.network.DaumServiceGenerator;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register2Activity extends AppCompatActivity {

    EditText nameEditText;
    EditText addressEditText;
    private LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        nameEditText = (EditText) findViewById(R.id.editTextName);
        addressEditText = (EditText) findViewById(R.id.editTextAddress);

        // TODO: 2017. 7. 21. GPS 를 켜야되나 ...?

    }

    private LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // Called when a new location is found by the network location provider.
            // TODO: 2017. 7. 21. 경도위도정보사용하긔
            getAddressWithMyCoord(location.getLatitude(),location.getLongitude())
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
                // TODO: 2017. 7. 21. 위치정보활용해서 주소정보 들고오기.(위치정보가있음.)
            }

        } else {
            Toast.makeText(getApplicationContext(),"네트워크 연결을 확인해주세요",Toast.LENGTH_SHORT).show();
            onNetworkSetting();
            //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, locationListener);
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

            //show dialog to allow user to enable location settings
            AlertDialog.Builder dialog = new AlertDialog.Builder(getApplicationContext());
            dialog.setTitle(" NETWORK 설정 ");
            dialog.setMessage(" NETWORK를 활성화 시켜주세요.");

            dialog.setPositiveButton("설정", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    startActivityForResult(new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS), 0);
                    // startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
                }
            });

            dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    //nothing to do
                }
            });
            dialog.show();
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
