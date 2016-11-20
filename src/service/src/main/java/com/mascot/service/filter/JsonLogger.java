package com.mascot.service.filter;

import com.mascot.common.MascotUtils;
import com.mascot.service.common.CommonUtils;
import org.apache.log4j.Logger;

/**
 * Created by Николай on 20.11.2016.
 */
class JsonLogger {
    static final String HTTP_REQUEST_LOGGER_NAME = "http-request";
    private static final Logger logger = Logger.getLogger(HTTP_REQUEST_LOGGER_NAME);
    private final long id;
    private final String type;

    JsonLogger(long id, String type) {
        this.id = id;
        this.type = type;
    }

    void log(String json) {
        final String formatted = CommonUtils.formatJson(json);
        logger.info(id + ": http " + type + " " + json +
                (MascotUtils.isEmpty(formatted) ? "" : "\n formatted: " + formatted));
    }
}
