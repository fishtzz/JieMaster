package com.szmaster.jiemaster.model;

import com.google.gson.annotations.SerializedName;

public class UserImg {
    @SerializedName("userImg")
    private String userImg;

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }
}
