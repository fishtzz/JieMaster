package com.szmaster.jiemaster.network;


import com.szmaster.jiemaster.model.ReportModel;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ArdApi {
    @GET("ardapi/report/")
    Observable<ReportModel> getReport();

}
