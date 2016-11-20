package com.mascot.server.beans;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.mascot.common.MascotUtils;
import com.mascot.server.common.BeanTableResult;
import com.mascot.server.model.Identified;
import com.mascot.server.model.Product;
import org.apache.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateFormatter;

import javax.persistence.*;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
                                               Map<String, Object> params, Map<String, String> filter) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final String orderByStr = MascotUtils.buildOrderByString(orderBy, "e");
        String whereStr = MascotUtils.buildWhereByString(filter, "e");
        whereStr = correctWhereStrIfNeeds(queryStr, whereStr);
        final Query query = em.createQuery(queryStr + " " + whereStr + " " + orderByStr).
                setMaxResults(count).
                setFirstResult(start);
        params.entrySet().stream().forEach(e -> query.setParameter(e.getKey(), e.getValue()));

        final List<A> resultList = query.getResultList();

        // Count
        whereStr = MascotUtils.buildWhereByString(filter, "e");
        whereStr = correctWhereStrIfNeeds(countQueryStr, whereStr);
        final Query countQuery = em.createQuery(countQueryStr + " " + whereStr);
        params.entrySet().stream().forEach(e -> countQuery.setParameter(e.getKey(), e.getValue()));
        final Long totalCount = (Long) countQuery.getSingleResult();
        return new BeanTableResult<A>(resultList, totalCount.intValue());
    }

    private String correctWhereStrIfNeeds(String queryStr, String whereStr) {
        if (queryStr.toUpperCase().contains("WHERE")) {
            whereStr = whereStr.replace("where", " and ");
        }
        return whereStr;
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

    protected Date parseISODate(String dateStr) {
        try {
            return StdDateFormat.instance.parse(dateStr);
        } catch (Exception e) {
            logger.error("Unable parse a date", e);
        }
        return null;

    }





}
