package com.szmaster.jiemaster.model;

import com.google.gson.annotations.SerializedName;

public class ReviseUserImgModel implements IModel<UserImg> {

    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private UserImg userImg;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUserImg(UserImg userImg) {
        this.userImg = userImg;
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
    public UserImg getData() {
        return userImg;
    }
}
