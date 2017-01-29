package com.mascot.server.beans.report;

import com.mascot.server.model.JobSubType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Николай on 29.01.2017.
 */
public class SalaryReportGroupItem {
    private final List<SalarySubTypeItem> subTypes;
    private Double groupCost;
    private Integer group;
    public SalaryReportGroupItem(Integer group) {
        this.group = group;
        this.groupCost = 0.0;
        this.subTypes = new ArrayList<>();
    }

    void addSubType(JobSubType subType, Double cost) {
        subTypes.add(new SalarySubTypeItem(subType, cost));
        groupCost += cost;
    }

    public List<SalarySubTypeItem> getSubTypes() {
        return subTypes;
    }

    public Double getGroupCost() {
        return groupCost;
    }

    public Integer getGroup() {
        return group;
    }
}
