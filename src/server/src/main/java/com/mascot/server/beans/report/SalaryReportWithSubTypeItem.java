package com.mascot.server.beans.report;

import com.mascot.server.model.JobType;

import java.util.List;

/**
 * Created by Николай on 22.01.2017.
 */
public class SalaryReportWithSubTypeItem extends SalaryReportItem {
    private final List<SalarySubTypeItem> subTypes;
    public SalaryReportWithSubTypeItem(JobType jobType, Double cost, List<SalarySubTypeItem> subTypes) {
        super(jobType, cost);
        this.subTypes = subTypes;
    }

    public List<SalarySubTypeItem> getSubTypes() {
        return subTypes;
    }
}
