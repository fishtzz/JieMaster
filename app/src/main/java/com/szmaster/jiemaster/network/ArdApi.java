package com.szmaster.jiemaster.network;


import com.szmaster.jiemaster.model.ReportModel;

import com.szmaster.jiemaster.model.SmsVerificationModel;
import com.szmaster.jiemaster.model.UserModel;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ArdApi {

    @POST("ardapi/report/")
    Observable<ReportModel> getReport();

    @POST("ardapi/login/")
    Observable<UserModel> login(@Query("mobile") String mobile, @Query("code") String code);

    @POST("ardapi/send_code/")
    Observable<SmsVerificationModel> getVerificationCode(@Query("mobile") String mobile);
}
