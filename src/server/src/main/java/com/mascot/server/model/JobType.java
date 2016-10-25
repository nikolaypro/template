package com.mascot.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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

    @Override
    public String toString() {
        return name;
    }

}
