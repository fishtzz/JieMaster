package com.szmaster.jiemaster.network.base;

import com.szmaster.jiemaster.Constants;
import com.szmaster.jiemaster.network.ArdApi;

/**
 * Created by jiangsiyu on 2018/10/16.
 */

public class ApiManager {

    private static ArdApi mArdApi;

    public static ArdApi getArdApi() {
        if (null == mArdApi) {
            mArdApi = getApi(ArdApi.class);
        }
        return mArdApi;
    }

    private static <T> T getApi(final Class<T> t) {
        return HttpConnector.getServer(Constants.BASE_URL).create(t);
    }
}
