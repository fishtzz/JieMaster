package com.szmaster.jiemaster.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportData {
    @SerializedName("activity")
    private List<ReportActivity> activity;

    @SerializedName("items")
    private List<ReportItem> items;

    @SerializedName("banners")
    private List<ReportBanner> banners;

    public List<ReportBanner> getBanners() {
        return banners;
    }

    public void setBanners(List<ReportBanner> banners) {
        this.banners = banners;
    }

    public List<ReportActivity> getActivity() {
        return activity;
    }

    public void setActivity(List<ReportActivity> activity) {
        this.activity = activity;
    }

    public List<ReportItem> getItems() {
        return items;
    }

    public void setItems(List<ReportItem> items) {
        this.items = items;
    }
}
