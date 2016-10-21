package com.mascot.service.controller.product;

import com.mascot.server.model.Product;

/**
 * Created by Nikolay on 19.10.2016.
 */

public class ProductRecord {
    public Long id;
    public String name;

    public static ProductRecord build(Product product) {
        ProductRecord result = new ProductRecord();
        result.id = product.getId();
        result.name = product.getName();
        return result;
    }
}
