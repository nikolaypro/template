package com.mascot.server.common;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Nikolay on 13.10.2016.
 */
public class ServerUtils {
    public static String getConfPath() {
        return Paths.get(System.getProperty("catalina.base"), "conf").toString();
    }

    public static String getReportsLogPath() {
        return Paths.get(System.getProperty("catalina.base"), "conf", "report-log").toString();
    }

    public static Path getSalaryReportLogPath() {
        return Paths.get(System.getProperty("catalina.base"), "conf", "report-log", "salary");
    }
}
