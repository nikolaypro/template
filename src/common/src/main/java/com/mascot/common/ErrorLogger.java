package com.mascot.common;

import org.apache.log4j.Logger;

/**
 * Created by Nikolay on 21.11.2016.
 */
public class ErrorLogger {
    public static void error(Logger logger, String errorMessage, Throwable e) {
        logger.error(errorMessage, e);
        MailSender.sendErrorAsync(errorMessage, e);
    }
}
