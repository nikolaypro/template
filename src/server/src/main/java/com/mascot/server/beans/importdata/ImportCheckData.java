package com.mascot.server.beans.importdata;

import com.mascot.server.model.JobSubType;
import com.mascot.server.model.JobSubTypeCost;
import com.mascot.server.model.JobType;
import com.mascot.server.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Николай on 02.12.2016.
 */
public class ImportCheckData {
    private List<Product> newProducts;
    private List<Product> changedProducts;
    private List<Product> removedProducts;
    private List<JobType> newJobTypes;
    private List<JobType> changedJobTypes;
    private List<JobType> removedJobTypes;
    private List<JobSubType> newJobSubTypes;
    private List<JobSubType> changedJobSubTypes;
    private List<JobSubType> removedJobSubTypes;
    private List<JobSubTypeCost> newCosts;
    private List<JobSubTypeCost> changedCosts;
    private List<JobSubTypeCost> removedCosts;

    public List<Product> getNewProducts() {
        return newProducts;
    }

    public void setNewProducts(List<Product> newProducts) {
        this.newProducts = newProducts;
    }

    public List<JobType> getNewJobTypes() {
        return newJobTypes;
    }

    public void setNewJobTypes(List<JobType> newJobTypes) {
        this.newJobTypes = newJobTypes;
    }

    public List<JobSubType> getNewJobSubTypes() {
        return newJobSubTypes;
    }

    public void setNewJobSubTypes(List<JobSubType> newJobSubTypes) {
        this.newJobSubTypes = newJobSubTypes;
    }

    public List<JobSubTypeCost> getNewCosts() {
        return newCosts;
    }

    public void setNewCosts(List<JobSubTypeCost> newCosts) {
        this.newCosts = newCosts;
    }

    public List<Product> getChangedProducts() {
        return changedProducts;
    }

    public void setChangedProducts(List<Product> changedProducts) {
        this.changedProducts = changedProducts;
    }

    public List<Product> getRemovedProducts() {
        return removedProducts;
    }

    public void setRemovedProducts(List<Product> removedProducts) {
        this.removedProducts = removedProducts;
    }

    public List<JobType> getChangedJobTypes() {
        return changedJobTypes;
    }

    public void setChangedJobTypes(List<JobType> changedJobTypes) {
        this.changedJobTypes = changedJobTypes;
    }

    public List<JobType> getRemovedJobTypes() {
        return removedJobTypes;
    }

    public void setRemovedJobTypes(List<JobType> removedJobTypes) {
        this.removedJobTypes = removedJobTypes;
    }

    public List<JobSubType> getChangedJobSubTypes() {
        return changedJobSubTypes;
    }

    public void setChangedJobSubTypes(List<JobSubType> changedJobSubTypes) {
        this.changedJobSubTypes = changedJobSubTypes;
    }

    public List<JobSubType> getRemovedJobSubTypes() {
        return removedJobSubTypes;
    }

    public void setRemovedJobSubTypes(List<JobSubType> removedJobSubTypes) {
        this.removedJobSubTypes = removedJobSubTypes;
    }

    public List<JobSubTypeCost> getChangedCosts() {
        return changedCosts;
    }

    public void setChangedCosts(List<JobSubTypeCost> changedCosts) {
        this.changedCosts = changedCosts;
    }

    public List<JobSubTypeCost> getRemovedCosts() {
        return removedCosts;
    }

    public void setRemovedCosts(List<JobSubTypeCost> removedCosts) {
        this.removedCosts = removedCosts;
    }

    public boolean hasData() {
        return
                hasData(newProducts) || hasData(changedProducts) || hasData(removedProducts) ||
                hasData(newJobTypes) || hasData(changedJobTypes) || hasData(removedJobTypes) ||
                hasData(newJobSubTypes) || hasData(changedJobSubTypes) || hasData(removedJobSubTypes) ||
                hasData(newCosts) || hasData(changedCosts) || hasData(removedCosts);
    }

    private boolean hasData(List list) {
        return list != null && !list.isEmpty();
    }
}
