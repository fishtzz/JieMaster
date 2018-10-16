package com.szmaster.jiemaster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.szmaster.jiemaster.model.ReportModel;
import com.szmaster.jiemaster.network.base.ApiManager;
import com.szmaster.jiemaster.utils.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        findViewById(R.id.btn).setOnClickListener(this);

    }

    private void initView() {
        mRadioGroup = findViewById(R.id.radio_group);
        mRadioGroup.setOnCheckedChangeListener(this);
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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.btn_home:
                break;
            case R.id.btn_activity:
                break;
            case R.id.btn_user:
                break;
            default:
                break;
        }
    }
}
