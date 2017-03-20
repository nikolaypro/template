package com.mascot.server.beans.integration;

import com.mascot.server.model.*;

import javax.persistence.EntityManager;
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
    private static final Map<EntityType, String> type2Select = new HashMap<>();
    static {
        type2Select.put(EntityType.USER, "select u from User u left join fetch u.roles");
    }

    public ChangesFacade(EntityManager em, EntityType entityType) {
        this.em = em;
        this.entityType = entityType;
    }

    public List<A> find(EntityActionType type) {
        switch (type) {
            case INSERT: return findNew();
        }
        throw new IllegalStateException("Unknown EntityActionType");
    }

    private List<A> findNew() {
        final String selectEntity = type2Select.get(entityType);
        return em.createQuery(selectEntity +
                " where not exists(" +
                "   select _se_.id " +
                "   from SentEntity _se_ " +
                "   where _se_.entityId = u.id and _se_.entityType = :entityType)")
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
        }
        throw new IllegalStateException("Unknown action type: '" + actionType + "'");
    }
}
