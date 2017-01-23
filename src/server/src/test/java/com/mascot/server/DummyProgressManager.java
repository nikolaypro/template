package com.mascot.server;

import com.mascot.server.beans.ProgressService;
import com.mascot.server.common.ProgressManager;
import com.mascot.server.common.SimpleProgressManager;
import com.mascot.server.model.Progress;

/**
 * Created by Nikolay on 18.01.2017.
 */
public class DummyProgressManager extends SimpleProgressManager {
    public DummyProgressManager() {
        super(-1L, new ProgressService() {
            @Override
            public Long start(String state) {
                return null;
            }

            @Override
            public void update(Long id, String state, int value) {

            }

            @Override
            public void update(Long id, int value) {

            }

            @Override
            public void inc(Long id, String state, int incValue) {

            }

            @Override
            public void inc(Long id, int incValue) {

            }

            @Override
            public Progress get(Long id) {
                return null;
            }

            @Override
            public void finish(Long id) {

            }
        });
    }

    @Override
    public Progress get() {
        return null;
    }

    @Override
    public void finish() {

    }
}
