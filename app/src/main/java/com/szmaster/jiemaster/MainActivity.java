package com.szmaster.jiemaster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.szmaster.jiemaster.model.ReportModel;
import com.szmaster.jiemaster.network.ArdApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(this);
//        test();
    }

    private void test() {
        Log.d("main","test");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ArdApi ardApi = retrofit.create(ArdApi.class);

        ardApi.getReport()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ReportModel>() {
                    @Override
                    public void onNext(ReportModel reportModel) {
                        Gson gson = new Gson();
                        Log.d("main", "" + gson.toJson(reportModel));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("main", "" + e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        Log.d("main", "onComplete");

                    }
                });
    }

    @Override
    public void onClick(View v) {
        test();
    }
}
