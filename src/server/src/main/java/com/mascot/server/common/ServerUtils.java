package com.mascot.server.common;

import java.nio.file.Paths;

/**
 * Created by Nikolay on 13.10.2016.
 */
public class ServerUtils {
    public static String getConfPath() {
        return Paths.get(System.getProperty("catalina.base"), "conf").toString();
    }
}
