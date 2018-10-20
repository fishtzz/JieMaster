package com.szmaster.jiemaster.model;

import com.google.gson.annotations.SerializedName;

public class Username {
    @SerializedName("userName")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
