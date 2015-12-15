package com.mascot.server.model;

import javax.persistence.*;

/**
 * Created by Nikolay on 01.12.2015.
 */
@Entity
@Table(name = "users")
public class User extends Identified {
    @Column
    private String login;

    @Column
    private String fullName;

    @Column
    private String password;

    public String getLogin() {
        return login;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return login;
    }
}
