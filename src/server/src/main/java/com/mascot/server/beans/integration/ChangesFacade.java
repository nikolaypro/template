package com.mascot.server.beans.integration;

import com.mascot.server.model.*;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nikolay on 20.03.2017.
 */
public class ChangesFacade<A> {
    private final EntityManager em;
    private final EntityType entityType;
    protected final Logger logger = Logger.getLogger(getClass());
    private static final Map<EntityType, String> type2Select = new HashMap<>();
    private static final Map<EntityType, String> type2SelectForRemove = new HashMap<>();
    static {
        type2Select.put(EntityType.USER, "select distinct e from User e left join fetch e.roles where e.web = :web and");
        type2SelectForRemove.put(EntityType.USER, "select e from User e where e.web = :web and");
    }

    ChangesFacade(EntityManager em, EntityType entityType) {
        this.em = em;
        this.entityType = entityType;
    }

    public List<A> find(EntityActionType type) {
        switch (type) {
            case INSERT: return findNew();
            case UPDATE: return findUpdated();
            case REMOVE: return findRemoved();
        }
        throw new IllegalStateException("Unknown EntityActionType");
    }

    private List<A> findNew() {
        final String selectEntity = type2Select.get(entityType);
        return em.createQuery(selectEntity +
                " not exists(" +
                "   select _se_.id " +
                "   from SentEntity _se_ " +
                "   where _se_.entityId = e.id and _se_.entityType = :entityType)")
                .setParameter("entityType", this.entityType)
                .setParameter("web", true)
                .getResultList();

    }

    private List<A> findUpdated() {
        final String selectEntity = type2Select.get(entityType);
        return em.createQuery(selectEntity +
                " exists(" +
                "   select _se_.id " +
                "   from SentEntity _se_ " +
                "   where _se_.entityId = e.id and _se_.entityType = :entityType and _se_.sentVersion <> e.version)")
                .setParameter("entityType", this.entityType)
                .setParameter("web", true)
                .getResultList();

    }

    private List<A> findRemoved() {
        final String selectEntity = type2SelectForRemove.get(entityType);
        return em.createQuery("select _se_.entityId " +
                "   from SentEntity _se_ " +
                "   where _se_.entityType = :entityType" +
                "   and not exists(" + selectEntity + " e.id = _se_.entityId)")
                .setParameter("entityType", this.entityType)
                .setParameter("web", true)
                .getResultList();
    }

    void done(List<A> entities, EntityActionType actionType) {
        switch (actionType) {
            case INSERT: {
                entities.forEach(e -> {
                    final SentEntity sentEntity = new SentEntity();
                    fillSentEntity(actionType, e, sentEntity);
                    em.persist(sentEntity);
                });
                return;
            }
            case UPDATE: {
                entities.forEach(e -> {
                    SentEntity sentEntity;
                    try {
                        sentEntity =
                                (SentEntity) em.createQuery("select distinct _se_ from SentEntity _se_ where _se_.entityId = :id and _se_.entityType = :type")
                                        .setParameter("id", getAsIdentified(e).getId())
                                        .setParameter("type", entityType)
                                .getSingleResult();
                    } catch (NoResultException e1) {
                        logger.warn("Not found SentEntity for entityId = " + getAsIdentified(e).getId() + " and type = " + entityType);
                        return;
                    } catch (NonUniqueResultException e1) {
                        logger.warn("Found more then one SentEntity for entityId = " + getAsIdentified(e).getId() + " and type = " + entityType);
                        return;
                    }
                    fillSentEntity(actionType, e, sentEntity);
                    em.merge(sentEntity);
                });
                return;
            }
            case REMOVE: {
                entities.forEach(e -> {
                    try {
                        final int update = em.createQuery("delete from SentEntity _se_ where _se_.entityId = :id and _se_.entityType = :type")
                                .setParameter("id", e)
                                .setParameter("type", entityType)
                                .executeUpdate();
                        if (update < 1) {
                            logger.warn("Not removed SentEntity for entityId = " + e + " and type = " + entityType);
                        }
                        if (update > 1) {
                            logger.warn("Removed more then one SentEntity for entityId = " + e + " and type = " + entityType);
                        }
                    } catch (Exception e1) {
                        logger.error("Unable delete from SentEntity for entityId = " + e + " and type = " + entityType, e1);
                    }
                });
                return;
            }
        }
        throw new IllegalStateException("Unknown action type: '" + actionType + "'");
    }

    private void fillSentEntity(EntityActionType actionType, A e, SentEntity sentEntity) {
        sentEntity.setActionType(actionType);
        sentEntity.setEntityId(getAsIdentified(e).getId());
        sentEntity.setEntityType(entityType);
        sentEntity.setSendDate(new Date());
        sentEntity.setSentVersion(getAsVersioned(e).getVersion());
    }

    private Identified getAsIdentified(A e) {
        return (Identified) e;
    }

    private Versioned getAsVersioned(A e) {
        return (Versioned) e;
    }
}
