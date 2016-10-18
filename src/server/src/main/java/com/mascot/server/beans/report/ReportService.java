package com.mascot.server.beans.report;

import com.mascot.server.model.User;

import java.util.List;

/**
 * Created by Nikolay on 12.10.2016.
 */
public interface ReportService {
    String NAME = "ReportService";

    byte[] usersReport();

    List<User> getUsers();
}