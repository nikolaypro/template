package com.mascot.server.model;

import javax.persistence.*;

/**
 * Created by Nikolay on 01.12.2015.
 */
@Entity
@Table(name = "users")
public class User extends Identified {
    @Column
    private String name;

    @Column
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
}
