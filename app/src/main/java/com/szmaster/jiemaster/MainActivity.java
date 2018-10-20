package com.szmaster.jiemaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.szmaster.jiemaster.bus.IReportRefresh;
import com.szmaster.jiemaster.bus.IUser;
import com.szmaster.jiemaster.bus.ReportBus;
import com.szmaster.jiemaster.bus.UserBus;
import com.szmaster.jiemaster.db.PreferenceImp;
import com.szmaster.jiemaster.model.ReportModel;
import com.szmaster.jiemaster.model.User;
import com.szmaster.jiemaster.network.base.ApiManager;
import com.szmaster.jiemaster.ui.ActivityFragment;
import com.szmaster.jiemaster.ui.HomeFragment;
import com.szmaster.jiemaster.ui.UserFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, IReportRefresh, IUser {

    private RadioGroup mRadioGroup;
    private HomeFragment homeFragment;
    private ActivityFragment activityFragment;
    private UserFragment userFragment;
    private static final int REQUEST_CODE_LOGIN = 10086;
    public static final int REQUEST_CODE_SETTING = 10087;
    private int currentCheckedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ReportBus.getInstance().registerIRefresh(this);
        UserBus.getInstance().registerIUser(this);
        initView();
        if (null != PreferenceImp.getReportCache()) {
            ReportBus.getInstance().updateData(PreferenceImp.getReportCache());
        }
        refreshReport();
    }

    private void initView() {
        mRadioGroup = findViewById(R.id.radio_group);
        mRadioGroup.setOnCheckedChangeListener(this);
        if (null == homeFragment) {
            homeFragment = new HomeFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_frame, homeFragment);
        transaction.commit();
        currentCheckedId = R.id.btn_home;
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
                currentCheckedId = R.id.btn_home;
                if (null == homeFragment) {
                    homeFragment = new HomeFragment();
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_frame, homeFragment);
                transaction.commit();
                break;
            case R.id.btn_activity:
                currentCheckedId = R.id.btn_activity;
                if (null == activityFragment) {
                    activityFragment = new ActivityFragment();
                }
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                transaction2.replace(R.id.fragment_frame, activityFragment);
                transaction2.commit();
//                startActivity(new Intent(this, UserSettingActivity.class));
//                startActivity(new Intent(this, HelpActivity.class));
//                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.btn_user:
                if (UserBus.getInstance().isLogin()) {
                    if (null == userFragment) {
                        userFragment = new UserFragment();
                    }
                    FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                    transaction3.replace(R.id.fragment_frame, userFragment);
                    transaction3.commit();
                    currentCheckedId = R.id.btn_user;
                } else {
                    startActivityForResult(new Intent(MainActivity.this
                            , RegisterOrLoginActivity.class), REQUEST_CODE_LOGIN);
                    ((RadioButton) findViewById(currentCheckedId)).setChecked(true);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LOGIN
                && resultCode == Activity.RESULT_OK
                && UserBus.getInstance().isLogin()) {
            ((RadioButton) findViewById(R.id.btn_user)).setChecked(true);
        } else if (requestCode == REQUEST_CODE_SETTING
                && !UserBus.getInstance().isLogin()) {
            currentCheckedId = R.id.btn_home;
            ((RadioButton) findViewById(R.id.btn_home)).setChecked(true);
        }
    }

    @Override
    protected void onDestroy() {
        ReportBus.getInstance().unregisterIRefresh(this);
        UserBus.getInstance().unregisterIUser(this);
        super.onDestroy();
    }

    @Override
    public void refreshReport() {

        ApiManager.getArdApi().getReport(Build.BRAND, Build.MODEL, Build.VERSION.RELEASE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ReportModel>() {
                    @Override
                    public void onNext(ReportModel reportModel) {
                        if (reportModel.getCode() == 200 && null != reportModel.getData()) {
                            ReportBus.getInstance().updateData(reportModel.getData());
                        } else {
                            ReportBus.getInstance().updateDataError(reportModel.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ReportBus.getInstance().updateDataError(e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onLogin(User user) {

    }

    @Override
    public void onLogout() {
    }
}
