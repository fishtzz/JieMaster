package com.szmaster.jiemaster;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.szmaster.jiemaster.utils.DensityUtil;

/**
 * Created by jiangsiyu on 2018/10/16.
 */

public class App extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    public static Context getContext() {
        return sContext;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private void init() {
        sContext = this.getApplicationContext();
        DensityUtil.init(sContext);
    }

}
