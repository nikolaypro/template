package com.mascot.server.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Nikolay on 20.03.2017.
 */
@Entity
@Table(name = "sent_entity")
public class SentEntity extends Identified {
    @Column(name = "send_date")
    private Date sendDate;

    @Column(name = "entity_id")
    private Long entityId;

    @Column(name = "action_type")
    @Enumerated(EnumType.STRING)
    private EntityActionType actionType;

    @Column(name = "entity_type")
    @Enumerated(EnumType.STRING)
    private EntityType entityType;

    @Column(name = "sent_version")
    private Long sentVersion;

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public EntityActionType getActionType() {
        return actionType;
    }

    public void setActionType(EntityActionType actionType) {
        this.actionType = actionType;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public Long getSentVersion() {
        return sentVersion;
    }

    public void setSentVersion(Long sentVersion) {
        this.sentVersion = sentVersion;
    }
}
