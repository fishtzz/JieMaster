package com.szmaster.jiemaster.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jiangsiyu on 2018/10/22.
 */

public class CheckVersionModel implements IModel<VersionInfo> {


    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private VersionInfo data;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(VersionInfo data) {
        this.data = data;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public VersionInfo getData() {
        return data;
    }
}
