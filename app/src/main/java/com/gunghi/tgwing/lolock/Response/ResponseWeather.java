package com.gunghi.tgwing.lolock.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by joyeongje on 2017. 7. 22..
 */

public class ResponseWeather {

    @SerializedName("baseTime")
    @Expose
    private Integer baseTime;
    @SerializedName("baseDate")
    @Expose
    private Integer baseDate;
    @SerializedName("sky")
    @Expose
    private String sky;
    @SerializedName("temperature")
    @Expose
    private Double temperature;
    @SerializedName("probabilityRain")
    @Expose
    private Integer probabilityRain;
    @SerializedName("minTemperature")
    @Expose
    private Integer minTemperature;
    @SerializedName("maxTemperature")
    @Expose
    private Integer maxTemperature;

    @SerializedName("location")
    @Expose
    private String location;
    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseWeather() {

    }

    /**
     *
     * @param sky
     * @param minTemperature
     * @param baseDate
     * @param baseTime
     * @param probabilityRain
     * @param maxTemperature
     * @param temperature
     */
    public ResponseWeather(Integer baseTime, Integer baseDate, String sky, Double temperature, Integer probabilityRain, Integer minTemperature, Integer maxTemperature) {
        super();
        this.baseTime = baseTime;
        this.baseDate = baseDate;
        this.sky = sky;
        this.temperature = temperature;
        this.probabilityRain = probabilityRain;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }

    public Integer getBaseTime() {
        return baseTime;
    }

    public void setBaseTime(Integer baseTime) {
        this.baseTime = baseTime;
    }

    public Integer getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(Integer baseDate) {
        this.baseDate = baseDate;
    }

    public String getSky() {
        return sky;
    }

    public void setSky(String sky) {
        this.sky = sky;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getProbabilityRain() {
        return probabilityRain;
    }

    public void setProbabilityRain(Integer probabilityRain) {
        this.probabilityRain = probabilityRain;
    }

    public Integer getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Integer minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Integer getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Integer maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}