package com.szmaster.jiemaster.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jiangsiyu on 2018/10/18.
 */

public class ReviseUsernameModel implements IModel<Username> {

    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Username username;

    public void setUsername(Username username) {
        this.username = username;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public Username getData() {
        return username;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
