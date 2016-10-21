package com.mascot.server.beans;

import com.mascot.server.common.BeanTableResult;
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

    User getCurrentUser();

    Long getCurrentUserId();

    Role getRole(String roleName);

    BeanTableResult<User> getList(int start, int count, Map<String, String> orderBy);

    void update(User user);

    boolean remove(Long userId);

    User find(Long userId);
}
