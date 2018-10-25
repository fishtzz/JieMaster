package com.szmaster.jiemaster;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.szmaster.jiemaster.utils.CommonUtil;

/**
 * Created by jiangsiyu on 2018/10/17.
 */

public class BrowserActivity extends AppCompatActivity {

    private String url4Load;
    public static final String KEY_URL = "url";
    private String title;
    public static final String KEY_TITLE = "title";
    private ContentLoadingProgressBar mProgressBar;
    private Handler mHandler = new Handler();
    private static final int ANIM_DELAY = 16;//听说有个东西叫16ms准则
    private int progressCurrent;
    private int progressNew;
    private WebView mWebView;

    private Runnable progressRun = new Runnable() {
        @Override
        public void run() {
            if (progressCurrent == progressNew) {
                mHandler.removeCallbacks(progressRun);
                if (progressCurrent == 100) {
                    progressCurrent = 0;
                    mProgressBar.hide();
                }
            } else {
                if (progressCurrent > progressNew) {
                    progressCurrent = 0;
                }
                mHandler.postDelayed(progressRun, ANIM_DELAY);
                if (!mProgressBar.isShown()) {
                    mProgressBar.show();
                }
                progressCurrent++;
                mProgressBar.setProgress(progressCurrent);
            }

        }
    };

    public static void launcher(Context context, String url, String title) {
        if (TextUtils.isEmpty(url))
            return;
        Intent intent = new Intent();
        intent.putExtra(KEY_URL, url);
        intent.putExtra(KEY_TITLE, title);
        intent.setClass(context, BrowserActivity.class);
        context.startActivity(intent);
    }

    public static void launcher(Fragment fragment, String url, String title) {
        if (TextUtils.isEmpty(url))
            return;
        Intent intent = new Intent();
        intent.putExtra(KEY_URL, url);
        intent.putExtra(KEY_TITLE, title);
        intent.setClass(fragment.getActivity(), BrowserActivity.class);
        fragment.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        Intent intent = getIntent();
        if (null != intent && intent.hasExtra(KEY_URL)) {
            url4Load = intent.getStringExtra(KEY_URL);
            title = intent.getStringExtra(KEY_TITLE);
        } else {
            finish();
        }
        initView();
    }

    private void initView() {

        mWebView = findViewById(R.id.web_view);
        mProgressBar = findViewById(R.id.progress);
        mWebView.requestFocus();
        if (TextUtils.isEmpty(title)) {
            ((TextView) findViewById(R.id.title)).setText(getString(R.string.app_name));
        } else {
            ((TextView) findViewById(R.id.title)).setText(title);
        }

        //设置进度条
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressNew = newProgress;
                if (CommonUtil.isForeground(BrowserActivity.this, BrowserActivity.class.getName())) {
                    mHandler.postDelayed(progressRun, ANIM_DELAY);
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return false;

            }
        });
        mWebView.loadUrl(url4Load);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
