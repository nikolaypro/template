package com.mascot.server.beans.importdata;

/**
 * Created by Николай on 02.12.2016.
 */
public class ImportProgress {
    private String state;
    private int percent;

    public String getState() {
        return state;
    }

    void setState(String state) {
        this.state = state;
    }

    public int getPercent() {
        return percent;
    }

    void setPercent(int percent) {
        this.percent = percent;
    }

    public void set(String state, int percent) {
        this.state = state;
        this.percent = percent;
    }

    void inc(String state, int percent) {
        this.state = state;
        this.percent += percent;
    }
}
