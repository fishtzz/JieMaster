package com.szmaster.jiemaster.network;


import com.szmaster.jiemaster.model.ReportModel;

import com.szmaster.jiemaster.model.SmsVerificationModel;
import com.szmaster.jiemaster.model.UserModel;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ArdApi {

    @POST("ardapi/report/")
    Observable<ReportModel> getReport();

    @FormUrlEncoded
    @POST("ardapi/login/")
    Observable<UserModel> login(@Field("mobile") String mobile, @Field("code") String code);

    @FormUrlEncoded
    @POST("ardapi/send_code/")
    Observable<SmsVerificationModel> getVerificationCode(@Field("mobile") String mobile);
}
