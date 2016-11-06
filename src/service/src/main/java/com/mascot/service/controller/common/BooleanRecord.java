package com.mascot.service.controller.common;

import com.mascot.service.security.MascotSession;

/**
 * Created by Николай on 06.11.2016.
 */
public class BooleanRecord {
    public boolean result;
    public String message;

    protected BooleanRecord() {
        this.result = true;
    }

    protected BooleanRecord(String message) {
        this.result = false;
        this.message = message;
    }

    protected BooleanRecord(String message, String... params) {
        this.result = false;
        this.message = Localization.get(message, MascotSession.getCurrent().getLocale(), params);
    }

    public static BooleanRecord trueResult() {
        return new BooleanRecord();
    }

    public static BooleanRecord falseResult(String message) {
        return new BooleanRecord(message);
    }

    public static BooleanRecord falseResult() {
        return new BooleanRecord("");
    }

    public static BooleanRecord falseLocalized(String message, String... params) {
        return new BooleanRecord(message, params);
    }

}
