package com.risenb.ykj.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vone (codeofshield@gmail.com) on 2016/8/7.
 */
public class HttpResult<T> {

    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
