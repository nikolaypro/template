package com.mascot.server.beans;

import com.mascot.server.common.BeanTableResult;
import com.mascot.server.model.Product;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Nikolay on 19.10.2016.
 */
public interface ProductService {
    String NAME = "ProductService";
    BeanTableResult<Product> getList(int start, int count, Map<String, String> orderBy);

    void update(Product entity);

    boolean remove(Long id);

    Product find(Long id);

    Product findByName(String name);

    List<Product> getAll();
}
