package com.mascot.service.controller.admin;

import com.mascot.server.beans.importdata.ImportProgress;
import com.mascot.server.model.Progress;
import com.mascot.service.controller.common.Localization;
import com.mascot.service.security.MascotSession;

/**
 * Created by Николай on 22.12.2016.
 */
public class ProgressRecord {
    public String state;
    public Integer percent;

    public static ProgressRecord build(ImportProgress importProgress) {
        ProgressRecord result = new ProgressRecord();
        result.percent = importProgress.getPercent();
        result.state = importProgress.getState();
        return result;
    }

    public static ProgressRecord build(Progress progress) {
        if (progress == null) {
            return null;
        }
        ProgressRecord result = new ProgressRecord();
        result.percent = progress.getValue();
        result.state = Localization.get(progress.getState(), MascotSession.getCurrent().getLocale());
        return result;
    }
}
