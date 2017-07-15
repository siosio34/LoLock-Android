
package com.gunghi.tgwing.lolock.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gunghi.tgwing.lolock.model.Mate;

import java.util.List;

public class ResponseMate {

    @SerializedName("mates")
    @Expose
    private List<Mate> mates = null;
    @SerializedName("mateNumber")
    @Expose
    private Integer mateNumber;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ResponseMate() {
    }

    /**
     * 
     * @param mateNumber
     * @param mates
     */
    public ResponseMate(List<Mate> mates, Integer mateNumber) {
        super();
        this.mates = mates;
        this.mateNumber = mateNumber;
    }

    public List<Mate> getMates() {
        return mates;
    }

    public void setMates(List<Mate> mates) {
        this.mates = mates;
    }

    public Integer getMateNumber() {
        return mateNumber;
    }

    public void setMateNumber(Integer mateNumber) {
        this.mateNumber = mateNumber;
    }

}
