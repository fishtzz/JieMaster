package com.szmaster.jiemaster.bus;

import com.szmaster.jiemaster.model.ReportData;

/**
 * Created by jiangsiyu on 2018/10/18.
 */

public interface IReportUpdate {

    void onReportUpdate(ReportData data);

    void onReportError(String msg);

}
