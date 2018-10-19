package com.szmaster.jiemaster.bus;

import java.util.HashSet;
import java.util.Set;

import com.szmaster.jiemaster.db.PreferenceImp;
import com.szmaster.jiemaster.model.ReportData;

/**
 * Created by jiangsiyu on 2018/10/18.
 */

public class ReportBus {

    private static ReportBus instance;
    private static Set<IReportRefresh> mRefreshSet;
    private static Set<IReportUpdate> mUpdateSet;

    private ReportData mData;
    private Object lock = new Object();

    public ReportData getData() {
        return mData;
    }

    public static ReportBus getInstance() {
        if (null == instance) {
            instance = new ReportBus();
            mRefreshSet = new HashSet<>();
            mUpdateSet = new HashSet<>();
        }
        return instance;
    }

    public void updateData(ReportData data) {
        PreferenceImp.cacheReport(data);
        synchronized (lock) {
            this.mData = data;
            for (IReportUpdate update : mUpdateSet) {
                update.onReportUpdate(mData);
            }
        }
    }

    public void updateDataError(String msg) {
        for (IReportUpdate update : mUpdateSet) {
            update.onReportError(msg);
        }
    }

    public void refreshData() {
        for (IReportRefresh refresh : mRefreshSet) {
            refresh.refreshReport();
        }
    }

    public void registerIRefresh(IReportRefresh iReportRefresh) {
        if (null != iReportRefresh) {
            mRefreshSet.add(iReportRefresh);
        }
    }

    public void unregisterIRefresh(IReportRefresh iReportRefresh) {
        if (null != iReportRefresh && mRefreshSet.contains(iReportRefresh)) {
            mRefreshSet.remove(iReportRefresh);
        }
    }

    public void registerIUpdate(IReportUpdate iReportUpdate) {
        if (null != iReportUpdate) {
            mUpdateSet.add(iReportUpdate);
        }
    }

    public void unregisterIUpdate(IReportUpdate iReportUpdate) {
        if (null != iReportUpdate && mUpdateSet.contains(iReportUpdate)) {
            mUpdateSet.remove(iReportUpdate);
        }
    }

}
