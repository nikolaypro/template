package com.mascot.service.controller.order;

import com.mascot.server.model.OrderClothLine;
import com.mascot.server.model.OrderLine;

/**
 * Created by Николай on 27.02.2017.
 */
public class OrderLineBaseRecord {
    public Long id;
    public Integer count;
    public Double cost;

    protected void fill(OrderLine line) {
        this.id = line.getId();
        this.count = line.getCount();
        this.cost = line.getCost();
    }

}
