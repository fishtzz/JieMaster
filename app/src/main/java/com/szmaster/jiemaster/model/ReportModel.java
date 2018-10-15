package com.szmaster.jiemaster.model;

import com.google.gson.annotations.SerializedName;

public class ReportModel implements IModel<ReportData> {

    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private ReportData data;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(ReportData data) {
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
    public ReportData getData() {
        return data;
    }
}
