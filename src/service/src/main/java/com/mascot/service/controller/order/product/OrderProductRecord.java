package com.mascot.service.controller.order.product;

import java.util.List;

/**
 * Created by Николай on 24.02.2017.
 */
public class OrderProductRecord {
    public Long id;
    public Boolean sent;
    private List<OrderProductLineRecord> lines;
}
