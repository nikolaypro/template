package com.mascot.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Nikolay on 07.04.2017.
 */
@Entity
@Table(name = "integration_log")
public class IntegrationLog extends Identified {
    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "action_type")
    private IntegrationActionType actionType;

    @Column(name = "count")
    private Integer count;

    @Column(name = "comment")
    private String comment;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public IntegrationActionType getActionType() {
        return actionType;
    }

    public void setActionType(IntegrationActionType actionType) {
        this.actionType = actionType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
