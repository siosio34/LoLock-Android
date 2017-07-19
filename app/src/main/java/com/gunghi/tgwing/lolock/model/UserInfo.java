package com.gunghi.tgwing.lolock.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by joyeongje on 2017. 7. 19..
 */

public class UserInfo extends RealmObject {
    private static final UserInfo ourInstance = new UserInfo();

    public static UserInfo getInstance() {
        return ourInstance;
    }

    @PrimaryKey
    private String registerUserPhoneId;

    private String registerLoraId;
    private String registerUserName;
    private String registerBluetoothId;
    private String lat;
    private String lon;

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

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

}
