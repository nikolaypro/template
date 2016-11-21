package com.mascot.server.beans;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.mascot.common.MailSender;
import com.mascot.common.MascotUtils;
import com.mascot.server.common.BeanTableResult;
import com.mascot.server.model.Identified;
import com.mascot.server.model.IdentifiedDeleted;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
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
                                               Map<String, Object> params, Map<String, String> filter) {
        return getResult(queryStr, countQueryStr, start, count, orderBy, params, filter, false);
    }

    protected Map<String, Object> deletedMap() {
        final Map<String, Object> res = new HashMap<>();
        res.put("deleted", true);
        return res;
    }

    protected <A> BeanTableResult<A> getResult(String queryStr, String countQueryStr,
                                               int start, int count, Map<String, String> orderBy,
                                               Map<String, Object> params, Map<String, String> filter,
                                               boolean checkDeleted) {
/*
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/
        final String orderByStr = MascotUtils.buildOrderByString(orderBy, "e");
        String whereStr = MascotUtils.buildWhereByString(filter, "e");
        whereStr = correctWhereStrIfNeeds(queryStr, whereStr);

        queryStr = queryStr + " " + whereStr;

        if (checkDeleted) {
            String deletedQuery = "where (e.deleted is null or e.deleted = :deleted)";
            deletedQuery = correctWhereStrIfNeeds(queryStr, deletedQuery);
            queryStr = queryStr + " " + deletedQuery;
            params.put("deleted", false);
        }
        final Query query = em.createQuery(queryStr + " " + orderByStr).
                setMaxResults(count).
                setFirstResult(start);
        params.entrySet().stream().forEach(e -> query.setParameter(e.getKey(), e.getValue()));

        final List<A> resultList = query.getResultList();

        // Count
        whereStr = MascotUtils.buildWhereByString(filter, "e");
        whereStr = correctWhereStrIfNeeds(countQueryStr, whereStr);
        countQueryStr = countQueryStr + " " + whereStr;
        if (checkDeleted) {
            String deletedQuery = "where (e.deleted is null or e.deleted = :deleted)";
            deletedQuery = correctWhereStrIfNeeds(countQueryStr, deletedQuery);
            countQueryStr = countQueryStr + " " + deletedQuery;
        }
        final Query countQuery = em.createQuery(countQueryStr);
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

    protected <A extends Identified> boolean remove(Class<A> aClass, Long id) {
        final A reference = em.getReference(aClass, id);
        em.remove(reference);
        return true;
    }

    protected <A extends IdentifiedDeleted> boolean markAsDeleted(Class<A> aClass, Long id) {
        em.createQuery("update " + aClass.getSimpleName() + " e set e.deleted = :deleted where e.id = :id").
                setParameter("deleted", true).
                setParameter("id", id).executeUpdate();
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
            MailSender.sendErrorAsync("NonUniqueResultException for entityClass: " + entityClass.getSimpleName() + ", id = " + id, e);
            throw new IllegalStateException(e);
        }
    }

    protected Date parseISODate(String dateStr) {
        try {
            return StdDateFormat.instance.parse(dateStr);
        } catch (Exception e) {
            MailSender.sendErrorAsync("Unable parse a date: '" + dateStr + "'", e);
        }
        return null;

    }





}
