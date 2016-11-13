package com.mascot.common;


import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Created by Nikolay on 09.12.2015.
 */
public class MascotUtils {
    public static boolean isEmpty(Object s) {
        if (s == null) {
            return true;
        }
        if (s instanceof Collection) {
            return ((Collection) s).isEmpty();
        }
        if (s instanceof Map) {
            return ((Map) s).isEmpty();
        }
        return isEmpty(s.toString());
    }
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }


    public static String buildOrderByString(Map<String, String> orderBy, String alias) {
        String orderByStr = "";
        if (orderBy != null && !orderBy.isEmpty()) {
            orderByStr = orderBy.entrySet().
                    stream().reduce(new StringJoiner(",", "order by ", ""),
                    (x, y) -> x.add(alias + "." + y.getKey() + " " + y.getValue()),
                    StringJoiner::merge).toString();
        }
        return orderByStr;
    }

    public static String buildWhereByString(Map<String, String> filter, String alias) {
        String filterByStr = "";
        if (filter != null && !filter.isEmpty()) {
            filterByStr = filter.entrySet().
                    stream().reduce(new StringJoiner(" and ", "where ", ""),
                    (x, y) -> x.add(alias + "." + y.getKey() + " like '%" + y.getValue() + "%'"),
                    StringJoiner::merge).toString();
        }
        return filterByStr;
    }

    public static String buildCommaSeparatedString(String... list) {
        final StringJoiner result = new StringJoiner(",");
        for (String s : list) {
            result.add(s);
        }
        return result.toString();
    }
}
