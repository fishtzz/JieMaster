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

import com.google.gson.Gson;
import com.szmaster.jiemaster.bus.UserBus;
import com.szmaster.jiemaster.db.PreferenceImp;
import com.szmaster.jiemaster.model.ReportModel;
import com.szmaster.jiemaster.utils.CommonUtil;

public class SplashActivity extends Activity {

    private static final int REQUEST_CODE = 10087;

    Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Gson gson = new Gson();
        ReportModel model = gson.fromJson("{\"message\": \"\", \"code\": 200, \"data\": {\"items\": [{\"img\": \"http://www.shiwandashi.com/appicon/22fd599e-cdf3-11e8-b180-00163e041d62.jpg\", \"title\": \"\\u4fe1\\u7528\\u5361\\u653b\\u7565\", \"url\": \"https://mp.weixin.qq.com/s/HySrhEwlrko-FRfRNE7elA\", \"label1\": \"\\u6700\\u5168\\u653b\\u7565\", \"label2\": \"\\u5feb\\u901f\\u7533\\u8bf7\", \"mark\": \"\", \"desc\": \"\\u4fe1\\u7528\\u5361\\u7533\\u8bf7\\u653b\\u7565\"}, {\"img\": \"http://www.shiwandashi.com/appicon/9b08db98-cdf3-11e8-a0f7-00163e041d62.png\", \"title\": \"\\u8fd8\\u6b3e\\u79d8\\u7c4d\", \"url\": \"https://mp.weixin.qq.com/s/hRb3Tq6xCdol_Lnkw_sQMw\", \"label1\": \"\\u94f6\\u884c\\u5361\\u8fd8\\u6b3e\\u5c0f\\u8bfe\\u5802\", \"label2\": \"\\u5b9a\\u671f\\u66f4\\u65b0\", \"mark\": \"\", \"desc\": \"\\u907f\\u514d\\u903e\\u671f\\u597d\\u65b9\\u6cd5\"}, {\"img\": \"http://www.shiwandashi.com/appicon/c884ff8e-cdf3-11e8-b180-00163e041d62.png\", \"title\": \"\\u4e1a\\u52a1\\u7f51\\u70b9\", \"url\": \"http://www.shgjj.com/html/wap/tools/ywwd/index.html\", \"label1\": \"\\u6700\\u5168\", \"label2\": \"\\u6700\\u65b9\\u4fbf\", \"mark\": \"\", \"desc\": \"\\u4e1a\\u52a1\\u7f51\\u70b9\\u5927\\u5168\"}, {\"img\": \"http://www.shiwandashi.com/appicon/f9e3b20a-cdf3-11e8-a0f7-00163e041d62.png\", \"title\": \"\\u4e1a\\u52a1\\u6307\\u5357\", \"url\": \"http://www.shgjj.com/html/wap/policy/fwzn/index.html\", \"label1\": \"\\u529e\\u7406\\u516c\\u79ef\\u91d1\", \"label2\": \"\\u6700\\u5168\\u6307\\u5357\\u653b\\u7565\", \"mark\": \"\", \"desc\": \"\\u516c\\u79ef\\u91d1\\u4e1a\\u52a1\\u6307\\u5357\"}, {\"img\": \"http://www.shiwandashi.com/appicon/39c04eba-cdf4-11e8-b180-00163e041d62.png\", \"title\": \"\\u8d37\\u6b3e\\u8bd5\\u7b97\", \"url\": \"http://www.shgjj.com/app/wap/zigeCalc_wap/tools_ammount.html\", \"label1\": \"\\u5feb\\u901f\\u8ba1\\u7b97\", \"label2\": \"\\u6d4b\\u6d4b\\u4f60\\u7684\\u516c\\u79ef\\u91d1\\u80fd\\u8d37\\u591a\\u5c11\\u94b1\", \"mark\": \"\", \"desc\": \"\\u516c\\u79ef\\u91d1\\u8d37\\u6b3e\\u8bd5\\u7b97\"}, {\"img\": \"http://www.shiwandashi.com/appicon/63380daa-cdf4-11e8-8416-00163e041d62.png\", \"title\": \"\\u516c\\u79ef\\u91d1\\u7f34\\u5b58\", \"url\": \"http://www.shgjj.com/app/wap/jccalc/tools_paid.html\", \"label1\": \"\\u6700\\u5168\\u7b97\\u6cd5\", \"label2\": \"\\u5feb\\u3001\\u7a33\\u3001\\u51c6\", \"mark\": \"\", \"desc\": \"\\u7f34\\u5b58\\u8ba1\\u7b97\\u5de5\\u5177\"}, {\"img\": \"http://www.shiwandashi.com/appicon/8a3eb584-cdf4-11e8-a0f7-00163e041d62.png\", \"title\": \"\\u516c\\u79ef\\u91d1\\u67e5\\u8be2\", \"url\": \"http://m.shgjj.com/verifier/verifier/index\", \"label1\": \"\\u4e09\\u6b65\\u67e5\\u8be2\", \"label2\": \"\\u66f4\\u5feb\\u66f4\\u4fbf\\u6377\", \"mark\": \"\", \"desc\": \"\\u4e0a\\u6d77\\u516c\\u79ef\\u91d1\\u67e5\\u8be2\"}, {\"img\": \"http://www.shiwandashi.com/appicon/b0db08fa-cdf4-11e8-a0f7-00163e041d62.png\", \"title\": \"\\u516c\\u79ef\\u91d1\\u653b\\u7565\", \"url\": \"http://www.shgjj.com/html/wap/policy/tjgjj/index.html\", \"label1\": \"\\u6700\\u5168\", \"label2\": \"\\u6700\\u4fbf\\u6377\", \"mark\": \"\", \"desc\": \"\\u516c\\u79ef\\u91d1\\u8d37\\u6b3e\\u653b\\u7565\"}], \"banners\": [{\"url\": \"#\", \"img\": \"http://www.shiwandashi.com/appicon/aa49cde8-d02c-11e8-a0f7-00163e041d62.png\"}, {\"url\": \"#\", \"img\": \"http://www.shiwandashi.com/appicon/aa49cde8-d02c-11e8-a0f7-00163e041d62.png\"}, {\"url\": \"#\", \"img\": \"http://www.shiwandashi.com/appicon/aa49cde8-d02c-11e8-a0f7-00163e041d62.png\"}], \"activity\": [{\"url\": \"http://news.163.com/18/0117/18/D8CG71DH00018AOR.html\", \"title\": \"\\u4e0a\\u6d77\\u516c\\u79ef\\u91d1\", \"img\": \"http://www.shiwandashi.com/appicon/9d906e78-cc5c-11e8-a186-00163e041d62.jpg\", \"desc\": \"\\u4e0a\\u6d77\\u516c\\u79ef\\u91d1\\u7f34\\u5b58\\u3001\\u63d0\\u53d6\\u3001\\u8d37\\u6b3e\\u7b49\\u7ba1\\u7406\\u529e\\u6cd5\\u6b63\\u5f0f\\u51fa\\u53f0\"}, {\"url\": \"http://news.pchouse.com.cn/222/2227818.html\", \"title\": \"\\u516c\\u79ef\\u91d1\\u4f7f\\u7528\\u653b\\u7565\", \"img\": \"http://www.shiwandashi.com/appicon/a24f43bc-cc5c-11e8-be40-00163e041d62.jpg\", \"desc\": \"\\u516c\\u79ef\\u91d1+\\u5546\\u4e1a\\u7ec4\\u5408\\u8d37\\u6b3e\\u4e86\\u89e3\\u4e0b\"}, {\"url\": \"http://finance.china.com.cn/news/20180614/4668052.shtml\", \"title\": \"\\u592e\\u884c\\u5f81\\u4fe1\\u77e5\\u591a\\u5c11\\uff1f\", \"img\": \"http://www.shiwandashi.com/appicon/ab6de4bc-cc5c-11e8-a186-00163e041d62.jpg\", \"desc\": \"\\u4e92\\u91d1\\u63a5\\u5165\\u592e\\u884c\\u5f81\\u4fe1\\u7684\\u610f\\u613f\\u548c\\u95e8\\u69db\\u5982\\u4f55\\uff1f\"}, {\"url\": \"https://www.rong360.com/gl/2018/03/08/153282.html\", \"title\": \"\\u7f51\\u8d37\\u653b\\u7565\", \"img\": \"http://www.shiwandashi.com/appicon/adf3af6e-cc5c-11e8-be40-00163e041d62.jpg\", \"desc\": \"\\u6559\\u4f60\\u600e\\u4e48\\u505a\\u624d\\u80fd\\u63d0\\u9ad8\\u7f51\\u8d37\\u901a\\u8fc7\\u7387\"}]}}", ReportModel.class);
        PreferenceImp.cacheReport(model.getData());
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
