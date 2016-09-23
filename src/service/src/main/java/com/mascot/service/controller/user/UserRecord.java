package com.mascot.service.controller.user;

import com.mascot.server.model.Role;
import com.mascot.server.model.User;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Nikolay on 06.05.2016.
 */
public class UserRecord {
    public Long id;

    public String fullName;

    public String login;

    public String password;

    public LocaleRecord locale;

    public Set<String> roles = new HashSet<String>();

    public static UserRecord build(User user) {
        if (user == null) {
            return null;
        }
        final UserRecord result = new UserRecord();
        result.id = user.getId();
        result.fullName = user.getFullName();
        result.login = user.getLogin();
        if (user.getRoles() != null) {
            result.roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        }
        result.locale = new LocaleRecord(user.getLocale() != null ? user.getLocale() : Locale.getDefault());
        return result;
    }
}
