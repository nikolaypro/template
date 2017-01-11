package com.mascot.common;


import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;

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

    public static ZonedDateTime getStartWeek(ZonedDateTime date) {
/*
        date = date.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime result = date.minusDays(date.getDayOfWeek().getValue());
        return new Date(result.toInstant().toEpochMilli());
*/

        date = date.withZoneSameInstant(ZoneId.systemDefault());
        TemporalField fieldISO = WeekFields.of(Locale.UK).dayOfWeek();
        return date.with(fieldISO, 1);
    }

    public static ZonedDateTime getEndWeek(ZonedDateTime date) {
/*
        date = date.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime result = date.plusDays(6 - date.getDayOfWeek().getValue());
        return new Date(result.toInstant().toEpochMilli());

*/
        date = date.withZoneSameInstant(ZoneId.systemDefault());
        TemporalField fieldISO = WeekFields.of(Locale.UK).dayOfWeek();
        return date.with(fieldISO, 7);
    }

    public static Date toDate(ZonedDateTime date) {
        return new Date(date.toInstant().toEpochMilli());
    }

    public static ZonedDateTime toDefaultZonedDateTime(Date date) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
    }

    public static boolean equalsOrBothEmpty(String o1, String o2) {
        if (o1 == null) {
            o1 = "";
        }
        if (o2 == null) {
            o2 = "";
        }
        return o1.equals(o2);
    }


}
