package com.szmaster.jiemaster;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.szmaster.jiemaster.bus.IUser;
import com.szmaster.jiemaster.bus.UserBus;
import com.szmaster.jiemaster.model.SmsVerificationModel;
import com.szmaster.jiemaster.model.User;
import com.szmaster.jiemaster.model.UserModel;
import com.szmaster.jiemaster.network.base.ApiManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RegisterOrLoginActivity extends AppCompatActivity implements IUser, View.OnClickListener {

    private Button btnGetVerification;
    private Button btnSubmit;
    private EditText editMobile;
    private EditText editVerification;
    private String mobile;
    private String verification;
    private int countdown;
    private static final int MSG_COUNTDOWN = 10086;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_COUNTDOWN:
                    if (countdown > 0) {
                        countdown--;
                        btnGetVerification.setText(getString(R.string.count_down, countdown));
                        btnGetVerification.setTextColor(getResources().getColor(R.color.text_grey));
                        sendEmptyMessageDelayed(MSG_COUNTDOWN, 1000);
                    } else {
                        countdown = 60;
                        btnGetVerification.setEnabled(true);
                        btnGetVerification.setText(R.string.get_verification);
                        btnGetVerification.setTextColor(getResources().getColor(R.color.colorMain));
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);
        UserBus.getInstance().registerIUser(this);
        countdown = 60;
        initView();
    }

    private void initView() {
        btnGetVerification = findViewById(R.id.btn_get_verification);
        btnGetVerification.setOnClickListener(this);
        btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);
        editMobile = findViewById(R.id.edit_mobile);
        editVerification = findViewById(R.id.edit_verification);
    }

    @Override
    protected void onDestroy() {
        UserBus.getInstance().unregisterIUser(this);
        super.onDestroy();
    }

    @Override
    public void onLogin(User user) {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void onLogout() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_get_verification) {
            mobile = editMobile.getText().toString();
            if (TextUtils.isEmpty(mobile)) {
                Toast.makeText(this, getString(R.string.input_mobile), Toast.LENGTH_SHORT).show();
            } else {
                btnGetVerification.setEnabled(false);
                handler.sendEmptyMessage(MSG_COUNTDOWN);
                getVerification(mobile);
            }
        } else if (v.getId() == R.id.btn_submit) {
            mobile = editMobile.getText().toString();
            verification = editVerification.getText().toString();
            if (TextUtils.isEmpty(mobile)) {
                Toast.makeText(this, getString(R.string.input_mobile), Toast.LENGTH_SHORT).show();

            } else if (TextUtils.isEmpty(verification)) {
                Toast.makeText(this, getString(R.string.input_verification), Toast.LENGTH_SHORT).show();
            } else {
                login(mobile, verification);
            }

        }
    }

    private void getVerification(String mobile) {
        ApiManager.getArdApi().getVerificationCode(mobile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<SmsVerificationModel>() {
                    @Override
                    public void onNext(SmsVerificationModel smsVerificationModel) {
                        if (smsVerificationModel.getCode() != 200) {
                            Toast.makeText(RegisterOrLoginActivity.this, smsVerificationModel.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(RegisterOrLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void login(String mobile, String verification) {
        ApiManager.getArdApi().login(mobile, verification)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UserModel>() {
                    @Override
                    public void onNext(UserModel userModel) {
                        if (userModel.getCode() != 200) {
                            Toast.makeText(RegisterOrLoginActivity.this, userModel.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            UserBus.getInstance().login(userModel.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(RegisterOrLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
