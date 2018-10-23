package com.szmaster.jiemaster.network;


import java.util.HashMap;

import com.szmaster.jiemaster.model.CheckVersionModel;
import com.szmaster.jiemaster.model.ReportModel;
import com.szmaster.jiemaster.model.ReviseUserImgModel;
import com.szmaster.jiemaster.model.ReviseUsernameModel;
import com.szmaster.jiemaster.model.SmsVerificationModel;
import com.szmaster.jiemaster.model.UserModel;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface ArdApi {

    /**
     * @param brand   手机品牌
     * @param model   手机型号
     * @param version 操作系统版本
     * @return ReportModel
     */

    @FormUrlEncoded
    @POST("ardapi/report/")
    Observable<ReportModel> getReport(@Field("brand") String brand, @Field("model") String model, @Field("osVersion") String version);

    /**
     * @param mobile 手机号
     * @param code   验证码
     * @return UserModel
     */
    @FormUrlEncoded
    @POST("ardapi/login/")
    Observable<UserModel> login(@Field("mobile") String mobile, @Field("code") String code);

    /**
     * @param mobile 手机号
     * @return SmsVerificationModel
     */
    @FormUrlEncoded
    @POST("ardapi/send_code/")
    Observable<SmsVerificationModel> getVerificationCode(@Field("mobile") String mobile);

    /**
     * @param userId   userID
     * @param userName userName
     * @param token    token
     * @return ReviseUsernameModel
     */
    @FormUrlEncoded
    @POST("ardapi/update_name/")
    Observable<ReviseUsernameModel> reviseUsername(@Field("userId") String userId, @Field("userName") String userName, @Field("token") String token);

    /**
     * @param params 手动构建请求参数
     * @return ReviseUserImgModel
     */
    @Multipart
    @POST("ardapi/update_img/")
    Observable<ReviseUserImgModel> reviseUserImg(@PartMap HashMap<String, RequestBody> params);


    @POST("ardapi/checkupdate/")
    Observable<CheckVersionModel> checkVersion();
}
