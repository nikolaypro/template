package com.mascot.service.controller.common;

import com.mascot.service.security.MascotSession;

import java.util.Locale;

/**
 * Created by Nikolay on 17.05.2016.
 */
public class ResultRecord {
    public boolean success;
    public String message;

    public static ResultRecord success() {
        final ResultRecord resultRecord = new ResultRecord();
        resultRecord.success = true;
        return resultRecord;
    }

    public static ResultRecord fail(String message) {
        final ResultRecord resultRecord = new ResultRecord();
        resultRecord.success = false;
        resultRecord.message = message;
        return resultRecord;
    }

    public static ResultRecord failLocalized(String message, String... params) {
        final ResultRecord resultRecord = new ResultRecord();
        resultRecord.success = false;
        resultRecord.message = Localization.get(message, MascotSession.getCurrent().getLocale(), params);
        return resultRecord;
    }
}
