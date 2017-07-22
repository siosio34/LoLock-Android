package com.gunghi.tgwing.lolock.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joyeongje on 2017. 7. 19..
 */

public class UserInfo {

    private static final UserInfo ourInstance = new UserInfo();
    public static UserInfo getInstance() {
        return ourInstance;
    }


    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lolockLTID")
    @Expose
    private String lolockLTID;

    private String devideId;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserInfo() {

    }

    /**
     *
     * @param name
     * @param lolockLTID
     */
    public UserInfo(String name, String lolockLTID) {
        super();
        this.name = name;
        this.lolockLTID = lolockLTID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLolockLTID() {
        return lolockLTID;
    }

    public void setLolockLTID(String lolockLTID) {
        this.lolockLTID = lolockLTID;
    }

    public String getDevideId() {
        return devideId;
    }

    public void setDevideId(String devideId) {
        this.devideId = devideId;
    }
}