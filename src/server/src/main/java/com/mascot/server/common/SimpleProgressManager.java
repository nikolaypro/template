package com.mascot.server.common;

import com.mascot.common.MascotUtils;
import com.mascot.server.beans.ProgressService;
import com.mascot.server.model.Progress;

/**
 * Created by Nikolay on 23.01.2017.
 */
public class SimpleProgressManager extends ProgressManager {
    private String state;
    private Double value;

    private String lastPersistedState;
    private Double lastPersistedValue;

    public SimpleProgressManager(Long progressId, ProgressService service) {
        super(progressId, service);
    }

    protected void reset() {
        state = null;
        value = null;
    }

    public static ProgressManager start(String state, ProgressService service) {
        final Long id = service.start(state);
        return new SimpleProgressManager(id, service);
    }

    public synchronized void update(String state, double value) {
        if (progressId == null) {
            return;
        }
        update(value);
        this.state = state;
    };

    public synchronized void update(double value) {
        if (progressId == null) {
            return;
        }
        this.value = value;
    };

    public synchronized void inc(String state, double incValue) {
        if (progressId == null) {
            return;
        }
        inc(incValue);
        this.state = state;
    };

    public synchronized void inc(double incValue) {
        if (progressId == null) {
            return;
        }
        this.value = this.value != null ? this.value + incValue : incValue;
    };

    /**
     *
     * @return progress from database merged by changes. Returned copy progress.
     */
    public Progress get() {
        if (progressId == null) {
            return null;
        }
        final Progress dbProgress = service.get(progressId);
        Progress progress = new Progress();
        if (dbProgress != null) {
            progress.setState(dbProgress.getState());
            progress.setValue(dbProgress.getValue());
        } else {
            progress.setState(null);
            progress.setValue(0);
        }
        if (this.value != null) {
            progress.setValue(Math.min(this.value.intValue(), 100));
        };
        if (this.state != null) {
            progress.setState(this.state);
        }
        return progress;
    };

    protected synchronized void flushChanges() {
        if (this.value != null && isChanged()) {
            double persistValue = Math.ceil(value);
            if (this.state != null) {
                service.update(progressId, state, (int) persistValue);
            } else {
                service.update(progressId, (int) persistValue);
            }
            lastPersistedValue = this.value;
            lastPersistedState = this.state;
        }
    }

    private boolean isChanged() {
        return !MascotUtils.equalsOrBothEmpty(lastPersistedState, state) ||
                !MascotUtils.equalsOrBothNull(lastPersistedValue, value);
    }

    public String getState() {
        return state;
    }

    public Double getValue() {
        return value;
    }
}
