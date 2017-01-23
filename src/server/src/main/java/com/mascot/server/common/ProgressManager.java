package com.mascot.server.common;

import com.mascot.server.beans.ProgressService;
import com.mascot.server.model.Progress;
import org.apache.log4j.Logger;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Nikolay on 18.01.2017.
 */
public abstract class ProgressManager {
    private static final Logger logger = Logger.getLogger(ProgressManager.class);
    protected final Long progressId;
    protected final ProgressService service;
    private final static CopyOnWriteArrayList<ProgressManager> instances = new CopyOnWriteArrayList<>();

    ProgressManager(Long progressId, ProgressService service) {
        this.progressId = progressId;
        this.service = service;
        logger.info("Created progress manager with progressId = " + progressId);
        instances.add(this);
    }

    public static void flush() {
        if (instances.isEmpty()) {
            return;
        }
        instances.forEach(m -> {
            logger.info("Start progress flash for: '" + m.progressId + "'");
            m.flushChanges();
//            instances.remove(m);
            logger.info("End progress flash for: '" + m.progressId + "'");
        });
    }

    public void finish() {
        if (progressId == null) {
            return;
        }
        service.finish(progressId);
        instances.remove(this);
        reset();
    };

    public abstract void update(String state, double value);

    public abstract void update(double value);

    public abstract void inc(String state, double value);

    public abstract void inc(double value);

    protected abstract void reset();

    protected abstract void flushChanges();

}
