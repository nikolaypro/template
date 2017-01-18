package com.mascot.server;

import com.mascot.server.beans.ProgressService;
import com.mascot.server.common.ProgressManager;
import com.mascot.server.model.Progress;

/**
 * Created by Nikolay on 18.01.2017.
 */
public class DummyProgressManager extends ProgressManager {
    public DummyProgressManager() {
        super(-1L, null);
    }

    @Override
    public void update(String state, int value) {

    }

    @Override
    public void update(int value) {

    }

    @Override
    public void inc(String state, int incValue) {

    }

    @Override
    public void inc(int incValue) {

    }

    @Override
    public Progress get() {
        return null;
    }

    @Override
    public void finish() {

    }
}
