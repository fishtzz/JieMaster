package com.szmaster.jiemaster.model;

import com.google.gson.annotations.SerializedName;

public class ReportActivity {
    @SerializedName("url")
    private String url;

    @SerializedName("title")
    private String title;

    @SerializedName("img")
    private String img;

    @SerializedName("desc")
    private String desc;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
