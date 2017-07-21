package com.gunghi.tgwing.lolock.model;

/**
 * Created by joyeongje on 2017. 7. 21..
 */

public class RegisterUserInfo {

    private String registerUserName;
    private String registerDeviceAddr;
    private String registerDeviceGPS_lat;
    private String registerDeviceGPS_lon;
    private String registerDeviceId;
    private String registerUserPhoneId;

    private static final RegisterUserInfo ourInstance = new RegisterUserInfo();

    public static RegisterUserInfo getInstance() {
        return ourInstance;
    }

    private RegisterUserInfo() {
    }


    public String getRegisterUserName() {
        return registerUserName;
    }

    public void setRegisterUserName(String registerUserName) {
        this.registerUserName = registerUserName;
    }

    public String getRegisterDeviceAddr() {
        return registerDeviceAddr;
    }

    public void setRegisterDeviceAddr(String registerDeviceAddr) {
        this.registerDeviceAddr = registerDeviceAddr;
    }

    public String getRegisterDeviceGPS_lat() {
        return registerDeviceGPS_lat;
    }

    public void setRegisterDeviceGPS_lat(String registerDeviceGPS_lat) {
        this.registerDeviceGPS_lat = registerDeviceGPS_lat;
    }

    public String getRegisterDeviceGPS_lon() {
        return registerDeviceGPS_lon;
    }

    public void setRegisterDeviceGPS_lon(String registerDeviceGPS_lon) {
        this.registerDeviceGPS_lon = registerDeviceGPS_lon;
    }

    public String getRegisterDeviceId() {
        return registerDeviceId;
    }

    public void setRegisterDeviceId(String registerDeviceId) {
        this.registerDeviceId = registerDeviceId;
    }

    public String getRegisterUserPhoneId() {
        return registerUserPhoneId;
    }

    public void setRegisterUserPhoneId(String registerUserPhoneId) {
        this.registerUserPhoneId = registerUserPhoneId;
    }
}
