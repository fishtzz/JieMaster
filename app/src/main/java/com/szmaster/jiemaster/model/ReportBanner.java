package com.szmaster.jiemaster.model;

import com.google.gson.annotations.SerializedName;

public class ReportBanner {
    @SerializedName("url")
    private String url;

    @SerializedName("img")
    private String img;

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return this.img;
    }
}
