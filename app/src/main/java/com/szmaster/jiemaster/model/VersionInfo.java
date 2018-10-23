package com.szmaster.jiemaster.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jiangsiyu on 2018/10/22.
 */

public class VersionInfo {

    @SerializedName("version")
    private String versionName;
    @SerializedName("info")
    private String updateDesc;
    @SerializedName("url")
    private String url;

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpdateDesc() {
        return updateDesc;
    }

    public void setUpdateDesc(String updateDesc) {
        this.updateDesc = updateDesc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
