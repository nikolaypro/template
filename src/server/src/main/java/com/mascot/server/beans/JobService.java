package com.mascot.server.beans;

import com.mascot.server.common.BeanTableResult;
import com.mascot.server.model.Job;

import java.util.Map;

/**
 * Created by Nikolay on 08.11.2016.
 */
public interface JobService {
    String NAME = "JobService";

    BeanTableResult<Job> getList(int start, int count, Map<String, String> orderBy);

    void update(Job entity);

    boolean remove(Long id);

    Job find(Long id);

}
