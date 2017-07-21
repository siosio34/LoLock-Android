package com.gunghi.tgwing.lolock.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by joyeongje on 2017. 7. 19..
 */

public class UserInfo extends RealmObject {
    private static UserInfo ourInstance = new UserInfo();

    public static UserInfo getInstance() {
        return ourInstance;
    }
    public static void setOurInstance(UserInfo userInfo) {
        ourInstance = userInfo;
    }


    @PrimaryKey
    private String registerUserPhoneId;

    private String registerLoraId;
    private String registerUserName;
    private String registerBluetoothId;
    private Double lat;
    private Double lon;


    public UserInfo() {

    }

    public String getRegisterLoraId() {
        return registerLoraId;
    }

    public void setRegisterLoraId(String registerLoraId) {
        this.registerLoraId = registerLoraId;
    }

    public String getRegisterUserName() {
        return registerUserName;
    }

    public void setRegisterUserName(String registerUserName) {
        this.registerUserName = registerUserName;
    }

    public String getRegisterUserPhoneId() {
        return registerUserPhoneId;
    }

    public void setRegisterUserPhoneId(String registerUserPhoneId) {
        this.registerUserPhoneId = registerUserPhoneId;
    }

    public String getRegisterBluetoothId() {
        return registerBluetoothId;
    }

    public void setRegisterBluetoothId(String registerBluetoothId) {
        this.registerBluetoothId = registerBluetoothId;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

}
