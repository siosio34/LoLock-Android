package com.gunghi.tgwing.lolock.Response;

/**
 * Created by joyeongje on 2017. 7. 21..
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseDaumAddressAPI {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("regionId")
    @Expose
    private String regionId;
    @SerializedName("name0")
    @Expose
    private String name0;
    @SerializedName("code1")
    @Expose
    private String code1;
    @SerializedName("name1")
    @Expose
    private String name1;
    @SerializedName("code2")
    @Expose
    private String code2;
    @SerializedName("name2")
    @Expose
    private String name2;
    @SerializedName("code3")
    @Expose
    private String code3;
    @SerializedName("name3")
    @Expose
    private String name3;
    @SerializedName("x")
    @Expose
    private Double x;
    @SerializedName("y")
    @Expose
    private Double y;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseDaumAddressAPI() {
    }

    /**
     *
     * @param code
     * @param type
     * @param name3
     * @param name
     * @param code2
     * @param code1
     * @param fullName
     * @param code3
     * @param name1
     * @param name2
     * @param y
     * @param x
     * @param regionId
     * @param name0
     */
    public ResponseDaumAddressAPI(String type, String code, String name, String fullName, String regionId, String name0, String code1, String name1, String code2, String name2, String code3, String name3, Double x, Double y) {
        super();
        this.type = type;
        this.code = code;
        this.name = name;
        this.fullName = fullName;
        this.regionId = regionId;
        this.name0 = name0;
        this.code1 = code1;
        this.name1 = name1;
        this.code2 = code2;
        this.name2 = name2;
        this.code3 = code3;
        this.name3 = name3;
        this.x = x;
        this.y = y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getName0() {
        return name0;
    }

    public void setName0(String name0) {
        this.name0 = name0;
    }

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getCode3() {
        return code3;
    }

    public void setCode3(String code3) {
        this.code3 = code3;
    }

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

}