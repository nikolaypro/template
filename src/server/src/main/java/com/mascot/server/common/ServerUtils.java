package com.mascot.server.common;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * Created by Nikolay on 13.10.2016.
 */
public class ServerUtils {
    private static final Logger logger = Logger.getLogger(ServerUtils.class);

    public static String getConfPath() {
        return Paths.get(System.getProperty("catalina.base"), "conf").toString();
    }

    public static String getReportsLogPath() {
        return Paths.get(System.getProperty("catalina.base"), "conf", "report-log").toString();
    }

    public static Path getSalaryReportLogPath() {
        return Paths.get(System.getProperty("catalina.base"), "conf", "report-log", "salary");
    }

    public static String read(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }

    public static String getAppVersion() {
        try {
            final InputStream resourceAsStream = ServerUtils.class.getResourceAsStream("/META-INF/build.version");
            return read(resourceAsStream);
        } catch (Exception e) {
            logger.error("Unable read a application version", e);
        }
        return "unknown";
    }
}
