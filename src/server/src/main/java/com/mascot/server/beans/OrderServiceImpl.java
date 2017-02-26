package com.mascot.server.beans;

import com.mascot.server.model.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

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
}
