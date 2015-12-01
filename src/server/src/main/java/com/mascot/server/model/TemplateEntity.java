package com.mascot.server.model;

import javax.persistence.*;

/**
 * Created by Nikolay on 15.12.2014.
 */
@Entity
@Table(name = "template_table")
public class TemplateEntity extends Identified {
    @Column
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
