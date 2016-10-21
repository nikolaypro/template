package com.mascot.server.common;

import java.util.List;

/**
 * Created by Nikolay on 19.10.2016.
 */
public class BeanTableResult <A> {
    private List<A> rows;
    private int count;

    public BeanTableResult(List<A> rows, int count) {
        this.rows = rows;
        this.count = count;
    }

    public List<A> getRows() {
        return rows;
    }

    public int getCount() {
        return count;
    }
}
