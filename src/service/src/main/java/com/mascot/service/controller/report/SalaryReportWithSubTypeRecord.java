package com.mascot.service.controller.report;

import com.mascot.server.beans.report.SalaryReportWithSubTypeItem;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Николай on 22.01.2017.
 */
public class SalaryReportWithSubTypeRecord extends SalaryReportRecord {
    public List<SalaryReportSubtypeRecord> subTypes;

    public static SalaryReportWithSubTypeRecord buildWithSubTypes(SalaryReportWithSubTypeItem item) {
        SalaryReportWithSubTypeRecord record = new SalaryReportWithSubTypeRecord();
        record.cost = item.getCost();
        record.jobType = item.getJobType().getName();
        record.subTypes = item.getSubTypes().stream().map(SalaryReportSubtypeRecord::build).collect(Collectors.toList());
        return record;
    }

}
