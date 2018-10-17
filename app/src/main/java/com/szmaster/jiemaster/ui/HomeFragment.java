package com.szmaster.jiemaster.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.XBanner.XBannerAdapter;
import com.szmaster.jiemaster.GlideApp;
import com.szmaster.jiemaster.R;

public class HomeFragment extends Fragment {

    private View root;
    private XBanner mXBanner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == root) {
            root = inflater.inflate(R.layout.fragment_home, container, false);
        }
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        mXBanner = root.findViewById(R.id.xbanner);
        final List<String> list = new ArrayList<>();
        list.add("http://www.shiwandashi.com/appicon/aa49cde8-d02c-11e8-a0f7-00163e041d62.png");
        list.add("http://www.shiwandashi.com/appicon/aa49cde8-d02c-11e8-a0f7-00163e041d62.png");
        list.add("http://www.shiwandashi.com/appicon/aa49cde8-d02c-11e8-a0f7-00163e041d62.png");
        mXBanner.setData(list, null);
        mXBanner.loadImage(new XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                GlideApp.with(HomeFragment.this).load(list.get(position)).into((ImageView) view);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mXBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        mXBanner.stopAutoPlay();
    }
}
