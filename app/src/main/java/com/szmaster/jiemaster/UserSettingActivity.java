package com.szmaster.jiemaster;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.szmaster.jiemaster.bus.UserBus;
import com.szmaster.jiemaster.model.User;

/**
 * Created by jiangsiyu on 2018/10/19.
 */

public class UserSettingActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView avatar;
    private View setAvatar;
    private TextView tvNickname;
    private View setNickname;
    private TextView tvMobile;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        user = UserBus.getInstance().getUser();
        if (null == user) {
            finish();
        } else {
            initView();
        }
    }

    private void initView() {
        avatar = findViewById(R.id.avatar);
        GlideApp.with(this).load(user.getUserImg()).into(avatar);
        setAvatar = findViewById(R.id.set_avatar);
        setAvatar.setOnClickListener(this);
        tvNickname = findViewById(R.id.tv_nickname);
        tvNickname.setText(user.getUserName());
        setNickname = findViewById(R.id.set_nickname);
        setNickname.setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        tvMobile = findViewById(R.id.tv_mobile);
        tvMobile.setText(user.getMobile());
        findViewById(R.id.btn_logout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.set_avatar:
                break;
            case R.id.set_nickname:
                break;

            case R.id.btn_logout:
                UserBus.getInstance().logout();
                finish();
                break;
            default:
                break;
        }

    }
}
