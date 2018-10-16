package com.szmaster.jiemaster.utils;

import android.text.TextUtils;
import android.text.format.DateFormat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import com.szmaster.jiemaster.App;
import com.szmaster.jiemaster.Constants;

/**
 * Wraps Android Logging to enable or disable debug output using Constants
 */
public final class Log {

    private static final String TAG = "GitStars";
    private static boolean DEBUG = Constants.IS_DEBUG;

    public static void v(String msg) {
        if (DEBUG) {
            android.util.Log.v(TAG, msg + "");
        }
    }

    public static void v(String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.v(TAG, msg + "", tr);
        }
    }

    public static void d(String msg) {
        if (DEBUG) {
            android.util.Log.d(TAG, msg + "");
        }
    }

    public static void d(Class<?> cls, String msg) {
        String tag = cls.getSimpleName();
        if (DEBUG) {
            android.util.Log.d(tag, buildMessage("%s", msg + ""));
        }
        return;
    }

    public static void d(String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.d(TAG, msg + "", tr);
        }
    }


    public static void d(Class<?> c, Throwable tr) {
        if (DEBUG) {
            android.util.Log.d(TAG, c.getName(), tr);
        }
    }

    public static void i(String msg) {
        if (DEBUG) {
            android.util.Log.i(TAG, msg + "");
        }
    }

    public static void i(String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.i(TAG, msg + "", tr);
        }
    }

    public static void w(String msg) {
        android.util.Log.w(TAG, msg + "");
    }

    public static void w(String msg, Throwable tr) {
        android.util.Log.w(TAG, msg + "", tr);
    }

    public static void w(Throwable tr) {
        android.util.Log.w(TAG, tr + "");
    }

    public static void e(String msg) {
        android.util.Log.e(TAG, msg + "");
    }

    public static void e(String msg, Throwable tr) {

        android.util.Log.e(TAG, msg + "", tr);
    }

    private static String buildMessage(String format, Object... args) {
        String msg = (args == null) ? format : String.format(Locale.US, format,
                args);
        StackTraceElement[] trace = new Throwable().fillInStackTrace()
                .getStackTrace();

        String caller = "<unknown>";
        // Walk up the stack looking for the first caller outside of Log.
        // It will be at least two frames up, so start there.
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(Log.class)) {
                String callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass
                        .lastIndexOf('.') + 1);
                callingClass = callingClass.substring(callingClass
                        .lastIndexOf('$') + 1);

                caller = callingClass + "." + trace[i].getMethodName();
                break;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s", Thread.currentThread()
                .getId(), caller, msg);
    }

    public static void dumpHashMap(Class<?> cls, HashMap<String, String> params) {
        if (DEBUG) {
            Iterator iterator = params.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next().toString();
                android.util.Log.d(cls.getSimpleName(), key + "=" + params.get(key));
            }
        }
    }

    private static final String FILE_NAME_FORMAT = "yyyy-MM-dd";
    private static final String FILE_NAME_REQUESTINFO = "reque_info";
    private static final String LOG_HEAD_FORMAT = "yyyy/MM/dd/ kk:mm:ss";
    private static final String FILE_DATE_FORMAT = "yyyy-MM-dd";
    private static final String FILE_NAME_CHAT_LOG = "chat";

    public static void wPushInfo(String text) {
        String fileName = DateFormat.format(FILE_NAME_FORMAT, System.currentTimeMillis()).toString();
        writeToFile(text, fileName);
    }

    public static void wChatInfo(String text) {
        String fileName = FILE_NAME_CHAT_LOG + DateFormat.format(FILE_DATE_FORMAT, System.currentTimeMillis()).toString();
        writeToFile(text, fileName);
    }

    public static void wRequestInfo(String text) {
        String fileName = DateFormat.format(FILE_NAME_REQUESTINFO, System.currentTimeMillis()).toString();
        writeToFile(text, fileName);
    }

    private static void writeToFile(String text, String fileName) {
        String logPath = LibIOUtil.getLogCachePath(App.getContext());
        if (!TextUtils.isEmpty(logPath)) {

            String msg = DateFormat.format(LOG_HEAD_FORMAT, System.currentTimeMillis()).toString() + " " + text;

            File file = new File(logPath, fileName);

            try {
                FileWriter filerWriter = new FileWriter(file, true);
                BufferedWriter bufWriter = new BufferedWriter(filerWriter);
                bufWriter.write(msg);
                bufWriter.newLine();
                bufWriter.close();
                filerWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
