package com.mascot.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Nikolay on 18.01.2017.
 */
@Entity
@Table(name = "progress")
public class Progress extends Identified {
    @Column
    private String state;

    @Column
    private Integer value;

    @Column(name = "last_update")
    private Date lastUpdate;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
