package com.mascot.service.controller.job;

import com.mascot.server.model.Job;
import com.mascot.service.controller.job_type.JobTypeRecord;
import com.mascot.service.controller.product.ProductRecord;

import java.util.Date;

/**
 * Created by Nikolay on 08.11.2016.
 */
public class JobRecord {
    public Long id;
    public JobTypeRecord jobType;
    public ProductRecord product;
    public Integer number;
    public Date completeDate;
    public Boolean tail;
    public Boolean forNextWeekTail;
    public Integer count;

    public static JobRecord build(Job job) {
        JobRecord result = new JobRecord();
        result.id = job.getId();
        result.jobType = JobTypeRecord.build(job.getJobType());
        result.product = ProductRecord.build(job.getProduct());
        result.number = job.getNumber();
        result.completeDate = job.getCompleteDate();
        return result;
    }

}
