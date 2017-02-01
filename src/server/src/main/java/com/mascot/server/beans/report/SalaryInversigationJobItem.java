package com.mascot.server.beans.report;

import com.mascot.server.model.Job;
import com.mascot.server.model.Product;

/**
 * Created by Николай on 31.01.2017.
 */
public class SalaryInversigationJobItem {
    private final Product product;
    private final double subTypeCost;
    private int count;

    public SalaryInversigationJobItem(Product product, double subTypeCost, int count) {
        this.product = product;
        this.subTypeCost = subTypeCost;
        this.count = count;
    }

    public void incCount() {
        count++;
    }

    public Product getProduct() {
        return product;
    }

    public double getSubTypeCost() {
        return subTypeCost;
    }

    public int getCount() {
        return count;
    }
}
