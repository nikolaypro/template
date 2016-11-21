package com.mascot.server.model;

import javax.persistence.*;

/**
 * Created by Николай on 31.10.2016.
 */
@Entity
@Table(name = "job_subtype_cost")
public class JobSubTypeCost extends IdentifiedDeleted {
    @Column
    private Double cost;

    @ManyToOne(targetEntity = JobSubType.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "job_subtype_id")
    private JobSubType jobSubType;

    @ManyToOne(targetEntity = Product.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return jobSubType + " - " + product + ": " + cost;
    }

    public JobSubType getJobSubType() {
        return jobSubType;
    }

    public void setJobSubType(JobSubType jobSubType) {
        this.jobSubType = jobSubType;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}