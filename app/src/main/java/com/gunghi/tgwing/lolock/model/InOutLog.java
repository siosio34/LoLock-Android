package com.gunghi.tgwing.lolock.model;

import com.gunghi.tgwing.lolock.Response.ResponseInOutLog;

/**
 * Created by joyeongje on 2017. 7. 22..
 */

public class InOutLog {

    private String name;
    private int outingFlag;
    private ResponseInOutLog.OutTime inOutDate;
    private int strangeFlag;


    public InOutLog() {

    }

    public InOutLog(String name, int outingFlag, ResponseInOutLog.OutTime inOutDate, int strangeFlag) {
        this.name = name;
        this.outingFlag = outingFlag;
        this.inOutDate = inOutDate;
        this.strangeFlag = strangeFlag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStrangeFlag() {
        return strangeFlag;
    }

    public void setStrangeFlag(int strangeFlag) {
        this.strangeFlag = strangeFlag;
    }

    public ResponseInOutLog.OutTime getInOutDate() {
        return inOutDate;
    }

    public void setInOutDate(ResponseInOutLog.OutTime inOutDate) {
        this.inOutDate = inOutDate;
    }

    public int getOutingFlag() {
        return outingFlag;
    }

    public void setOutingFlag(int outingFlag) {
        this.outingFlag = outingFlag;
    }
}
