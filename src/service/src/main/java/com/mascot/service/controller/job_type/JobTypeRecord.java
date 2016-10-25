package com.mascot.service.controller.job_type;

import com.mascot.server.model.JobType;

/**
 * Created by Nikolay on 24.10.2016.
 */
public class JobTypeRecord {
    public Long id;
    public String name;
    public Integer order;

    public static JobTypeRecord build(JobType product) {
        JobTypeRecord result = new JobTypeRecord();
        result.id = product.getId();
        result.name = product.getName();
        result.order = product.getOrder();
        return result;
    }

}
