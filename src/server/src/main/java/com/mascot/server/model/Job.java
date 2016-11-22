package com.mascot.server.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Nikolay on 07.11.2016.
 */
@Entity
@Table(name = "job")
public class Job extends Identified {
    @Column(name = "complete_date")
    private Date completeDate;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "number")
    private String number;

    @ManyToOne(targetEntity = JobType.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "job_type_id")
    private JobType jobType;

    @ManyToOne(targetEntity = Product.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public Job() {
        creationDate = new Date();
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date date) {
        this.completeDate = date;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
