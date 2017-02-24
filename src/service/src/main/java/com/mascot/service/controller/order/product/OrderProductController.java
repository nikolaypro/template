package com.mascot.service.controller.order.product;

import com.mascot.server.beans.DictionaryService;
import com.mascot.server.beans.UserService;
import com.mascot.server.model.Role;
import com.mascot.service.controller.AbstractController;
import com.mascot.service.controller.common.DictionaryRecord;
import com.mascot.service.controller.common.ResultRecord;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Николай on 24.02.2017.
 */
@RestController
@RequestMapping(value = "/order-product")
public class OrderProductController extends AbstractController {
//    @Inject
//    private OrderService orderService;
//
    @Inject
    private DictionaryService dictionaryService;

    @Inject
    private UserService userService;

/*
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    public TableResult<OrderTableRecord> getList(@RequestBody TableParams params) {
        final ZonedDateTime filterDate = jobService.getFilterDate(params.filter);
        final int maxOrder = jobTypeService.getMaxOrder();
        final BeanTableResult<Job> beanTableResult = jobService.getList(params.getStartIndex(), params.count,
                params.orderBy, params.filter);
        final Collection<Job> list = beanTableResult.getRows();
        final int totalCount = beanTableResult.getCount();

        final List<JobRecord> result = list.stream().
                map(JobRecord::build).
                peek(e -> {
                    if (filterDate != null) {
                        e.tail = isTail(e, filterDate, maxOrder);
                        e.forNextWeekTail = !e.tail && e.jobType.order < maxOrder;
                    }
                }).
                collect(Collectors.toList());

        return TableResult.create(result.toArray(new JobRecord[result.size()]), totalCount);
    }
*/

/*
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    public ResultRecord update(@RequestBody OrderProductRecord record) {
        logger.info("Order : product: " + record.product + ", number: " +
                record.number +  ", complete date: " + record.completeDate + " ,id = " + record.id + ", count: " + record.count);
        if (record.id == null && record.count != null) {
            IntStream.range(0, record.count).forEach(i -> {
                updateSingle(record);
                record.number++;
            });
            return ResultRecord.success();
        } else {
            return updateSingle(record);
        }
    }
*/

/*
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
                        ErrorLogger.error(logger, "Unable delete job sub type cost: " + id, e);;
                    }
                }
        );
        return ResultRecord.success();
    }
*/

/*
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
*/

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    public List<DictionaryRecord> getProducts() {
        return dictionaryService.getProducts().stream().map(DictionaryRecord::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "/main-clothes", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    public List<DictionaryRecord> getMainClothes(@RequestBody Long productId) {
        return dictionaryService.getMainClothes(productId).stream().map(DictionaryRecord::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "/comp-clothes-1", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    public List<DictionaryRecord> getCompClothes1(@RequestBody Long productId) {
        return dictionaryService.getCompClothes1(productId).stream().map(DictionaryRecord::new).collect(Collectors.toList());
    }

    @RequestMapping(value = "/comp-clothes-2", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    public List<DictionaryRecord> getCompClothes2(@RequestBody Long productId) {
        return dictionaryService.getCompClothes2(productId).stream().map(DictionaryRecord::new).collect(Collectors.toList());
    }

}
