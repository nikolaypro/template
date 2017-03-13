package com.mascot.server.beans.integration.dto;

import com.mascot.common.MascotUtils;
import com.mascot.server.model.Role;
import com.mascot.server.model.User;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Nikolay on 13.03.2017.
 */
public class SiteUser {
    public Long id;

    public String fullName;

    public String login;

    public String password;

    public String locale;

    public Set<String> roles = new HashSet<String>();

    public static SiteUser build(User user) {
        if (user == null) {
            return null;
        }
        final SiteUser result = new SiteUser();
        result.id = user.getId();
        result.fullName = user.getFullName();
        result.login = user.getLogin();
        result.password = user.getPassword();
        if (user.getRoles() != null) {
            result.roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        }
        result.locale = MascotUtils.localeAsString(user.getLocale() != null ? user.getLocale() : Locale.getDefault());
        return result;
    }

}
