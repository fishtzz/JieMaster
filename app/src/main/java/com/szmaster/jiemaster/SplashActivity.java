package com.szmaster.jiemaster;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.szmaster.jiemaster.bus.UserBus;
import com.szmaster.jiemaster.db.PreferenceImp;
import com.szmaster.jiemaster.utils.CommonUtil;

public class SplashActivity extends Activity {

    private static final int REQUEST_CODE = 10087;

    Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        /**
         * 初始化缓存登录状态
         */
        if (null != PreferenceImp.getUserCache()) {
            UserBus.getInstance().login(PreferenceImp.getUserCache());
        }
        if (TextUtils.isEmpty(PreferenceImp.getMacCache())) {
            PreferenceImp.cacheMac(CommonUtil.getMacAddr());
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);
        } else {
            cacheIMEI();
            goon();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            cacheIMEI();
        }
        goon();
    }

    private void cacheIMEI() {
        if (TextUtils.isEmpty(PreferenceImp.getIMEICache())) {
            PreferenceImp.cacheIMEI(CommonUtil.getDeviceId(App.getContext()));
        }
    }

    private void goon() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        }, 1500);

    }


    @Override
    public void onBackPressed() {
        return;
    }
}
