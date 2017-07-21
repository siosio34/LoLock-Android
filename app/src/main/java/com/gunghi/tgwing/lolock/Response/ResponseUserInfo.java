package com.gunghi.tgwing.lolock.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gunghi.tgwing.lolock.model.UserInfo;

/**
 * Created by joyeongje on 2017. 7. 21..
 */

public class ResponseUserInfo {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("userInfo")
    @Expose
    private UserInfo userInfo;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseUserInfo() {

    }

    /**
     *
     * @param message
     * @param code
     * @param userInfo
     */
    public ResponseUserInfo(String code, UserInfo userInfo, String message) {
        super();
        this.code = code;
        this.userInfo = userInfo;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}