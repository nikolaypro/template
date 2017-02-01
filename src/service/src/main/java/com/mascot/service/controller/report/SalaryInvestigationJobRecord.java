package com.mascot.service.controller.report;

import com.mascot.server.beans.report.SalaryInversigationJobItem;

/**
 * Created by Николай on 31.01.2017.
 */
public class SalaryInvestigationJobRecord {
    public String product;
    public Double subTypeCost;
    public Integer count;

    public static SalaryInvestigationJobRecord build(SalaryInversigationJobItem item) {
        SalaryInvestigationJobRecord result = new SalaryInvestigationJobRecord();
        result.product = item.getProduct().getName();
        result.count = item.getCount();
        result.subTypeCost = item.getSubTypeCost();
        return result;
    }

}
