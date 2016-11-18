package com.mascot.service.controller.job_subtype_cost;

import com.mascot.server.beans.JobSubTypeCostService;
import com.mascot.server.beans.JobSubTypeService;
import com.mascot.server.beans.ProductService;
import com.mascot.server.beans.UserService;
import com.mascot.server.common.BeanTableResult;
import com.mascot.server.model.JobSubType;
import com.mascot.server.model.JobSubTypeCost;
import com.mascot.server.model.Product;
import com.mascot.server.model.Role;
import com.mascot.service.controller.AbstractController;
import com.mascot.service.controller.common.BooleanRecord;
import com.mascot.service.controller.common.ResultRecord;
import com.mascot.service.controller.common.TableParams;
import com.mascot.service.controller.common.TableResult;
import com.mascot.service.controller.job_subtype.JobSubTypeRecord;
import com.mascot.service.controller.product.ProductRecord;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Николай on 31.10.2016.
 */
@RestController
@RequestMapping(value = "/job-subtype-cost")
public class JobSubTypeCostController extends AbstractController {
    @Inject
    private JobSubTypeCostService jobSubTypeCostService;

    @Inject
    private JobSubTypeService jobSubTypeService;

    @Inject
    private ProductService productService;

    @Inject
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public TableResult<JobSubTypeCostRecord> getList(@RequestBody TableParams params) {
        final BeanTableResult<JobSubTypeCost> beanTableResult = jobSubTypeCostService.getList(params.getStartIndex(), params.count, params.orderBy);
        final Collection<JobSubTypeCost> list = beanTableResult.getRows();
        final int totalCount = beanTableResult.getCount();

        final List<JobSubTypeCostRecord> result = list.stream().
                map(JobSubTypeCostRecord::build).
                collect(Collectors.toList());

        return TableResult.create(result.toArray(new JobSubTypeCostRecord[result.size()]), totalCount);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public ResultRecord update(@RequestBody JobSubTypeCostRecord record) {
        logger.info("Job type cost: jobSubType = " + record.jobSubType + ", product: " + record.product + ", cost: " +
                record.cost +  "id = " + record.id);
        final JobSubTypeCost entity;
        if (record.id == null) {
            final JobSubTypeCost existsEntity = jobSubTypeCostService.findCost(record.jobSubType.id, record.product.id);
            if (existsEntity != null) {
                return ResultRecord.failLocalized("job-subtype-cost.already.exists", record.jobSubType.name, record.product.name);
            }
            entity = new JobSubTypeCost();
        } else {
            final JobSubTypeCost existsEntity = jobSubTypeCostService.find(record.id);
            if (existsEntity == null) {
                return ResultRecord.fail("Unable find job sub type cost with id = " + record.id);
            }
            final JobSubTypeCost existsNameEntity = jobSubTypeCostService.findCost(record.jobSubType.id, record.product.id);
            if (existsNameEntity != null && !existsNameEntity.getId().equals(existsEntity.getId())) {
                return ResultRecord.failLocalized("job-subtype-cost.already.exists", record.jobSubType.name, record.product.name);
            }
            entity = existsEntity;
        }
        entity.setCost(record.cost);
        if (record.jobSubType == null) {
            throw new IllegalStateException("Job sub type not defined");
        }
        JobSubType jobSubType = jobSubTypeService.find(record.jobSubType.id);
        if (jobSubType == null) {
            throw new IllegalStateException("Not found job sub type with id = '" + record.jobSubType.id + "'");
        }
        entity.setJobSubType(jobSubType);

        if (record.product == null) {
            throw new IllegalStateException("Product not defined");
        }
        Product product = productService.find(record.product.id);
        if (product == null) {
            throw new IllegalStateException("Not found product with id = '" + record.product.id + "'");
        }
        entity.setProduct(product);

        jobSubTypeCostService.update(entity);

        return ResultRecord.success();
    }

    @RequestMapping(value = "/update-cost", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public ResultRecord updateCost(@RequestBody JobSubTypeCostRecord record) {
        logger.info("Update cost only: jobSubType = " + record.jobSubType + ", product: " + record.product + ", cost: " +
                record.cost +  "id = " + record.id);
            final JobSubTypeCost existsEntity = jobSubTypeCostService.findCost(record.jobSubType.id, record.product.id);
            if (existsEntity == null) {
                throw new IllegalStateException("Not found cost for jobSubType = " + record.jobSubType + ", product: " + record.product);
            }
        existsEntity.setCost(record.cost);
        jobSubTypeCostService.update(existsEntity);
        return ResultRecord.success();
    }

    @RequestMapping(value = "/not-exists", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public BooleanRecord isNotExists(@RequestBody JobSubTypeCostRecord record) {
        logger.info("Check exists: jobSubType = " + record.jobSubType + ", product: " + record.product + ", cost: " +
                record.cost +  "id = " + record.id);
        final JobSubTypeCost existsEntity = jobSubTypeCostService.findCost(record.jobSubType.id, record.product.id);
        if (existsEntity != null) {
            return BooleanRecord.falseResult(existsEntity.getCost().toString());
        }
        return BooleanRecord.trueResult();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public ResultRecord delete(@RequestBody Long[] ids) {
        if (ids == null) {
            return ResultRecord.success();
        }
        logger.info("Delete job sub type costs size: " + ids.length);
        Stream.of(ids).forEach(id -> {
                    logger.info("Delete job sub type cost: " + id);
                    try {
                        if (!jobSubTypeCostService.remove(id)) {
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
    public JobSubTypeCostRecord get(@PathVariable("id") Long id) {
        final JobSubTypeCost entity = jobSubTypeCostService.find(id);
        if (entity == null) {
            logger.warn("Job sub type cost with id = '" + id + "' not found");
            throw new IllegalStateException("Unable find job sub type cost. May be it was deleted");
        }
        return JobSubTypeCostRecord.build(entity);
    }

    @RequestMapping(value = "/job-subtypes", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public List<JobSubTypeRecord> getJobSubTypes() {
        return jobSubTypeService.getAll().stream().map(JobSubTypeRecord::build).collect(Collectors.toList());
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public List<ProductRecord> getProducts() {
        return productService.getAll().stream().map(ProductRecord::build).collect(Collectors.toList());
    }

}

