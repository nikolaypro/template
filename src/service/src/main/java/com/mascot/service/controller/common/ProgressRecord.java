package com.mascot.service.controller.common;

import com.mascot.server.model.Progress;

/**
 * Created by Николай on 22.12.2016.
 */
public class ProgressRecord {
    public String state;
    public Integer percent;

    public static ProgressRecord build(Progress progress) {
        if (progress == null) {
            return null;
        }
        ProgressRecord result = new ProgressRecord();
        result.percent = progress.getValue();
        result.state = progress.getState();
        return result;
    }
}
