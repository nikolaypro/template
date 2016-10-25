package com.mascot.server.beans;

import com.mascot.common.MascotUtils;
import com.mascot.server.common.BeanTableResult;
import com.mascot.server.model.Identified;
import com.mascot.server.model.Product;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * Created by Nikolay on 19.10.2016.
 */
public abstract class AbstractMascotService {
    protected final Logger logger = Logger.getLogger(getClass());

    @PersistenceContext
    protected EntityManager em;

    protected <A> BeanTableResult<A> getResult(String queryStr, String countQueryStr,
                                               int start, int count, Map<String, String> orderBy,
                                               Map<String, Object> params) {
        final String orderByStr = MascotUtils.buildOrderByString(orderBy, "e");
        final Query query = em.createQuery(queryStr + " " + orderByStr).
                setMaxResults(count).
                setFirstResult(start);
        params.entrySet().stream().forEach(e -> query.setParameter(e.getKey(), e.getValue()));

        final List<A> resultList = query.getResultList();

        final Long totalCount = (Long) em.createQuery(countQueryStr).getSingleResult();
        return new BeanTableResult<A>(resultList, totalCount.intValue());
    }

    protected void update(Identified entity) {
        if (entity.getId() == null) {
            em.persist(entity);
        } else {
            em.merge(entity);
        }
    }

    protected <A extends Identified> boolean remove(Class<A> aClass, Long userId) {
        final A reference = em.getReference(aClass, userId);
        em.remove(reference);
        return true;
    }

    protected <A extends Identified> A find(Long id, Class entityClass) {
        try {
            return (A) em.createQuery("select e from " + entityClass.getSimpleName() + " e where e.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;

        } catch (NonUniqueResultException e) {
            throw new IllegalStateException(e);
        }
    }





}
