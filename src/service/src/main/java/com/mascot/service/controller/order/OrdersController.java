package com.mascot.service.controller.order;

import com.mascot.server.beans.OrderService;
import com.mascot.server.beans.UserService;
import com.mascot.server.common.BeanTableResult;
import com.mascot.server.model.Order;
import com.mascot.server.model.Role;
import com.mascot.server.model.User;
import com.mascot.service.controller.AbstractController;
import com.mascot.service.controller.common.TableParams;
import com.mascot.service.controller.common.TableResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Николай on 26.02.2017.
 */
@RestController
@RequestMapping(value = "/orders")
public class OrdersController extends AbstractController {
    @Inject
    private OrderService orderService;

    @Inject
    private UserService userService;


    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('" + Role.ADMIN + "') or hasRole('" + Role.REGULAR + "')")
    public TableResult<OrderTableRecord> getList(@RequestBody TableParams params) {
        if (params.filter == null) {
            params.filter = new HashMap<>();
        }
        params.filter.put("user.id", "" + userService.getCurrentUserId());
        final BeanTableResult<Order> beanTableResult = orderService.getList(params.getStartIndex(), params.count,
                params.orderBy, params.filter);
        final Collection<Order> list = beanTableResult.getRows();
        final int totalCount = beanTableResult.getCount();

        final List<OrderTableRecord> result = list.stream().
                map(OrderTableRecord::build).
                collect(Collectors.toList());

        return TableResult.create(result.toArray(new OrderTableRecord[result.size()]), totalCount);
    }

}
