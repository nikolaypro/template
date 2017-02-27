package com.mascot.service.controller.order.cloth;

import com.mascot.server.beans.DictionaryService;
import com.mascot.server.beans.OrderService;
import com.mascot.server.beans.UserService;
import com.mascot.server.model.*;
import com.mascot.service.controller.AbstractController;
import com.mascot.service.controller.common.DictionaryRecord;
import com.mascot.service.controller.order.OrderEntityProvider;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Николай on 27.02.2017.
 */
@RestController
@RequestMapping(value = "/order-cloth")
public class OrderClothController extends AbstractController {
    @Inject
    private OrderService orderService;

    @Inject
    private DictionaryService dictionaryService;

    @Inject
    private UserService userService;

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    public Long update(@RequestBody OrderClothRecord record) {
        final User user = userService.getCurrentUser();
        String orderInfo = getOrderLogInfo(record, user);
        logger.info(String.format("Order (id = %s): %s", record.id, orderInfo));

        final Order entity = OrderEntityProvider.getAndFillEntity(record, user, orderService);
        entity.setType(OrderType.CLOTH);

        List<OrderClothLine> entityLines = new ArrayList<>();
        record.lines.forEach(line -> {
            final OrderClothLine entityLine;
            if (line.id == null) {
                entityLine = new OrderClothLine();
                entityLine.setOrder(entity);
            } else {
                Optional<OrderClothLine> first = entity.getClothLines().stream().filter(e -> e.getId().equals(line.id)).findFirst();
                if (first.isPresent()) {
                    entityLine = first.get();
                } else {
                    // todo сделать правильно
                    throw new IllegalStateException("Not found order line with id = " + line.id);
                }
            }
            entityLine.setCost(line.cost);
            entityLine.setCount(line.count);

            if (line.cloth == null) {
                throw new IllegalStateException("Cloth not defined");
            }
            final Cloth cloth = dictionaryService.findCloth(line.cloth.id);
            if (cloth == null) {
                throw new IllegalStateException("Not found cloth with id = '" + line.cloth.id + "'");
            }
            entityLine.setCloth(cloth);

            entityLines.add(entityLine);
        });

        entity.setClothLines(new HashSet<>(entityLines));
        orderService.update(entity);
        logger.info("Order " + (record.id == null ? "created" : "updated") + " success with id = " + entity.getId());
        return entity.getId();
//        return ResultRecord.success();
    }


    private String getOrderLogInfo(@RequestBody OrderClothRecord record, User user) {
        String userInfo = getUserLogInfo(user);
        StringBuilder linesInfo = new StringBuilder("");
        Function<DictionaryRecord, String> dictionaryInfo = p -> "name: " + p.name + ", id: " + p.id;
        record.lines.forEach(line -> linesInfo
                .append("\n\t\t").append("id = ").append(line.id)
                .append("\n\t\t").append("cloth = ").append(dictionaryInfo.apply(line.cloth))
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

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    @ResponseBody
    public OrderClothRecord get(@PathVariable("id") Long id) {
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
        return OrderClothRecord.build(entity);
    }

    @RequestMapping(value = "/clothes", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    public List<DictionaryRecord> getMainClothes() {
        return dictionaryService.getClothes().stream().map(DictionaryRecord::new).collect(Collectors.toList());
    }

}
