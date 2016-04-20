package com.mascot.service.controller.common;

import java.util.List;

/**
 * Created by Nikolay on 20.04.2016.
 */
public class TableResult<A> {
    public A[] list;
    public int total;

    public static <A> TableResult<A> create(A[] list, int total) {
        final TableResult<A> result = new TableResult<>();
        result.list = list;
        result.total = total;
        return result;
    }
}
