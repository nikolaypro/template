package com.mascot.service.controller.order.cloth;

import com.mascot.server.model.Identified;
import com.mascot.server.model.Order;
import com.mascot.service.controller.dictionary.ClothRecord;
import com.mascot.service.controller.order.OrderBaseRecord;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Created by Николай on 24.02.2017.
 */
public class OrderClothRecord extends OrderBaseRecord<OrderClothLineRecord> {
    public static OrderClothRecord build(Order entity) {
        OrderClothRecord result = new OrderClothRecord();
        result.fill(entity);
        result.lines = entity.getClothLines().stream()
                .sorted(Comparator.comparingLong(Identified::getId))
                .map(OrderClothLineRecord::build)
                .collect(Collectors.toList());
        return result;
    }

}
