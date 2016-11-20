package com.mascot.service.common;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by Николай on 20.11.2016.
 */
public class CommonUtils {
    public static String formatJson(String json) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final Object object = mapper.readValue(json, Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception e) {
            return json;
        }
    }
}
