package com.szmaster.jiemaster.db;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.szmaster.jiemaster.model.ReportData;
import com.szmaster.jiemaster.model.User;

/**
 * Created by jiangsiyu on 2018/10/19.
 */

public class PreferenceImp {

    private static final String KEY_MAC = "mac";
    private static final String KEY_IMEI = "imei";
    private static final String KEY_USER = "user";
    private static final String KEY_REPORT = "report";
    private static final String KEY_TOKEN = "token";
    private static Gson sGson = new Gson();

    public static void cacheMac(String mac) {
        if (!TextUtils.isEmpty(mac) && !mac.equals("02:00:00:00:00:00")) {
            SharedPreferencesManager.getInstance().putData(KEY_MAC, mac);
        }
    }

    public static String getMacCache() {
        return SharedPreferencesManager.getInstance().getString(KEY_MAC);
    }

    public static void cacheIMEI(String imei) {
        if (!TextUtils.isEmpty(imei)) {
            SharedPreferencesManager.getInstance().putData(KEY_IMEI, imei);
        }
    }

    public static String getIMEICache() {
        return SharedPreferencesManager.getInstance().getString(KEY_IMEI);
    }

    public static void cacheUser(User user) {
        if (null != user) {
            SharedPreferencesManager.getInstance().putData(KEY_USER, sGson.toJson(user));
        }
    }

    public static User getUserCache() {
        String u = SharedPreferencesManager.getInstance().getString(KEY_USER);
        if (TextUtils.isEmpty(u)) {
            return null;
        } else {
            return sGson.fromJson(u, User.class);
        }
    }

    public static void cacheReport(ReportData reportData) {
        if (null != reportData) {
            SharedPreferencesManager.getInstance().putData(KEY_REPORT, sGson.toJson(reportData));
        }
    }

    public static ReportData getReportCache() {
        String d = SharedPreferencesManager.getInstance().getString(KEY_REPORT);
        if (TextUtils.isEmpty(d)) {
            return null;
        } else {
            return sGson.fromJson(d, ReportData.class);
        }
    }

    public static void cacheToken(String token) {
        if (!TextUtils.isEmpty(token)) {
            SharedPreferencesManager.getInstance().putData(KEY_TOKEN, token);
        }
    }

    public static String getToken() {
        return SharedPreferencesManager.getInstance().getString(KEY_TOKEN);
    }

    public static void logout() {
        SharedPreferencesManager.getInstance().putData(KEY_USER, "");
        SharedPreferencesManager.getInstance().putData(KEY_TOKEN, "");
    }

    public static void login(User user) {
        cacheUser(user);
        cacheToken(user.getToken());
    }
}
