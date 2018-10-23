package com.szmaster.jiemaster;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.szmaster.jiemaster.bus.IReportRefresh;
import com.szmaster.jiemaster.bus.IUser;
import com.szmaster.jiemaster.bus.ReportBus;
import com.szmaster.jiemaster.bus.UserBus;
import com.szmaster.jiemaster.db.PreferenceImp;
import com.szmaster.jiemaster.model.CheckVersionModel;
import com.szmaster.jiemaster.model.ReportModel;
import com.szmaster.jiemaster.model.User;
import com.szmaster.jiemaster.model.VersionInfo;
import com.szmaster.jiemaster.network.base.ApiManager;
import com.szmaster.jiemaster.ui.ActivityFragment;
import com.szmaster.jiemaster.ui.HomeFragment;
import com.szmaster.jiemaster.ui.UserFragment;
import com.szmaster.jiemaster.utils.CommonUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, IReportRefresh, IUser {

    private RadioGroup mRadioGroup;
    private HomeFragment homeFragment;
    private ActivityFragment activityFragment;
    private UserFragment userFragment;

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

        checkVersion();
    }

    private void checkVersion() {
        ApiManager.getArdApi().checkVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<CheckVersionModel>() {
                    @Override
                    public void onNext(CheckVersionModel checkVersionModel) {
                        if (200 == checkVersionModel.getCode() && null != checkVersionModel.getData()) {
                            if (checkVersionModel.getData().getVersionName().compareTo(CommonUtil.getVersionName()) > 0) {
                                showUpdateDialog(checkVersionModel.getData());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
                if (null == homeFragment) {
                    homeFragment = new HomeFragment();
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_frame, homeFragment);
                transaction.commit();
                break;
            case R.id.btn_activity:
                if (null == activityFragment) {
                    activityFragment = new ActivityFragment();
                }
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                transaction2.replace(R.id.fragment_frame, activityFragment);
                transaction2.commit();
                break;
            case R.id.btn_user:
                if (null == userFragment) {
                    userFragment = new UserFragment();
                }
                FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                transaction3.replace(R.id.fragment_frame, userFragment);
                transaction3.commit();
                break;
            default:
                break;
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
                        if (200 == reportModel.getCode() && null != reportModel.getData()) {
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
        finish();
    }

    private void showUpdateDialog(final VersionInfo versionInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);

        builder.setTitle(R.string.update_available)
                .setMessage(versionInfo.getUpdateDesc())
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.update_now, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(versionInfo.getUrl());
                        intent.setData(content_url);
                        startActivity(intent);
                    }
                })
                .show();
    }
}
