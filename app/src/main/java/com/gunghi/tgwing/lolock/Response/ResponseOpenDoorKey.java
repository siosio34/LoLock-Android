package com.gunghi.tgwing.lolock.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joyeongje on 2017. 7. 22..
 */

public class ResponseOpenDoorKey {

    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("code")
    @Expose
    private String code;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseOpenDoorKey() {
    }

    /**
     *
     * @param link
     * @param code
     */
    public ResponseOpenDoorKey(String link, String code) {
        super();
        this.link = link;
        this.code = code;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}