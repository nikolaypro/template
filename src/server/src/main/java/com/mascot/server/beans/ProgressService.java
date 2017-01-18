package com.mascot.server.beans;

import com.mascot.server.model.Progress;

/**
 * Created by Nikolay on 18.01.2017.
 */
public interface ProgressService {
    String NAME = "ProgressService";

    Long start(String state);

    void update(Long id, String state, int value);

    void update(Long id, int value);

    void inc(Long id, String state, int incValue);

    void inc(Long id, int incValue);

    Progress get(Long id);

    void finish(Long id);

}
