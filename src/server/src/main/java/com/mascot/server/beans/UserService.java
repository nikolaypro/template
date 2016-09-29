package com.mascot.server.beans;

import com.mascot.server.model.Role;
import com.mascot.server.model.User;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Nikolay on 16.12.2015.
 */
public interface UserService {
    String NAME = "UserService";

    public User loadUserByLogin(String login);

    Collection<User> getUsers(int start, int count, Map<String, String> orderBy);

    User getCurrentUser();

    Long getCurrentUserId();

    Role getRole(String roleName);

    void saveUser(User user);

    boolean removeUser(Long userId);

    User findUser(Long userId);

    int getUsersCount();
}
