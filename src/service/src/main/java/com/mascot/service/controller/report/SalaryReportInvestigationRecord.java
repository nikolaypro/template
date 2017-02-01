package com.mascot.service.controller.report;

import com.mascot.server.beans.report.SalaryInvestigationSubTypeItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Николай on 31.01.2017.
 */
public class SalaryReportInvestigationRecord {
    public SalaryReportSubtypeRecord subType;
    public List<SalaryInvestigationJobRecord> jobs;
    public Integer jobCount;

    public static SalaryReportInvestigationRecord build(SalaryInvestigationSubTypeItem item) {
        SalaryReportInvestigationRecord result = new SalaryReportInvestigationRecord();
        result.subType = SalaryReportSubtypeRecord.build(item.getSubType());
        result.jobs = item.getJobs().stream().map(SalaryInvestigationJobRecord::build).collect(Collectors.toList());
        result.jobCount = result.jobs.stream().mapToInt(e -> e.count).sum();

        return result;
    }

}
