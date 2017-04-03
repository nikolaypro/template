package com.mascot.server.beans.integration;

import com.mascot.server.common.ServerUtils;
import com.mascot.server.model.*;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nikolay on 20.03.2017.
 */
public class ChangesFacade/*<A extends Identified & Versioned>*/ {
    private final Logger logger = Logger.getLogger(getClass());
    private final EntityManager em;
    private final EntityType entityType;
    private final EntityActionType actionType;
    private final ChangesAdapter adapter;

    ChangesFacade(EntityManager em, EntityType entityType, EntityActionType actionType) {
        this.em = em;
        this.entityType = entityType;
        this.actionType = actionType;
        this.adapter = createAdapter();
    }

    private ChangesAdapter createAdapter() {
        switch (actionType) {
            case INSERT: return new NewFinder();
            case UPDATE: return new UpdateFinder();
            case REMOVE: return new RemoveFinder();
        }
        throw new IllegalStateException("Unknown EntityActionType: '" + actionType + "'");
    }

    public List<Long> find() {
        List longs = adapter.find();
        if (longs == null) {
            return null;
        }
        return (List<Long>) longs.stream().map(e -> {
            if (e instanceof BigInteger) {
                return ((BigInteger)e).longValue();
            }
            if (e instanceof Long) {
                return e;
            }
            throw new IllegalArgumentException("Unsupported type: " + e.getClass().getName());
        }).collect(Collectors.toList());
    }

    void done(List<Long> entities) {
        ServerUtils.processBigCollection(entities, adapter::done);
    }

    private List<Long> executeFind(String sql, boolean findRemove) {
        final Query query = em.createNativeQuery(sql)
                .setParameter("entityType", this.entityType.name());
        if (entityType.hasWeb) {
            query.setParameter("web", true);
        }
        if (findRemove && entityType.selfRemove) {
            query.setParameter("deleted", true);
        }
        return query.getResultList();
    }

/*
    private void fillSentEntity(EntityActionType actionType, A e, SentEntity sentEntity) {
        sentEntity.setActionType(actionType);
        sentEntity.setEntityId(e.getId());
        sentEntity.setEntityType(entityType);
        sentEntity.setSendDate(new Date());
        sentEntity.setSentVersion(e.getVersion());
    }

*/
    private void executeDone(List<Long> entities, Query query, boolean isNative) {
        query.setParameter("actionType", isNative ? actionType.name() : actionType);
        query.setParameter("entityType", isNative ? entityType.name() : entityType);
        query.setParameter("sendDate", new Date());
        query.setParameter("ids", entities);
        query.executeUpdate();
//        em.flush();
//        em.clear();
    }
    private interface ChangesAdapter {
        List<Long> find();
        void done(List<Long> entities);
    }

    private class NewFinder implements ChangesAdapter {

        @Override
        public List<Long> find() {
            final String sql = "select e.id from " + entityType.tableName + " e " +
                    "left join sent_entity se ON e.id = se.entity_id and se.entity_type = :entityType " +
                    "where se.entity_id is null " + (entityType.hasWeb ? " and e.web = :web" : "");
            return executeFind(sql, false);
        }
        @Override
        public void done(List<Long> entities) {
            Query query = em.createQuery("insert into SentEntity (actionType, entityType, sendDate, entityId, sentVersion) " +
                    "select :actionType, :entityType, :sendDate, u.id, u.version from " + entityType.entityName + " u " +
                    "where u.id in (:ids)");
/*
                Query query = em.createNativeQuery("insert into sent_entity (action_type, entity_type, send_date, entity_id, sent_version) " +
                        "select :actionType, :entityType, :sentDate, u.id, u.version from users u " +
                        "where u.id in (:ids)");
*/
            executeDone(entities, query, false);
        }

    }

    private class UpdateFinder implements ChangesAdapter {

        @Override
        public List<Long> find() {
            final String sql = "select e.id from " + entityType.tableName + " e " +
                    "inner join sent_entity se ON e.id = se.entity_id and se.entity_type = :entityType " +
                    "where se.sent_version <> e.version " + (entityType.hasWeb ? " and e.web = :web" : "");
            return executeFind(sql, false);
        }

        @Override
        public void done(List<Long> entities) {
            Query query = em.createNativeQuery(
                    "update " +
                    "  sent_entity " +
                    "inner join " +
                    "  users u ON u.id = entity_id " +
                    "set " +
                    "  action_type = :actionType, " +
                    "  send_date = :sendDate, " +
                    "  sent_version = u.version " +
                    "where " +
                    "  entity_id in (:ids) and entity_type = :entityType");
            executeDone(entities, query, true);

/*
            entities.forEach(e -> {
                SentEntity sentEntity;
                try {
                    sentEntity =
                            (SentEntity) em.createQuery("select distinct _se_ from SentEntity _se_ where _se_.entityId = :id and _se_.entityType = :type")
                                    .setParameter("id", e)
                                    .setParameter("type", entityType)
                                    .getSingleResult();
                } catch (NoResultException e1) {
                    logger.warn("Not found SentEntity for entityId = " + e + " and type = " + entityType);
                    return;
                } catch (NonUniqueResultException e1) {
                    logger.warn("Found more then one SentEntity for entityId = " + e + " and type = " + entityType);
                    return;
                }
                fillSentEntity(actionType, e, sentEntity);
                em.merge(sentEntity);
            });
*/

        }
    }

    private class RemoveFinder implements ChangesAdapter {

        @Override
        public List<Long> find() {
            final String sql = "select se.entity_id from sent_entity se " +
                    "left join " + entityType.tableName + " e ON e.id = se.entity_id " +
                    (entityType.hasWeb ? " and e.web = :web " : " ") +
                    (entityType.selfRemove ? " and e.deleted = :deleted " : " ") +
                    "where e.id is null and se.entity_type = :entityType";

            return executeFind(sql, true);
        }

        @Override
        public void done(List<Long> entities) {
            em.createQuery("delete from SentEntity _se_ where _se_.entityId in (:ids) and _se_.entityType = :entityType")
                    .setParameter("ids", entities)
                    .setParameter("entityType", entityType)
                    .executeUpdate();



/*
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
*/

        }
    }

}
