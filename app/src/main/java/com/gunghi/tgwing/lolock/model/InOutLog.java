package com.gunghi.tgwing.lolock.model;

/**
 * Created by joyeongje on 2017. 7. 22..
 */

public class InOutLog {

    private String name;
    private String time;
    private String inOutDate;
    private String strangeFlag;

    public InOutLog() {

    }

    public InOutLog(String name, String time, String inOutDate, String strangeFlag) {
        this.name = name;
        this.time = time;
        this.inOutDate = inOutDate;
        this.strangeFlag = strangeFlag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStrangeFlag() {
        return strangeFlag;
    }

    public void setStrangeFlag(String strangeFlag) {
        this.strangeFlag = strangeFlag;
    }

    public String getInOutDate() {
        return inOutDate;
    }

    public void setInOutDate(String inOutDate) {
        this.inOutDate = inOutDate;
    }
}
