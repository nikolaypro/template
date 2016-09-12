package com.mascot.service.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Locale;

/**
 * Created by Nikolay on 09.09.2016.
 */
public class MascotSession extends User {
    private final Long userId;
    private final Locale locale;

    public MascotSession(String username, String password, Collection<? extends GrantedAuthority> authorities,
                         Long userId, Locale locale) {
        super(username, password, authorities);
        this.userId = userId;
        this.locale = locale;
    }

    public Long getUserId() {
        return userId;
    }

    public Locale getLocale() {
        return locale;
    }

    public static MascotSession getCurrent() {
        return (MascotSession) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
