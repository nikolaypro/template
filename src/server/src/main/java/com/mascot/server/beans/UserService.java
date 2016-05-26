package com.mascot.server.beans;

import com.mascot.server.model.Role;
import com.mascot.server.model.User;

import java.util.Collection;

/**
 * Created by Nikolay on 16.12.2015.
 */
public interface UserService {
    String NAME = "UserService";

    public User loadUserByLogin(String login);

    Collection<User> getUsers();

    User getCurrentUser();

    Long getCurrentUserId();

    Role getRole(String roleName);

    void saveUser(User user);

    boolean removeUser(Long userId);
}
