package com.mascot.service.controller.job_subtype_cost;

import com.mascot.server.model.JobSubType;
import com.mascot.server.model.JobSubTypeCost;
import com.mascot.service.controller.job_subtype.JobSubTypeRecord;
import com.mascot.service.controller.product.ProductRecord;

/**
 * Created by Николай on 31.10.2016.
 */
public class JobSubTypeCostRecord {
    public Long id;
    public JobSubTypeRecord jobSubType;
    public ProductRecord product;
    public Double cost;

    public static JobSubTypeCostRecord build(JobSubTypeCost subTypeCost) {
        JobSubTypeCostRecord result = new JobSubTypeCostRecord();
        result.id = subTypeCost.getId();
        result.jobSubType = JobSubTypeRecord.build(subTypeCost.getJobSubType());
        result.product = ProductRecord.build(subTypeCost.getProduct());
        result.cost = subTypeCost.getCost();
        return result;
    }
}
