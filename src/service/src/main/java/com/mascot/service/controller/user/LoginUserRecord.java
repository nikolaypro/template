package com.mascot.service.controller.user;

import com.mascot.server.model.User;

/**
 * Created by Nikolay on 24.11.2016.
 */
public class LoginUserRecord extends UserRecord {
    public String appVersion;

    public static LoginUserRecord build(User user, String appVersion) {
        if (user == null) {
            return null;
        }

        final UserRecord userRecord = UserRecord.build(user);

        LoginUserRecord result = new LoginUserRecord();
        result.id = userRecord.id;
        result.fullName = userRecord.fullName;
        result.login = userRecord.login;
        result.roles = userRecord.roles;
        result.locale = userRecord.locale;
        result.appVersion = appVersion;

        return result;
    }

}
