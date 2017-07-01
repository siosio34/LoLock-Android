
package com.gunghi.tgwing.lolock.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mate {

    @SerializedName("mateImageUrl")
    @Expose
    private String mateImageUrl;
    @SerializedName("mateName")
    @Expose
    private String mateName;
    @SerializedName("mateOutingFlag")
    @Expose
    private String mateOutingFlag;
    @SerializedName("mateDoorOpenTime")
    @Expose
    private String mateDoorOpenTime;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Mate() {
    }

    /**
     * 
     * @param mateOutingFlag
     * @param mateDoorOpenTime
     * @param mateImageUrl
     * @param mateName
     */
    public Mate(String mateImageUrl, String mateName, String mateOutingFlag, String mateDoorOpenTime) {
        super();
        this.mateImageUrl = mateImageUrl;
        this.mateName = mateName;
        this.mateOutingFlag = mateOutingFlag;
        this.mateDoorOpenTime = mateDoorOpenTime;
    }

    public String getMateImageUrl() {
        return mateImageUrl;
    }

    public void setMateImageUrl(String mateImageUrl) {
        this.mateImageUrl = mateImageUrl;
    }

    public String getMateName() {
        return mateName;
    }

    public void setMateName(String mateName) {
        this.mateName = mateName;
    }

    public String getMateOutingFlag() {
        return mateOutingFlag;
    }

    public void setMateOutingFlag(String mateOutingFlag) {
        this.mateOutingFlag = mateOutingFlag;
    }

    public String getMateDoorOpenTime() {
        return mateDoorOpenTime;
    }

    public void setMateDoorOpenTime(String mateDoorOpenTime) {
        this.mateDoorOpenTime = mateDoorOpenTime;
    }

}
