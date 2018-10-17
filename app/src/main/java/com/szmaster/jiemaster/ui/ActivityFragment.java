package com.szmaster.jiemaster.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.szmaster.jiemaster.R;
import com.szmaster.jiemaster.model.ReportData;
import com.szmaster.jiemaster.model.ReportModel;
import com.szmaster.jiemaster.network.base.ApiManager;
import com.szmaster.jiemaster.utils.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ActivityFragment extends Fragment {
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
        ApiManager.getArdApi().getReport()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ReportModel>() {
                    @Override
                    public void onNext(ReportModel reportModel) {
                        if (reportModel.getCode() == 200 && null != reportModel.getData()) {
                            refreshView(reportModel.getData());
                        } else {
                            Toast.makeText(getActivity(), reportModel.getMessage(), Toast.LENGTH_LONG).show();
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

    private void refreshView(ReportData data) {
        mAdapter.updateItems(data.getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);
    }

}
