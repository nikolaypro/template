package com.mascot.service.controller.user;

import com.mascot.server.model.Role;
import com.mascot.server.model.User;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Nikolay on 06.05.2016.
 */
public class UserRecord {
    public String fullName;

    public String login;

    public Set<String> roles = new HashSet<String>();

    public static UserRecord build(User user) {
        if (user == null) {
            return null;
        }
        final UserRecord result = new UserRecord();
        result.fullName = user.getFullName();
        result.login = user.getLogin();
        if (user.getRoles() != null) {
            result.roles = new HashSet<>();
            for (Role role : user.getRoles()) {
                result.roles.add(role.getName());
            }
        }
        return result;
    }
}
