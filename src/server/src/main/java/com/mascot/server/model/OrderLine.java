package com.mascot.server.model;

import javax.persistence.*;

/**
 * Created by Николай on 24.02.2017.
 */
@MappedSuperclass
public class OrderLine extends Identified {
    @ManyToOne(targetEntity = Order.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "count")
    private Integer count;

    @Column(name = "cost")
    private Double cost;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
