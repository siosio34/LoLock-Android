package com.gunghi.tgwing.lolock.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;


public class ResponseInOutLog {

    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseInOutLog() {
    }

    /**
     *
     * @param results
     */
    public ResponseInOutLog(List<Result> results) {
        super();
        this.results = results;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public ResponseInOutLog withResults(List<Result> results) {
        this.results = results;
        return this;
    }

    public class OutTime {

        @SerializedName("month")
        @Expose
        private Integer month;
        @SerializedName("day")
        @Expose
        private Integer day;
        @SerializedName("hour")
        @Expose
        private Integer hour;
        @SerializedName("min")
        @Expose
        private Integer min;

        @SerializedName("dayName")
        @Expose
        private String dayName;

        @SerializedName("timeStamp")
        @Expose
        private long timeStamp;



        /**
         * No args constructor for use in serialization
         *
         */
        public OutTime() {
        }

        /**
         *  @param month
         * @param day
         * @param hour
         * @param min
         * @param dayName
         */
        public OutTime(Integer month, Integer day, Integer hour, Integer min, String dayName) {
            super();
            this.month = month;
            this.day = day;
            this.hour = hour;
            this.min = min;
            this.dayName = dayName;
        }

        public Integer getMonth() {
            return month;
        }

        public void setMonth(Integer month) {
            this.month = month;
        }

        public OutTime withMonth(Integer month) {
            this.month = month;
            return this;
        }

        public Integer getDay() {
            return day;
        }

        public void setDay(Integer day) {
            this.day = day;
        }

        public OutTime withDay(Integer day) {
            this.day = day;
            return this;
        }

        public Integer getHour() {
            return hour;
        }

        public void setHour(Integer hour) {
            this.hour = hour;
        }

        public OutTime withHour(Integer hour) {
            this.hour = hour;
            return this;
        }

        public Integer getMin() {
            return min;
        }

        public void setMin(Integer min) {
            this.min = min;
        }

        public OutTime withMin(Integer min) {
            this.min = min;
            return this;
        }

        public String getDayName() {
            return dayName;
        }

        public void setDayName(String dayName) {
            this.dayName = dayName;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
        }
    }

    public class Result {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("outFlag")
        @Expose
        private Integer outFlag;
        @SerializedName("strangeFlag")
        @Expose
        private Integer strangeFlag;
        @SerializedName("outTime")
        @Expose
        private OutTime outTime;

        /**
         * No args constructor for use in serialization
         *
         */
        public Result() {
        }

        /**
         *
         * @param name
         * @param outFlag
         * @param strangeFlag
         * @param outTime
         */
        public Result(String name, Integer outFlag, Integer strangeFlag, OutTime outTime) {
            super();
            this.name = name;
            this.outFlag = outFlag;
            this.strangeFlag = strangeFlag;
            this.outTime = outTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Result withName(String name) {
            this.name = name;
            return this;
        }

        public Integer getOutFlag() {
            return outFlag;
        }

        public void setOutFlag(Integer outFlag) {
            this.outFlag = outFlag;
        }

        public Result withOutFlag(Integer outFlag) {
            this.outFlag = outFlag;
            return this;
        }

        public Integer getStrangeFlag() {
            return strangeFlag;
        }

        public void setStrangeFlag(Integer strangeFlag) {
            this.strangeFlag = strangeFlag;
        }

        public Result withStrangeFlag(Integer strangeFlag) {
            this.strangeFlag = strangeFlag;
            return this;
        }

        public OutTime getOutTime() {
            return outTime;
        }

        public void setOutTime(OutTime outTime) {
            this.outTime = outTime;
        }

        public Result withOutTime(OutTime outTime) {
            this.outTime = outTime;
            return this;
        }

    }


}


