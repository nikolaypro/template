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
public class ChangesFacade<A extends Identified & Versioned> {
    private final EntityManager em;
    private final EntityType entityType;
    protected final Logger logger = Logger.getLogger(getClass());
    private static final Map<EntityType, String> type2Select = new HashMap<>();
    static {
        type2Select.put(EntityType.USER, "select distinct e from User e left join fetch e.roles");
    }

    public ChangesFacade(EntityManager em, EntityType entityType) {
        this.em = em;
        this.entityType = entityType;
    }

    public List<A> find(EntityActionType type) {
        switch (type) {
            case INSERT: return findNew();
            case UPDATE: return findUpdated();
        }
        throw new IllegalStateException("Unknown EntityActionType");
    }

    private List<A> findNew() {
        final String selectEntity = type2Select.get(entityType);
        return em.createQuery(selectEntity +
                " where not exists(" +
                "   select _se_.id " +
                "   from SentEntity _se_ " +
                "   where _se_.entityId = e.id and _se_.entityType = :entityType)")
                .setParameter("entityType", this.entityType)
                .getResultList();

    }

    private List<A> findUpdated() {
        final String selectEntity = type2Select.get(entityType);
        return em.createQuery(selectEntity +
                " where exists(" +
                "   select _se_.id " +
                "   from SentEntity _se_ " +
                "   where _se_.entityId = e.id and _se_.entityType = :entityType and _se_.sentVersion <> e.version)")
                .setParameter("entityType", this.entityType)
                .getResultList();

    }

    public void done(List<A> entities, EntityActionType actionType) {
        switch (actionType) {
            case INSERT: {
                entities.forEach(e -> {
                    final SentEntity sentEntity = new SentEntity();
                    sentEntity.setActionType(actionType);
                    sentEntity.setEntityId(e.getId());
                    sentEntity.setEntityType(entityType);
                    sentEntity.setSendDate(new Date());
                    sentEntity.setSentVersion(e.getVersion());
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
                                        .setParameter("id", e.getId())
                                        .setParameter("type", entityType)
                                .getSingleResult();
                    } catch (NoResultException e1) {
                        logger.warn("Not found SentEntity for entityId = " + e.getId() + " and type = " + entityType);
                        return;
                    } catch (NonUniqueResultException e1) {
                        logger.warn("Found more then one SentEntity for entityId = " + e.getId() + " and type = " + entityType);
                        return;
                    }
                    sentEntity.setActionType(actionType);
                    sentEntity.setEntityId(e.getId());
                    sentEntity.setEntityType(entityType);
                    sentEntity.setSendDate(new Date());
                    sentEntity.setSentVersion(e.getVersion());
                    em.merge(sentEntity);
                });
                return;
            }
        }
        throw new IllegalStateException("Unknown action type: '" + actionType + "'");
    }
}
