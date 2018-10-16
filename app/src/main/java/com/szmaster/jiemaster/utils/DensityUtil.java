package com.szmaster.jiemaster.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import com.szmaster.jiemaster.App;

public class DensityUtil {

    private static DisplayMetrics dm;

    public static void init(Context context) {
        if (dm == null) {
            dm = context.getApplicationContext().getResources().getDisplayMetrics();
        }
    }

    public static DisplayMetrics getDisplayMetrics() {
        if (dm == null)
            init(App.getContext());
        return dm;
    }

    public static int dip2px(int dpValue) {
        return (int) (dpValue * getDisplayMetrics().density + 0.5f);
    }

    public static int px2dip(int pxValue) {
        return (int) (pxValue / getDisplayMetrics().density + 0.5f);
    }

    public static int sp2px(int spValue) {
        return (int) (spValue * getDisplayMetrics().density + 0.5f);
    }

    public static int px2sp(int pxValue) {
        return (int) (pxValue / getDisplayMetrics().density + 0.5f);
    }

    public static int getScaledSize(int size) {
        return (int) (size * getDisplayMetrics().scaledDensity);
    }

}
