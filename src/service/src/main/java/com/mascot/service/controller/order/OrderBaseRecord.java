package com.mascot.service.controller.order;

import com.mascot.server.model.Order;
import com.mascot.service.controller.order.product.OrderProductLineRecord;

import java.util.List;

/**
 * Created by Николай on 27.02.2017.
 */
public abstract class OrderBaseRecord<A> {
    public Long id;
    public Boolean send;
    public Double cost;
    public List<A> lines;

    protected void fill(Order entity) {
        this.id = entity.getId();
        this.cost = entity.getCost();
        this.send = entity.isSend();

    }
}
