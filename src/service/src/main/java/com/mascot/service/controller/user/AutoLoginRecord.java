package com.mascot.service.controller.user;

import com.mascot.common.MascotUtils;

/**
 * Created by Николай on 05.01.2017.
 */
public class AutoLoginRecord {
    public String login;
    public String password;
    public Boolean enabled;

    public AutoLoginRecord(String login, String password) {
        this.login = login;
        this.password = password;
        this.enabled = !MascotUtils.isEmpty(login) && !MascotUtils.isEmpty(password);
    }
}
