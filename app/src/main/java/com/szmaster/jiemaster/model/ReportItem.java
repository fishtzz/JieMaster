package com.szmaster.jiemaster.model;

import com.google.gson.annotations.SerializedName;

public class ReportItem {
    @SerializedName("img")
    private String img;

    @SerializedName("title")
    private String title;

    @SerializedName("label1")
    private String label1;

    @SerializedName("label2")
    private String label2;

    @SerializedName("mark")
    private String mark;

    @SerializedName("desc")
    private String desc;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLabel1() {
        return label1;
    }

    public void setLabel1(String label1) {
        this.label1 = label1;
    }

    public String getLabel2() {
        return label2;
    }

    public void setLabel2(String label2) {
        this.label2 = label2;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
