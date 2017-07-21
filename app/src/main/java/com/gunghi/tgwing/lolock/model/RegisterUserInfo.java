package com.gunghi.tgwing.lolock.model;

/**
 * Created by joyeongje on 2017. 7. 21..
 */

public class RegisterUserInfo {

    private String name;
    private String address;
    private String lat;
    private String lon;
    private String loraId;
    private String deviceId;

    private static final RegisterUserInfo ourInstance = new RegisterUserInfo();

    public static RegisterUserInfo getInstance() {
        return ourInstance;
    }

    private RegisterUserInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getLoraId() {
        return loraId;
    }

    public void setLoraId(String loraId) {
        this.loraId = loraId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
