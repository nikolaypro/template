package com.mascot.service.controller.order;

import com.mascot.server.model.Order;

import java.util.Date;

/**
 * Created by Николай on 26.02.2017.
 */
public class OrderTableRecord {
    public Long id;
    public Date creationDate;
    public Date modifyDate;
    public Double cost;
    public Boolean send;
    public String type;

    public static OrderTableRecord build(Order order) {
        final OrderTableRecord result = new OrderTableRecord();
        result.id = order.getId();
        result.creationDate = order.getCreationDate();
        result.modifyDate = order.getModifyDate();
        result.cost = order.getCost();
        result.send = order.isSend();
        result.type = order.getType().name();

        return result;
    }
}
