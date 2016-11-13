package com.mascot.service.controller.common;

import java.util.Map;

/**
 * Created by Nikolay on 05.04.2016.
 */
public class TableParams {
    public int page;
    public int count;
    public Map<String, String> orderBy;
    public Map<String, String> filter;
    public boolean isOrderAsc;

    public TableParams() {
//        System.out.println();
    }

    public int getStartIndex() {
        return (page - 1) * count;
    }
}
