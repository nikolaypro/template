package com.mascot.server.beans.importdata;

/**
 * Created by Николай on 02.12.2016.
 */
public class ImportProgress {
    private String stage;
    private int percent;

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public void set(String stage, int percent) {
        this.stage = stage;
        this.percent = percent;
    }

    public void inc(String stage, int percent) {
        this.stage = stage;
        this.percent += percent;
    }
}
