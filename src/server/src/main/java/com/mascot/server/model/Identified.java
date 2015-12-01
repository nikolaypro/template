package com.mascot.server.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Nikolay on 01.12.2015.
 */
@MappedSuperclass
public class Identified implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
