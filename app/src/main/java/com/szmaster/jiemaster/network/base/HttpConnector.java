package com.szmaster.jiemaster.network.base;

import android.os.Build;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.szmaster.jiemaster.App;
import com.szmaster.jiemaster.Constants;
import com.szmaster.jiemaster.db.PreferenceImp;
import com.szmaster.jiemaster.network.base.HttpLoggingInterceptor.Level;
import com.szmaster.jiemaster.utils.CommonUtil;
import com.szmaster.jiemaster.utils.Log;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * retrofit封装
 * Created by luozhaocheng on 3/5/16.
 */
public class HttpConnector {

    private static final Map<String, Retrofit> mAllRetrofits = new HashMap<String, Retrofit>();
    private static final Map<String, Retrofit> mAllEncryptionRetrofits = new HashMap<String, Retrofit>();
    private static final Map<String, Retrofit> mManualRetrofits = new HashMap<>();
    private static Object lock = new Object();
    private static Object encryptionLock = new Object();
    private static Object manualLock = new Object();

    public static final Retrofit getServer(String uri) {
        synchronized (lock) {
            Retrofit retrofit = mAllRetrofits.get(uri);
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(uri)
                        .client(getOkHttpClient())
                        .build();
            }
            mAllRetrofits.put(uri, retrofit);
            return retrofit;
        }
    }

    public static final Retrofit getEncryptionServer(String uri, String md5Key, String desKey) {
        synchronized (encryptionLock) {
            String key = uri + md5Key + desKey;
            Retrofit retrofit = mAllEncryptionRetrofits.get(key);
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(uri)
                        .client(getEncryptionOkHttpClient(md5Key, desKey))
                        .build();
            }
            mAllEncryptionRetrofits.put(key, retrofit);
            return retrofit;
        }
    }


    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        CustomInterceptor interceptor = new CustomInterceptor();
        clientBuilder.addInterceptor(interceptor);
        if (Constants.IS_DEBUG) {
            // print Log
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.d(HttpConnector.class, message);
                }
            });
            logInterceptor.setLevel(Level.BODY);
            clientBuilder.interceptors().add(logInterceptor);
        }
        clientBuilder.connectTimeout(Constants.TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.readTimeout(10, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(10, TimeUnit.SECONDS);
        return clientBuilder.build();
    }

    private static OkHttpClient getEncryptionOkHttpClient(String md5Key, String desKey) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        CustomInterceptor interceptor = new CustomInterceptor(true, md5Key, desKey);
        clientBuilder.addInterceptor(interceptor);
        if (Constants.IS_DEBUG) {
            // print Log
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.d(HttpConnector.class, message);
                }
            });
            logInterceptor.setLevel(Level.BODY);
            clientBuilder.interceptors().add(logInterceptor);
        }
        clientBuilder.connectTimeout(10, TimeUnit.SECONDS);
        clientBuilder.readTimeout(10, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(10, TimeUnit.SECONDS);
        return clientBuilder.build();
    }

    private static class CustomInterceptor implements Interceptor {

        private boolean encryptionFormParams = false;
        private String mMd5Key;
        private String mDesKey;

        public CustomInterceptor(boolean encryptionFormParams, String md5Key, String desKey) {
            this.encryptionFormParams = encryptionFormParams;
            mMd5Key = md5Key;
            mDesKey = desKey;
        }

        public CustomInterceptor() {
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            HttpUrl.Builder urlBuilder = chain.request().url().newBuilder();
            // 设置基础参数
            urlBuilder.addQueryParameter("sign", CommonUtil.getSign());
            urlBuilder.addQueryParameter("imei", PreferenceImp.getIMEICache());
            urlBuilder.addQueryParameter("mac", PreferenceImp.getIMEICache());
            urlBuilder.addQueryParameter("SerialNumber", Build.SERIAL);
            if (Constants.IS_DEBUG) {
                urlBuilder.addQueryParameter("type", Constants.DEBUG);
            } else {
                urlBuilder.addQueryParameter("type", Constants.RELEASE);
            }

            // 设置Form 表单及Cookie
            Request.Builder newBuilder = chain.request().newBuilder();
            newBuilder.url(urlBuilder.build());
            // 如果加密参数
//            if (encryptionFormParams) {
//                RequestBody body = chain.request().body();
//                if (body instanceof FormBody) {
//                    FormBody formBody = (FormBody) body;
//                    HashMap<String, String> params = new HashMap<>();
//                    for (int i = 0; i < formBody.size(); i++) {
//                        params.put(formBody.encodedName(i), formBody.encodedValue(i));
//                    }
//
//                    FormBody newFormBody = new FormBody.Builder()
//                            .add("parad", EncryptionTools.getParad(params, mMd5Key, mDesKey))
//                            .build();
//                    newBuilder.method(chain.request().method(), newFormBody);
//                }
//            }
//            if (UserSessionManager.getInstance().hasCookie()) {
//                newBuilder.addHeader("Cookie", "Q=" + UserSessionManager.getInstance().getQString() + "; "
//                        + "T=" + UserSessionManager.getInstance().getTString());
//            }

            Request newRequest = newBuilder.build();
            return chain.proceed(newRequest);
        }
    }
}
