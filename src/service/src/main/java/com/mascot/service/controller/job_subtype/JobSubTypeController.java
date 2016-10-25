package com.mascot.service.controller.job_subtype;

import com.mascot.server.beans.JobSubTypeService;
import com.mascot.server.beans.JobTypeService;
import com.mascot.server.beans.UserService;
import com.mascot.server.common.BeanTableResult;
import com.mascot.server.model.JobSubType;
import com.mascot.server.model.JobType;
import com.mascot.server.model.Role;
import com.mascot.service.controller.AbstractController;
import com.mascot.service.controller.common.ResultRecord;
import com.mascot.service.controller.common.TableParams;
import com.mascot.service.controller.common.TableResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Nikolay on 25.10.2016.
 */
@RestController
@RequestMapping(value = "/job-subtype")
public class JobSubTypeController extends AbstractController {
    @Inject
    private JobSubTypeService jobSubTypeService;

    @Inject
    private JobTypeService jobTypeService;

    @Inject
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public TableResult<JobSubTypeRecord> getList(@RequestBody TableParams params) {
        final BeanTableResult<JobSubType> beanTableResult = jobSubTypeService.getList(params.getStartIndex(), params.count, params.orderBy);
        final Collection<JobSubType> list = beanTableResult.getRows();
        final int totalCount = beanTableResult.getCount();

        final List<JobSubTypeRecord> result = list.stream().
                map(JobSubTypeRecord::build).
                collect(Collectors.toList());

        return TableResult.create(result.toArray(new JobSubTypeRecord[result.size()]), totalCount);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public ResultRecord update(@RequestBody JobSubTypeRecord record) {
        logger.info("Job type: name = " + record.name + ", id = " + record.id);
        final JobSubType entity;
        if (record.id == null) {
            final JobSubType existsEntity = jobSubTypeService.findByName(record.name);
            if (existsEntity != null) {
                return ResultRecord.failLocalized("job-subtype.name.already.exists", record.name);
            }
            entity = new JobSubType();
        } else {
            final JobSubType existsEntity = jobSubTypeService.find(record.id);
            if (existsEntity == null) {
                return ResultRecord.fail("Unable find job sub type with id = " + record.id);
            }
            final JobSubType existsNameEntity = jobSubTypeService.findByName(record.name);
            if (existsNameEntity != null && !existsNameEntity.getId().equals(existsEntity.getId())) {
                return ResultRecord.failLocalized("job-subtype.name.already.exists", record.name);
            }
            entity = existsEntity;
        }
        entity.setName(record.name);
        if (record.jobType == null) {
            throw new IllegalStateException("Job type not defined");
        }
        JobType jobType = jobTypeService.find(record.jobType.id);
        if (jobType == null) {
            throw new IllegalStateException("Not found job type with id = '" + record.jobType.id + "'");
        }
        entity.setJobType(jobType);

        jobSubTypeService.update(entity);

        return ResultRecord.success();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public ResultRecord delete(@RequestBody Long[] ids) {
        if (ids == null) {
            return ResultRecord.success();
        }
        logger.info("Delete job sub types size: " + ids.length);
        Stream.of(ids).forEach(id -> {
                    logger.info("Delete job sub type: " + id);
                    try {
                        if (!jobSubTypeService.remove(id)) {
                            logger.warn("Unable delete job sub type: " + id);
                        }
                    } catch (Exception e) {
                        logger.error("Unable delete job sub type: " + id, e);
                    }
                }
        );
        return ResultRecord.success();
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    @ResponseBody
    public JobSubTypeRecord get(@PathVariable("id") Long id) {
        logger.info("Job sub type: '" + userService.getCurrentUser().getFullName() + "'");
        logger.info("Job sub type id: '" + userService.getCurrentUserId()+ "'");
        final JobSubType entity = jobSubTypeService.find(id);
        if (entity == null) {
            logger.warn("Job sub type with id = '" + id + "' not found");
            throw new IllegalStateException("Unable find job sub type. May be it was deleted");
        }
        return JobSubTypeRecord.build(entity);
    }


}
