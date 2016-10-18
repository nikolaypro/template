package com.mascot.server.beans.report;

import com.mascot.common.MascotUtils;
import com.mascot.server.model.Role;
import com.mascot.server.model.User;

import java.util.StringJoiner;

/**
 * Created by Nikolay on 13.10.2016.
 */
public class UserReportItem {
    private String login;
    private String fullName;
    private String roles;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public static UserReportItem create(User user) {
        UserReportItem result = new UserReportItem();
        result.login = user.getLogin();
        result.fullName = user.getFullName();
        final StringJoiner roles = new StringJoiner(",");
        for (Role s : user.getRoles()) {
            roles.add(s.getName());
        }
        result.roles = roles.toString();
        return result;
    }
}
