package com.risenb.ykj;

/**
 * Created by Administrator on 2016/10/10.
 */
public class WeatherDailyModel {
    /**
     * date : 2016-05-30
     * text_day : 多云
     * code_day : 4
     * text_night : 阴
     * code_night : 9
     * high : 34
     * low : 22
     */
    private String date;
    private String text_day;
    private int code_day;
    private String text_night;
    private int code_night;
    private int high;
    private int low;
    private boolean start;

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText_day() {
        return text_day;
    }

    public void setText_day(String text_day) {
        this.text_day = text_day;
    }

    public int getCode_day() {
        return code_day;
    }

    public void setCode_day(int code_day) {
        this.code_day = code_day;
    }

    public String getText_night() {
        return text_night;
    }

    public void setText_night(String text_night) {
        this.text_night = text_night;
    }

    public int getCode_night() {
        return code_night;
    }

    public void setCode_night(int code_night) {
        this.code_night = code_night;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }
}
