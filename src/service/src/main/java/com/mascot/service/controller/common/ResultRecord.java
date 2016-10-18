package com.mascot.service.controller.common;

import com.mascot.service.security.MascotSession;

import java.util.Locale;

/**
 * Created by Nikolay on 17.05.2016.
 */
public class ResultRecord {
    public boolean success;
    public String message;

    protected ResultRecord() {
        this.success = true;
    }

    protected ResultRecord(String message) {
        this.success = false;
        this.message = message;
    }

    protected ResultRecord(String message, String... params) {
        this.success = false;
        this.message = Localization.get(message, MascotSession.getCurrent().getLocale(), params);
    }

    public static ResultRecord success() {
        return new ResultRecord();
    }

    public static ResultRecord fail(String message) {
        return new ResultRecord(message);
    }

    public static ResultRecord failLocalized(String message, String... params) {
        return new ResultRecord(message, params);
    }
}
