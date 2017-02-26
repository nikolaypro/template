package com.mascot.service.controller.order.product;

import com.mascot.server.model.Identified;
import com.mascot.server.model.Order;
import com.mascot.server.model.OrderStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Николай on 24.02.2017.
 */
public class OrderProductRecord {
    public Long id;
//    public Boolean sent;
    public Boolean send;
    public Double cost;
    public List<OrderProductLineRecord> lines;

    public static OrderProductRecord build(Order entity) {
        OrderProductRecord result = new OrderProductRecord();
        result.id = entity.getId();
        result.cost = entity.getCost();
        result.send = Arrays.asList(OrderStatus.SEND, OrderStatus.SENT).contains(entity.getStatus());
        result.lines = entity.getProductLines().stream()
                .sorted(Comparator.comparingLong(Identified::getId))
                .map(OrderProductLineRecord::build)
                .collect(Collectors.toList());
        return result;
    }
}
