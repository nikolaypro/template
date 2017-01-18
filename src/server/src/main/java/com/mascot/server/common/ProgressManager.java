package com.mascot.server.common;

import com.mascot.server.beans.ProgressService;
import com.mascot.server.model.Progress;

/**
 * Created by Nikolay on 18.01.2017.
 */
public class ProgressManager {
    private final Long progressId;
    private final ProgressService service;

    public ProgressManager(Long progressId, ProgressService service) {
        this.progressId = progressId;
        this.service = service;
    }

    public static ProgressManager start(String state, ProgressService service) {
        final Long id = service.start(state);
        return new ProgressManager(id, service);
    }

    public void update(String state, int value) {
        if (progressId == null) {
            return;
        }
        service.update(progressId, state, value);
    };

    public void update(int value) {
        if (progressId == null) {
            return;
        }
        service.update(progressId, value);
    };

    public void inc(String state, int incValue) {
        if (progressId == null) {
            return;
        }
        service.inc(progressId, state, incValue);
    };

    public void inc(int incValue) {
        if (progressId == null) {
            return;
        }
        service.inc(progressId, incValue);
    };

    public Progress get() {
        if (progressId == null) {
            return null;
        }
        return service.get(progressId);
    };

    public void finish() {
        if (progressId == null) {
            return;
        }
        service.finish(progressId);
    };

}
