package com.mascot.service.controller.order.product;

import com.mascot.server.model.Identified;
import com.mascot.server.model.Order;
import com.mascot.server.model.OrderStatus;
import com.mascot.service.controller.order.OrderBaseRecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Николай on 24.02.2017.
 */
public class OrderProductRecord extends OrderBaseRecord<OrderProductLineRecord> {

    public static OrderProductRecord build(Order entity) {
        OrderProductRecord result = new OrderProductRecord();
        result.fill(entity);
        result.lines = entity.getProductLines().stream()
                .sorted(Comparator.comparingLong(Identified::getId))
                .map(OrderProductLineRecord::build)
                .collect(Collectors.toList());
        return result;
    }
}
