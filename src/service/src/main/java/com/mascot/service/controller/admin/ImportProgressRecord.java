package com.mascot.service.controller.admin;

import com.mascot.server.beans.importdata.ImportProgress;

/**
 * Created by Николай on 22.12.2016.
 */
public class ImportProgressRecord {
    public String state;
    public Integer percent;

    public static ImportProgressRecord build(ImportProgress importProgress) {
        ImportProgressRecord result = new ImportProgressRecord();
        result.percent = importProgress.getPercent();
        result.state = importProgress.getState();
        return result;
    }
}
