package com.szmaster.jiemaster.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.XBanner.XBannerAdapter;
import com.szmaster.jiemaster.GlideApp;
import com.szmaster.jiemaster.R;
import com.szmaster.jiemaster.bus.IReportUpdate;
import com.szmaster.jiemaster.bus.ReportBus;
import com.szmaster.jiemaster.model.ReportBanner;
import com.szmaster.jiemaster.model.ReportData;
import com.szmaster.jiemaster.model.ReportModel;
import com.szmaster.jiemaster.network.base.ApiManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment implements IReportUpdate {

    private View root;
    private XBanner mXBanner;
    private RecyclerView recyclerView;
    private GridAdapter mAdapter;

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
        recyclerView = root.findViewById(R.id.recycler);
        mAdapter = new GridAdapter(getActivity());
        ReportBus.getInstance().registerIUpdate(this);
        if (null != ReportBus.getInstance().getData()) {
            refreshView(ReportBus.getInstance().getData());
        }

    }

    private void refreshView(ReportData data) {

        final List<String> list = new ArrayList<>();
        for (ReportBanner banner : data.getBanners()) {
            list.add(banner.getImg());
        }
        mXBanner.setData(list, null);
        mXBanner.loadImage(new XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                GlideApp.with(HomeFragment.this).load(list.get(position)).into((ImageView) view);
            }
        });
        mAdapter.updateItems(data.getItems());
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);
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

    @Override
    public void onDestroy() {
        ReportBus.getInstance().unregisterIUpdate(this);
        super.onDestroy();
    }

    @Override
    public void onReportUpdate(ReportData data) {
        refreshView(data);
    }

    @Override
    public void onReportError(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
        }
    }
}
