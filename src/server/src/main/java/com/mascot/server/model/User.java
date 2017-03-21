package com.mascot.server.model;

import org.hibernate.annotations.Type;
import org.hibernate.type.LocaleType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Created by Nikolay on 01.12.2015.
 */
@Entity
@Table(name = "users")
public class User extends Identified implements Versioned {
    @Column
    private String login;

    @Column(name = "full_name")
    private String fullName;

    @Column
    private String password;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"})
    )
    private Set<Role> roles = new HashSet<Role>();

    @Column
    @Type(type = "org.hibernate.type.LocaleType" )
    private Locale locale;

    @Version
    @Column(name = "version")
    private Long version = 0L;

    @Column(name = "web")
    private Boolean web;

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Boolean getWeb() {
        return web;
    }

    public void setWeb(Boolean web) {
        this.web = web;
    }
}
