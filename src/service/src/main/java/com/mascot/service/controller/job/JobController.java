package com.mascot.service.controller.job;

import com.mascot.server.beans.JobService;
import com.mascot.server.beans.JobTypeService;
import com.mascot.server.beans.ProductService;
import com.mascot.server.beans.UserService;
import com.mascot.server.common.BeanTableResult;
import com.mascot.server.model.Job;
import com.mascot.server.model.JobType;
import com.mascot.server.model.Product;
import com.mascot.server.model.Role;
import com.mascot.service.controller.AbstractController;
import com.mascot.service.controller.common.BooleanRecord;
import com.mascot.service.controller.common.ResultRecord;
import com.mascot.service.controller.common.TableParams;
import com.mascot.service.controller.common.TableResult;
import com.mascot.service.controller.job_type.JobTypeRecord;
import com.mascot.service.controller.product.ProductRecord;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Nikolay on 08.11.2016.
 */
@RestController
@RequestMapping(value = "/jobs")
public class JobController extends AbstractController {
    @Inject
    private JobService jobService;

    @Inject
    private JobTypeService jobTypeService;

    @Inject
    private ProductService productService;

    @Inject
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    public TableResult<JobRecord> getList(@RequestBody TableParams params) {
        final BeanTableResult<Job> beanTableResult = jobService.getList(params.getStartIndex(), params.count, params.orderBy);
        final Collection<Job> list = beanTableResult.getRows();
        final int totalCount = beanTableResult.getCount();

        final List<JobRecord> result = list.stream().
                map(JobRecord::build).
                collect(Collectors.toList());

        return TableResult.create(result.toArray(new JobRecord[result.size()]), totalCount);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    public ResultRecord update(@RequestBody JobRecord record) {
        logger.info("Job : jobType = " + record.jobType + ", product: " + record.product + ", number: " +
                record.number +  ", complete date: " + new Date(record.completeDate) + " ,id = " + record.id);
        final Job entity;
        if (record.id == null) {
            entity = new Job();
        } else {
            final Job existsEntity = jobService.find(record.id);
            if (existsEntity == null) {
                return ResultRecord.fail("Unable find job with id = " + record.id);
            }
            entity = existsEntity;
        }
        entity.setNumber(record.number);
        entity.setCompleteDate(new Date(record.completeDate));
        if (record.jobType == null) {
            throw new IllegalStateException("Job type not defined");
        }
        JobType jobType = jobTypeService.find(record.jobType.id);
        if (jobType == null) {
            throw new IllegalStateException("Not found job type with id = '" + record.jobType.id + "'");
        }
        entity.setJobType(jobType);

        if (record.product == null) {
            throw new IllegalStateException("Product not defined");
        }
        Product product = productService.find(record.product.id);
        if (product == null) {
            throw new IllegalStateException("Not found product with id = '" + record.product.id + "'");
        }
        entity.setProduct(product);

        jobService.update(entity);

        return ResultRecord.success();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    public ResultRecord delete(@RequestBody Long[] ids) {
        if (ids == null) {
            return ResultRecord.success();
        }
        logger.info("Delete job sub type costs size: " + ids.length);
        Stream.of(ids).forEach(id -> {
                    logger.info("Delete job sub type cost: " + id);
                    try {
                        if (!jobService.remove(id)) {
                            logger.warn("Unable delete job sub type cost: " + id);
                        }
                    } catch (Exception e) {
                        logger.error("Unable delete job sub type cost: " + id, e);
                    }
                }
        );
        return ResultRecord.success();
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    @ResponseBody
    public JobRecord get(@PathVariable("id") Long id) {
        final Job entity = jobService.find(id);
        if (entity == null) {
            logger.warn("Job sub type cost with id = '" + id + "' not found");
            throw new IllegalStateException("Unable find job sub type cost. May be it was deleted");
        }
        return JobRecord.build(entity);
    }

    @RequestMapping(value = "/job-types", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    public List<JobTypeRecord> getJobSubTypes() {
        return jobTypeService.getAll().stream().map(JobTypeRecord::build).collect(Collectors.toList());
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    public List<ProductRecord> getProducts() {
        return productService.getAll().stream().map(ProductRecord::build).collect(Collectors.toList());
    }


}
