package com.mascot.server.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by Nikolay on 21.11.2016.
 */
@MappedSuperclass
public class IdentifiedDeleted extends Identified {
    @Column
    private Boolean deleted = false;

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
