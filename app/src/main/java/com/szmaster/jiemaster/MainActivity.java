package com.szmaster.jiemaster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.szmaster.jiemaster.model.ReportModel;
import com.szmaster.jiemaster.network.base.ApiManager;
import com.szmaster.jiemaster.utils.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(this);
//        test();
    }

    private void test() {

        ApiManager.getArdApi().getReport()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ReportModel>() {
                    @Override
                    public void onNext(ReportModel reportModel) {
                        Gson gson = new Gson();
                        Log.d(MainActivity.class, "" + gson.toJson(reportModel));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(MainActivity.class, "" + e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        Log.d(MainActivity.class, "onComplete");

                    }
                });
    }

    @Override
    public void onClick(View v) {
        test();
    }

    private long stampQuit;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - stampQuit > 1000) {
            stampQuit = System.currentTimeMillis();
            Toast.makeText(this, getString(R.string.quit), Toast.LENGTH_SHORT).show();
            return;
        } else {
            super.onBackPressed();
        }
    }
}
