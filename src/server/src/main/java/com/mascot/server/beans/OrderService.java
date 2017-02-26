package com.mascot.server.beans;

import com.mascot.server.model.Order;

/**
 * Created by Николай on 25.02.2017.
 */
public interface OrderService {
    String NAME = "OrderService";

    Order find(Long id);

    void update(Order entity);
}
