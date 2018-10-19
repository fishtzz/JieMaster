package com.szmaster.jiemaster;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.szmaster.jiemaster.utils.CommonUtil;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        ((TextView) findViewById(R.id.tv_version)).setText(getString(R.string.version_name, CommonUtil.getVersionName()));
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
