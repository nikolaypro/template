package com.mascot.service.controller.order;

import com.mascot.server.beans.OrderService;
import com.mascot.server.model.Order;
import com.mascot.server.model.OrderStatus;
import com.mascot.server.model.User;

import java.util.Date;

/**
 * Created by Николай on 28.02.2017.
 */
public class OrderEntityProvider {
    public static Order getAndFillEntity(OrderBaseRecord record, User user, OrderService orderService) {
        final Order entity;
        if (record.id == null) {
            entity = new Order();
            entity.setCreationDate(new Date());
        } else {
            final Order existsEntity = orderService.find(record.id);
            if (existsEntity == null) {
                throw new IllegalStateException("Unable find order with id = " + record.id);
//                return ResultRecord.fail("Unable find order with id = " + record.id);
            }
            entity = existsEntity;
            if (!OrderStatus.ON_HOLD.equals(existsEntity.getStatus())) {
                // todo сделать правильно
                throw new IllegalStateException("Unable change entity in statue: " + existsEntity.getStatus());
            }
            entity.setModifyDate(new Date());
        }
        entity.setUser(user);
        entity.setCost(record.cost);
        entity.setStatus(record.send ? OrderStatus.SEND : OrderStatus.ON_HOLD);
        return entity;
    }
}
