package com.mascot.service.controller.integration.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Nikolay on 14.03.2017.
 */
public class ExtUser {
    public Long id;

    public String fullName;

    public String login;

    public String password;

    public String locale;

    public Set<String> roles = new HashSet<String>();

}
