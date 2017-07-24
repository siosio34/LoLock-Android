package com.gunghi.tgwing.lolock.model;

/**
 * Created by joyeongje on 2017. 7. 25..
 */

public class GoogleSchedularData {

    private String summaryTitle;
    private String startTime;
    private String googleLink;

    public GoogleSchedularData() {

    }

    public GoogleSchedularData(String summaryTitle,String startTime,String googleLink) {
        this.summaryTitle = summaryTitle;
        this.startTime = startTime;
        this.googleLink = googleLink;
    }

    public String getSummaryTitle() {
        return summaryTitle;
    }

    public void setSummaryTitle(String summaryTitle) {
        this.summaryTitle = summaryTitle;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getGoogleLink() {
        return googleLink;
    }

    public void setGoogleLink(String googleLink) {
        this.googleLink = googleLink;
    }
}
