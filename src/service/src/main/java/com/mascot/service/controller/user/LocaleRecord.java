package com.mascot.service.controller.user;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by Nikolay on 12.09.2016.
 */
public class LocaleRecord {
    String id;

/*
    public LocaleRecord(String id) {
        this.id = id;
    }
*/

    public LocaleRecord() {
    }

    public LocaleRecord(Locale locale) {
        this.id = locale.getLanguage() + "_" + locale.getCountry();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

    public Locale asLocale() {
        final String[] split = id.split("_");
        if (split.length != 2) {
            throw new IllegalArgumentException("Incorrect Locale: '" + id + "'");
        }
        return new Locale(split[0], split[1]);
    }

}
