package com.mascot.server.common;

import org.apache.log4j.Logger;

import javax.persistence.Table;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Nikolay on 13.10.2016.
 */
public class ServerUtils {

    private static final int COLLECTION_BATCH_SIZE = 500;

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

    public static String getTableName(Class entityClass) {
        Table table = (Table) entityClass.getAnnotation(Table.class);
        return table.name();
    }

    public static <E, T> List<E> loadBigCollection(List<T> idList, Function<List<T>, List<E>> function) {
        return loadBigCollection(idList, COLLECTION_BATCH_SIZE, function);
    }

    public static <E, T> List<E> loadBigCollection(List<T> idList, int batchSize, Function<List<T>, List<E>> function) {
        List<E> list = new ArrayList<E>(idList.size());
        int i = 0;
        while (i < idList.size()) {
            List<T> subList = idList.subList(i, Math.min(i + batchSize, idList.size()));
            i += batchSize;
            list.addAll(function.apply(subList));
        }
        return list;
    }

    public static <T> void processBigCollection(List<T> idList, Consumer<List<T>> function) {
        processBigCollection(idList, COLLECTION_BATCH_SIZE, function);
    }

    public static <T> void processBigCollection(List<T> idList, int batchSize, Consumer<List<T>> function) {
        int i = 0;
        while (i < idList.size()) {
            List<T> subList = idList.subList(i, Math.min(i + batchSize, idList.size()));
            i += batchSize;
            function.accept(subList);
        }
    }

}
