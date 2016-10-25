package com.mascot.server.beans;

import com.mascot.server.common.BeanTableResult;
import com.mascot.server.model.JobSubType;
import com.mascot.server.model.JobType;

import java.util.Map;

/**
 * Created by Nikolay on 25.10.2016.
 */
public interface JobSubTypeService {
    String NAME = "JobSubTypeService";

    BeanTableResult<JobSubType> getList(int start, int count, Map<String, String> orderBy);

    void update(JobSubType entity);

    boolean remove(Long id);

    JobSubType find(Long id);

    JobSubType findByName(String name);

}
