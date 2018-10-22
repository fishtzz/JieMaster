package com.szmaster.jiemaster.model;

/**
 * Created by jiangsiyu on 2018/10/22.
 */

public class VersionInfo {

    private String versionName;
    private String updateDesc;
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
