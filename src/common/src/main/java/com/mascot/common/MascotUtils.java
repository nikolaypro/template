package com.mascot.common;


import java.util.Collection;
import java.util.Map;

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


}
