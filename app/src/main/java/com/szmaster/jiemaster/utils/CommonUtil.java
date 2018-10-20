package com.szmaster.jiemaster.utils;

import android.Manifest;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.szmaster.jiemaster.App;
import com.szmaster.jiemaster.Constants;
import com.szmaster.jiemaster.db.PreferenceImp;

/**
 * 通用工具类
 */
public class CommonUtil {

    private static PackageManager mPackageManager;

    public static boolean isEmpty(Collection collection) {
        return null == collection || collection.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return null == map || map.isEmpty();
    }

    public static boolean isEmpty(Object[] objs) {
        return null == objs || objs.length <= 0;
    }

    public static boolean isEmpty(int[] objs) {
        return null == objs || objs.length <= 0;
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return null == charSequence || charSequence.length() <= 0;
    }

    public static String[] mergeArray(String[] a, String[] b) {
        if (null == a || null == b) {
            return null;
        } else {
            int al = a.length;
            int bl = b.length;
            a = Arrays.copyOf(a, al + bl);
            System.arraycopy(b, 0, a, al, bl);
            return a;
        }
    }

    /**
     * 隐藏输入法键盘
     *
     * @param view
     */
    public static void hideInputMethod(View view) {
        if (view == null)
            return;
        // 隐藏键盘
        Context context = App.getContext();
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 判断某个界面是否在前台
     *
     * @param context
     * @param className 某个界面名称
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }

        return false;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            android.util.Log.d("", e.toString());
        }

        return result;
    }

    /**
     * 压缩bitmap
     *
     * @param image     原圖
     * @param compress2 壓縮到尺寸,單位k
     * @return
     */
    public static Bitmap compressBmpFromBmp(Bitmap image, int compress2) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int options = 100;
        image.compress(CompressFormat.JPEG, 100, output);
        while (output.toByteArray().length / 1024 > compress2) {
            output.reset();
            options -= 10;
            image.compress(CompressFormat.JPEG, options, output);
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(output.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 压缩然后转byte[]
     *
     * @param image     原图
     * @param compress2 压缩尺寸,单位k
     * @return
     */
    public static byte[] compressByeArrFromBmp(Bitmap image, int compress2, boolean recycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int options = 100;
        image.compress(CompressFormat.JPEG, 100, output);
        while (output.toByteArray().length / 1024 > compress2 && options > 1) {
            output.reset();
            options -= 10;
            image.compress(CompressFormat.JPEG, options, output);
        }
        byte[] result = output.toByteArray();
        if (recycle) {
            image.recycle();
        }
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String transactionBuilder(String transaction) {
        return TextUtils.isEmpty(transaction) ? String.valueOf(System.currentTimeMillis()) : transaction + String.valueOf(System.currentTimeMillis());
    }


    public static int getVersion() {
        // 获取packagemanager的实例
        int version = 1;

        if (mPackageManager == null) {
            mPackageManager = App.getContext()
                    .getPackageManager();
        }
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        try {
            packInfo = mPackageManager.getPackageInfo(App
                    .getContext().getPackageName(), 0);
            version = packInfo.versionCode;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return version;
    }

    public static String getVersionName() {
        // 获取packagemanager的实例
        String verName = "0.1.0";
        if (mPackageManager == null) {
            mPackageManager = App.getContext()
                    .getPackageManager();
        }
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        try {
            packInfo = mPackageManager.getPackageInfo(App
                    .getContext().getPackageName(), 0);
            verName = packInfo.versionName;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return verName;
    }

    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        return tm.getDeviceId();
    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 如（假设key为1234）：
     * time=1539766193
     * version=1.0.4
     * mac=00:11:22:33:44
     * imei=841746298812321
     * 则：
     * sign = md5(imei=841746298812321&mac=00:11:22:33:44&time=1539766193version=1.0.41234).hexdigest().lowwer()
     */
    private static StringBuffer sb = new StringBuffer();

    public static String getSign(HashMap<String, String> map) {
        List<String> list = new ArrayList<>();
        list.addAll(map.keySet());
        Collections.sort(list);
        if (sb.length() > 0) {
            sb.delete(0, sb.length());
        }
        for (String key : list) {
            sb.append(key).append("=").append(map.get(key)).append("&");
        }
        sb.delete(sb.length() - 1, sb.length());
        sb.append(Constants.KEY);
        Log.d("sign -> " + sb.toString());

        return MD5Utils.encodeMD5(sb.toString()).toLowerCase();
    }

    public static int getWindowWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static String getb(){

        return Build.BRAND;
    }

}
