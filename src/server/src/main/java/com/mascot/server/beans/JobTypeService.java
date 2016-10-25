package com.mascot.server.beans;

import com.mascot.server.common.BeanTableResult;
import com.mascot.server.model.JobType;

import java.util.Map;

/**
 * Created by Nikolay on 24.10.2016.
 */
public interface JobTypeService {

    String NAME = "JobTypeService";

    BeanTableResult<JobType> getList(int start, int count, Map<String, String> orderBy);

    void update(JobType entity);

    boolean remove(Long id);

    JobType find(Long id);

    JobType findByName(String name);

    void moveUp(Long id);

    void moveDown(Long id);
}
