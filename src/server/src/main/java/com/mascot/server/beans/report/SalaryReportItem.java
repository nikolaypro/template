package com.mascot.server.beans.report;

import com.mascot.server.model.JobType;

/**
 * Created by Nikolay on 15.11.2016.
 */
public class SalaryReportItem {
    private final JobType jobType;
    private final Double cost;

    public SalaryReportItem(JobType jobType, Double cost) {
        this.jobType = jobType;
        this.cost = cost;
    }

    public JobType getJobType() {
        return jobType;
    }

    public Double getCost() {
        return cost;
    }

}
