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

    @Column(name = "use_in_salary_report")
    private Boolean useInSalaryReport = true;

    @Column(name = "report_group")
    private Integer reportGroup;

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

    public Boolean getUseInSalaryReport() {
        return useInSalaryReport;
    }

    public void setUseInSalaryReport(Boolean useInSalaryReport) {
        this.useInSalaryReport = useInSalaryReport;
    }

    public Integer getReportGroup() {
        return reportGroup;
    }

    public void setReportGroup(Integer reportGroup) {
        this.reportGroup = reportGroup;
    }
}

