package com.mascot.service.controller.report;

import com.mascot.server.beans.report.SalaryReportItem;

/**
 * Created by Николай on 17.11.2016.
 */
public class SalaryReportRecord {
    public String jobType;
    public Double cost;

    public static SalaryReportRecord build(SalaryReportItem item) {
        SalaryReportRecord record = new SalaryReportRecord();
        record.cost = item.getCost();
        record.jobType = item.getJobType().getName();
        return record;
    }
}
