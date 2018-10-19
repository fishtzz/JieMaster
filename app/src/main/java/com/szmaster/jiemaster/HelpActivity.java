package com.szmaster.jiemaster;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
