package com.mascot.server.beans.report;

import com.mascot.server.model.JobSubType;

/**
 * Created by Николай on 22.01.2017.
 */
public class SalarySubTypeItem {
    private final JobSubType subType;
    private final Double cost;

    public SalarySubTypeItem(JobSubType subType, Double cost) {
        this.subType = subType;
        this.cost = cost;
    }

    public JobSubType getSubType() {
        return subType;
    }

    public Double getCost() {
        return cost;
    }
}
