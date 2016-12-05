package com.mascot.server.model;

import javax.persistence.*;

/**
 * Created by Nikolay on 25.10.2016.
 */
@Entity
@Table(name = "job_subtype")
public class JobSubType extends ExternalEntity {
    @Column
    private String name;

    @ManyToOne(targetEntity = JobType.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "job_type_id")
    private JobType jobType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }
}

