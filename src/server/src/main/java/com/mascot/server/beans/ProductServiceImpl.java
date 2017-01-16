package com.mascot.server.beans;

import com.mascot.common.MailSender;
import com.mascot.server.common.BeanTableResult;
import com.mascot.server.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nikolay on 19.10.2016.
 */
@Service(ProductService.NAME)
@Transactional(propagation = Propagation.REQUIRED)
public class ProductServiceImpl extends AbstractMascotService implements ProductService {

    @Override
    public BeanTableResult<Product> getList(int start, int count, Map<String, String> orderBy) {
        return getResult("select distinct e from Product e",
                "select count(distinct e) from Product e", start, count, orderBy, new HashMap<>(), new HashMap<>(), true, null);
    }

    @Override
    public void update(Product entity) {
        super.update(entity);
    }

    @Override
    public boolean remove(Long id) {
        em.createQuery("update JobSubTypeCost e set e.deleted = true where e.product.id = :id").
                setParameter("id", id).
                executeUpdate();
        return markAsDeleted(Product.class, id);
//        return remove(Product.class, id);
    }

    @Override
    public Product find(Long id) {
        try {
            return (Product) em.createQuery("select e from Product e where e.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;

        } catch (NonUniqueResultException e) {
            MailSender.sendErrorAsync("NonUniqueResultException for product id = '" + id + "'", e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Product findByName(String name) {
        try {
            return (Product) em.createQuery("select e from Product e where e.name = :name and e.deleted <> true")
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;

        } catch (NonUniqueResultException e) {
            MailSender.sendErrorAsync("NonUniqueResultException for product name = '" + name + "'", e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Product> getAll() {
/*
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
        return em.createQuery("select e from Product e where e.deleted <> true").getResultList();
    }

}
