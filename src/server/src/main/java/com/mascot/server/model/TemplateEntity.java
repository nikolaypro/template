package com.mascot.server.model;

import javax.persistence.*;

/**
 * Created by Nikolay on 15.12.2014.
 */
@Entity
@Table(name = "template_table")
public class TemplateEntity {
    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
