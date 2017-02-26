package com.mascot.service.controller.order.product;

import com.mascot.server.beans.DictionaryService;
import com.mascot.server.beans.OrderService;
import com.mascot.server.beans.UserService;
import com.mascot.server.model.*;
import com.mascot.service.controller.AbstractController;
import com.mascot.service.controller.common.DictionaryRecord;
import com.mascot.service.controller.common.ResultRecord;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Николай on 24.02.2017.
 */
@RestController
@RequestMapping(value = "/order-product")
public class OrderProductController extends AbstractController {
    @Inject
    private OrderService orderService;

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

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    public Long update(@RequestBody OrderProductRecord record) {
        final User user = userService.getCurrentUser();
        String orderInfo = getOrderLogInfo(record, user);
        logger.info(String.format("Order (id = %s): %s", record.id, orderInfo));
        final Order entity;
        if (record.id == null) {
            entity = new Order();
            entity.setCreationDate(new Date());
        } else {
            final Order existsEntity = orderService.find(record.id);
            if (existsEntity == null) {
                throw new IllegalStateException("Unable find order with id = " + record.id);
//                return ResultRecord.fail("Unable find order with id = " + record.id);
            }
            entity = existsEntity;
            if (!OrderStatus.ON_HOLD.equals(existsEntity.getStatus())) {
                // todo сделать правильно
                throw new IllegalStateException("Unable change entity in statue: " + existsEntity.getStatus());
            }
            entity.setModifyDate(new Date());
        }
        entity.setUser(user);
        entity.setCost(record.cost);
        entity.setType(OrderType.PRODUCT);
        entity.setStatus(record.send ? OrderStatus.SEND : OrderStatus.ON_HOLD);

        List<OrderProductLine> entityLines = new ArrayList<>();
        record.lines.forEach(line -> {
            final OrderProductLine entityLine;
            if (line.id == null) {
                entityLine = new OrderProductLine();
                entityLine.setOrder(entity);
            } else {
                Optional<OrderProductLine> first = entity.getProductLines().stream().filter(e -> e.getId().equals(line.id)).findFirst();
                if (first.isPresent()) {
                    entityLine = first.get();
                } else {
                    // todo сделать правильно
                    throw new IllegalStateException("Not found order line with id = " + line.id);
                }
            }
            entityLine.setCost(line.cost);
            entityLine.setCount(line.count);
            entityLine.setStitchingType(StitchingType.valueOf(line.stitchingType));

            if (line.product == null) {
                throw new IllegalStateException("Product not defined");
            }
            final Product product = dictionaryService.findProduct(line.product.id);
            if (product == null) {
                throw new IllegalStateException("Not found product with id = '" + line.product.id + "'");
            }
            entityLine.setProduct(product);

            if (line.mainCloth == null) {
                throw new IllegalStateException("MainCloth not defined");
            }
            final Cloth mainCloth = dictionaryService.findCloth(line.mainCloth.id);
            if (mainCloth == null) {
                throw new IllegalStateException("Not found cloth with id = '" + line.mainCloth.id + "'");
            }
            entityLine.setMainCloth(mainCloth);

            if (line.compCloth1 == null) {
                throw new IllegalStateException("CompCloth1 not defined");
            }
            final Cloth compCloth1 = dictionaryService.findCloth(line.compCloth1.id);
            if (compCloth1 == null) {
                throw new IllegalStateException("Not found cloth with id = '" + line.compCloth1.id + "'");
            }
            entityLine.setCompCloth1(compCloth1);

            if (line.compCloth2 == null) {
                throw new IllegalStateException("CompCloth2 not defined");
            }
            final Cloth compCloth2 = dictionaryService.findCloth(line.compCloth2.id);
            if (compCloth2 == null) {
                throw new IllegalStateException("Not found cloth with id = '" + line.compCloth2.id + "'");
            }
            entityLine.setCompCloth2(compCloth2);

            entityLines.add(entityLine);
        });

        entity.setProductLines(new HashSet<>(entityLines));
        orderService.update(entity);
        logger.info("Order " + (record.id == null ? "created" : "updated") + " success with id = " + entity.getId());
        return entity.getId();
//        return ResultRecord.success();
    }


    private String getOrderLogInfo(@RequestBody OrderProductRecord record, User user) {
        String userInfo = getUserLogInfo(user);
        StringBuilder linesInfo = new StringBuilder("");
        Function<DictionaryRecord, String> dictionaryInfo = p -> "name: " + p.name + ", id: " + p.id;
        record.lines.forEach(line -> linesInfo
                .append("\n\t\t").append("id = ").append(line.id)
                .append("\n\t\t").append("product = ").append(dictionaryInfo.apply(line.product))
                .append("\n\t\t").append("mainCloth = ").append(dictionaryInfo.apply(line.mainCloth))
                .append("\n\t\t").append("compCloth 1 = ").append(dictionaryInfo.apply(line.compCloth1))
                .append("\n\t\t").append("compCloth 2 = ").append(dictionaryInfo.apply(line.compCloth2))
                .append("\n\t\t").append("count = ").append(line.count)
                .append("\n\t\t").append("cost = ").append(line.cost)
        );
        return "\n\tuser: " + userInfo +
        "\n\tsend: " + record.send +
        "\n\tlines: " + linesInfo;
    }

    private String getUserLogInfo(User user) {
        return String.format("id = %s, login = '%s', full name = '%s'", user.getId(), user.getLogin(), user.getFullName());
    }

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

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    @ResponseBody
    public OrderProductRecord get(@PathVariable("id") Long id) {
/*
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
        final Order entity = orderService.find(id);
        if (entity == null) {
            logger.warn("Order with id = '" + id + "' not found");
            throw new IllegalStateException("Unable find order. May be it was deleted");
        }
        if (!entity.getUser().getId().equals(userService.getCurrentUserId())) {
            throw new IllegalStateException("Unable show order for another user");
        }
        return OrderProductRecord.build(entity);
    }

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

    @RequestMapping(value = "/stitching-types", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public String[] getLocales() {
        logger.info("Get stitching");
        List<String> result = Arrays.stream(StitchingType.values()).map(Enum::name).collect(Collectors.toList());
        return result.toArray(new String[result.size()]);

/*
        final List<String> locales = Stream.of(Locale.getAvailableLocales()).
                filter(locale -> Arrays.binarySearch(allowed, locale.getLanguage()) >= 0).
                map(locale -> locale.toString()).
                collect(Collectors.toList());
        final List<Locale> locales2 = Stream.of(Locale.getAvailableLocales()).
                filter(locale -> Arrays.binarySearch(allowed, locale.getLanguage()) >= 0).
                collect(Collectors.toList());
*/
    }

}
