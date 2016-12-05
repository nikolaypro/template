package com.mascot.server.model.importdata;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

/**
 * Created by Nikolay on 05.12.2016.
 */
@Entity
@Table(name = "_Reference6995")
public class OneCJobSubType {
    @Id
    @Nationalized
    @Column(name = "_IDRRef")
    private String idRef;

    @Lob
    @Nationalized
    @Column(name = "_Description")
    private String description;

    public String getIdRef() {
        return idRef;
    }

    public void setIdRef(String idRef) {
        this.idRef = idRef;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
