package com.mascot.server.beans;

import com.mascot.server.common.BeanTableResult;
import com.mascot.server.model.Order;
import com.mascot.server.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Николай on 25.02.2017.
 */
@Service(OrderService.NAME)
@Transactional(propagation = Propagation.REQUIRED)
public class OrderServiceImpl extends AbstractMascotService implements OrderService {
    @Override
    public Order find(Long id) {
        try {
            return (Order) em.createQuery("select e from Order e " +
                    "left join fetch e.clothLines cl " +
                    "left join fetch e.productLines pl " +
                    "left join fetch e.user " +
                    "left join fetch cl.cloth " +
                    "left join fetch pl.product " +
                    "left join fetch pl.mainCloth " +
                    "left join fetch pl.compCloth1 " +
                    "left join fetch pl.compCloth2 " +
                    "where e.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void update(Order entity) {
        super.update(entity);
    }

    @Override
    public BeanTableResult<Order> getList(int startIndex, int count, Map<String, String> orderBy, Map<String, String> filter) {
        return super.getResult("select e from Order e", "select count(e) from Order e", startIndex, count, orderBy, new HashMap(), filter);
    }
}
