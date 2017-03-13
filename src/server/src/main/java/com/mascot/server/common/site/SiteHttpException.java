package com.mascot.server.common.site;

/**
 * Created by Nikolay on 10.03.2017.
 */
public class SiteHttpException extends Exception {
    public SiteHttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public SiteHttpException(String message) {
        super(message);
    }

}
