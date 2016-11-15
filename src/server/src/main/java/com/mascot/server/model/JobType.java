package com.mascot.server.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Nikolay on 24.10.2016.
 */
@Entity
@Table(name = "job_type")
public class JobType extends Identified {
    @Column
    private String name;

    @Column(name = "order_number")

    private Integer order;

    @OneToMany(targetEntity = JobSubType.class, fetch = FetchType.LAZY, mappedBy = "jobType")
    private Set<JobSubType> jobSubTypes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Set<JobSubType> getJobSubTypes() {
        return jobSubTypes;
    }

    public void setJobSubTypes(Set<JobSubType> jobSubTypes) {
        this.jobSubTypes = jobSubTypes;
    }

    @Override
    public String toString() {
        return name;
    }

}
