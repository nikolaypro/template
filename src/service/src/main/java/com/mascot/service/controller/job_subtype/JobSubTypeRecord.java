package com.mascot.service.controller.job_subtype;


import com.mascot.server.model.JobSubType;
import com.mascot.service.controller.job_type.JobTypeRecord;

/**
 * Created by Nikolay on 25.10.2016.
 */
public class JobSubTypeRecord {
    public Long id;
    public String name;
    public JobTypeRecord jobType;

    public static JobSubTypeRecord build(JobSubType subType) {
        JobSubTypeRecord result = new JobSubTypeRecord();
        result.id = subType.getId();
        result.name = subType.getName();
        result.jobType = JobTypeRecord.build(subType.getJobType());
        return result;
    }


}
