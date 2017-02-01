package com.mascot.server.beans.report;

import com.mascot.server.model.Job;
import com.mascot.server.model.JobSubType;

import java.util.List;
import java.util.Map;

/**
 * Created by Николай on 31.01.2017.
 */
public class SalaryInvestigationSubTypeItem {
    private final SalarySubTypeItem subType;
    private final List<SalaryInversigationJobItem> jobs;

    public SalaryInvestigationSubTypeItem(SalarySubTypeItem subType, List<SalaryInversigationJobItem> jobs) {
        this.subType = subType;
        this.jobs = jobs;
    }

    public SalarySubTypeItem getSubType() {
        return subType;
    }

    public List<SalaryInversigationJobItem> getJobs() {
        return jobs;
    }
}
