package com.mascot.server.beans;

import com.mascot.server.common.BeanTableResult;
import com.mascot.server.model.Order;

import java.util.Map;

/**
 * Created by Николай on 25.02.2017.
 */
public interface OrderService {
    String NAME = "OrderService";

    Order find(Long id);

    void update(Order entity);

    BeanTableResult<Order> getList(int startIndex, int count, Map<String, String> orderBy, Map<String, String> filter);
}
