package com.mascot.service.controller.report;

import com.mascot.server.beans.report.SalaryReportGroupItem;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Николай on 29.01.2017.
 */
public class SalaryReportGroupedRecord {
    public List<SalaryReportSubtypeRecord> subTypes;
    public Double groupCost;

    public static SalaryReportGroupedRecord build(SalaryReportGroupItem item) {
        SalaryReportGroupedRecord record = new SalaryReportGroupedRecord();
        record.groupCost = item.getGroupCost();
        record.subTypes = item.getSubTypes().stream().map(SalaryReportSubtypeRecord::build).collect(Collectors.toList());
        return record;
    }

}
