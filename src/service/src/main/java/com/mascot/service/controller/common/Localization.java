package com.mascot.service.controller.common;

import com.mascot.common.ErrorLogger;
import com.mascot.common.MailSender;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Nikolay on 08.09.2016.
 */
public class Localization {

    private final ResourceBundle bundle;
    private static final String BUNDLE_NAME = "messages";

    private static final Object sync = new Object();
    private static final ConcurrentHashMap<Locale, Localization> instances = new ConcurrentHashMap<>();

    private static final Logger logger = Logger.getLogger(Localization.class);

    public Localization(Locale locale) {
        final String bundleName = toBundleName(BUNDLE_NAME, locale);
        final InputStream resourceAsStream;
        try {
            resourceAsStream = getClass().getClassLoader().getResourceAsStream(bundleName);
        } catch (Exception e) {
            ErrorLogger.error(logger, "Unable get a bundle '" + bundleName + "'", e);;
            throw new IllegalStateException(e);
        }
        try (Reader r = new InputStreamReader(resourceAsStream, Charset.forName("UTF-8"))) {
            this.bundle = new PropertyResourceBundle(r);
        } catch (IOException e) {
            ErrorLogger.error(logger, "Unable get reader for bundle '" + bundleName + "'", e);;
            throw new IllegalStateException(e);
        }

    }

    private static Localization getInstance(Locale locale) {
        if (instances.get(locale) == null) {
            synchronized (sync) {
                if (instances.get(locale) == null) {
                    instances.put(locale, new Localization(locale));
                }
            }
        }
        return instances.get(locale);
    }

    private String getFromBundle(String key, Object[] params) {
        String result = null;
        try {
            result = bundle.getString(key);
        } catch (Exception e) {
        }
        return result != null ? String.format(result, params) : key;
    }

    public static String get(String key, Locale locale, String... params) {
        return getInstance(locale).getFromBundle(key, params);
    }

    /**
     * @param baseName
     * @param locale
     * @return
     */
    private static String toBundleName(String baseName, Locale locale) {
        if (locale == Locale.ROOT) {
            return baseName;
        }
        return String.format("%s_%s.properties", baseName, locale.getLanguage()) ;

    }

}
