package com.mascot.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Nikolay on 16.12.2015.
 */
@Entity
@Table(name = "roles")
public class Role extends Identified {
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String REGULAR = "ROLE_REGULAR";

/*
    public static String get(String... roles) {
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < roles.length; i++) {
            result.append("hasRole('").append(roles[i]).append("')");
            if (i < roles.length - 1) {
                result.append(" or ");
            }
        }
        return result.toString();
    }
*/

    @Column
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
