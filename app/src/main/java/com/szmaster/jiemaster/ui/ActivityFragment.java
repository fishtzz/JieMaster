package com.szmaster.jiemaster.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.szmaster.jiemaster.R;
import com.szmaster.jiemaster.bus.IReportUpdate;
import com.szmaster.jiemaster.bus.ReportBus;
import com.szmaster.jiemaster.model.ReportData;

public class ActivityFragment extends Fragment implements IReportUpdate {

    private View root;
    private RecyclerView recyclerView;
    private ListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == root) {
            root = inflater.inflate(R.layout.fragment_activity, container, false);
        }
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        recyclerView = root.findViewById(R.id.recycler);
        mAdapter = new ListAdapter(getActivity());
        ReportBus.getInstance().registerIUpdate(this);
        if (null != ReportBus.getInstance().getData()) {
            refreshView(ReportBus.getInstance().getData());
        } else {
            ReportBus.getInstance().refreshData();
        }
    }

    @Override
    public void onDestroy() {
        ReportBus.getInstance().unregisterIUpdate(this);
        super.onDestroy();
    }

    private void refreshView(ReportData data) {
        mAdapter.updateItems(data.getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);
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
