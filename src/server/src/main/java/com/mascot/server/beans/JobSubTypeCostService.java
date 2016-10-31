package com.mascot.server.beans;

import com.mascot.server.common.BeanTableResult;
import com.mascot.server.model.JobSubTypeCost;

import java.util.Map;

/**
 * Created by Николай on 31.10.2016.
 */
public interface JobSubTypeCostService {
    String NAME = "JobSubTypeCostService";

    BeanTableResult<JobSubTypeCost> getList(int start, int count, Map<String, String> orderBy);

    void update(JobSubTypeCost entity);

    boolean remove(Long id);

    JobSubTypeCost find(Long id);

    JobSubTypeCost findCost(Long jobSubTypeId, Long productId);
}
