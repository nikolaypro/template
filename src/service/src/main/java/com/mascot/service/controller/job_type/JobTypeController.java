package com.mascot.service.controller.job_type;

import com.mascot.common.ErrorLogger;
import com.mascot.server.beans.JobTypeService;
import com.mascot.server.beans.UserService;
import com.mascot.server.common.BeanTableResult;
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
 * Created by Nikolay on 24.10.2016.
 */
@RestController
@RequestMapping(value = "/job-type")
public class JobTypeController extends AbstractController {
    @Inject
    private JobTypeService jobTypeService;

    @Inject
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    public TableResult<JobTypeRecord> getList(@RequestBody TableParams params) {
        final BeanTableResult<JobType> beanTableResult = jobTypeService.getList(params.getStartIndex(), params.count, params.orderBy);
        final Collection<JobType> list = beanTableResult.getRows();
        final int totalCount = beanTableResult.getCount();

        final List<JobTypeRecord> result = list.stream().
                map(JobTypeRecord::build).
                collect(Collectors.toList());

        return TableResult.create(result.toArray(new JobTypeRecord[result.size()]), totalCount);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    public ResultRecord update(@RequestBody JobTypeRecord record) {
        logger.info("Job type: name = " + record.name + ", id = " + record.id);
        final JobType entity;
        if (record.id == null) {
            final JobType existsEntity = jobTypeService.findByName(record.name);
            if (existsEntity != null) {
                return ResultRecord.failLocalized("job-type.name.already.exists", record.name);
            }
            entity = new JobType();
        } else {
            final JobType existsEntity = jobTypeService.find(record.id);
            if (existsEntity == null) {
                return ResultRecord.fail("Unable find job type with id = " + record.id);
            }
            final JobType existsNameEntity = jobTypeService.findByName(record.name);
            if (existsNameEntity != null && !existsNameEntity.getId().equals(existsEntity.getId())) {
                return ResultRecord.failLocalized("job-type.name.already.exists", record.name);
            }
            entity = existsEntity;
        }
        entity.setName(record.name);

        jobTypeService.update(entity);

        return ResultRecord.success();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    public ResultRecord delete(@RequestBody Long[] ids) {
/*
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
        if (ids == null) {
            return ResultRecord.success();
        }
        logger.info("Delete job types size: " + ids.length);
        Stream.of(ids).forEach(id -> {
                    logger.info("Delete job type: " + id);
                    try {
                        if (!jobTypeService.remove(id)) {
                            logger.warn("Unable delete job type: " + id);
                        }
                    } catch (Exception e) {
                        ErrorLogger.error(logger, "Unable delete job type: " + id, e);;
                    }
                }
        );
        return ResultRecord.success();
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    @ResponseBody
    public JobTypeRecord get(@PathVariable("id") Long id) {
        logger.info("Job type: '" + userService.getCurrentUser().getFullName() + "'");
        logger.info("Job type id: '" + userService.getCurrentUserId()+ "'");
        final JobType entity = jobTypeService.find(id);
        if (entity == null) {
            logger.warn("Job type with id = '" + id + "' not found");
            throw new IllegalStateException("Unable find job type. May be it was deleted");
        }
        return JobTypeRecord.build(entity);
    }

    @RequestMapping(value = "/move_up/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
//    @ResponseBody
    public void moveUp(@PathVariable("id") Long id) {
        jobTypeService.moveUp(id);
    }

    @RequestMapping(value = "/move_down/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
//    @ResponseBody
    public void moveDown(@PathVariable("id") Long id) {
        jobTypeService.moveDown(id);
    }

}
