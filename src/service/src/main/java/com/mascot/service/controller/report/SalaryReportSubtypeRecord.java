package com.mascot.service.controller.report;

import com.mascot.server.beans.report.SalarySubTypeItem;

/**
 * Created by Николай on 22.01.2017.
 */
public class SalaryReportSubtypeRecord {
    public String subtype;
    public Double cost;

    public static SalaryReportSubtypeRecord build(SalarySubTypeItem item) {
        SalaryReportSubtypeRecord result = new SalaryReportSubtypeRecord();
        result.subtype = item.getSubType().getName();
        result.cost = item.getCost();
        return result;
    }

}
