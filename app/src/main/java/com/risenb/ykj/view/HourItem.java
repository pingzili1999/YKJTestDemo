package com.risenb.ykj.view;

import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by user on 2016/10/19.
 */
public class HourItem {

    public String time; //时间点
    public Rect windyBoxRect; //表示风力的box(自己创建)
    public int windy; //风力
    public int temperature; //温度
    public Point tempPoint; //温度的点坐标（自己创建）
    public int res = -1; //图片资源(有则绘制)

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Rect getWindyBoxRect() {
        return windyBoxRect;
    }

    public void setWindyBoxRect(Rect windyBoxRect) {
        this.windyBoxRect = windyBoxRect;
    }

    public int getWindy() {
        return windy;
    }

    public void setWindy(int windy) {
        this.windy = windy;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public Point getTempPoint() {
        return tempPoint;
    }

    public void setTempPoint(Point tempPoint) {
        this.tempPoint = tempPoint;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }
}
