package com.mascot.common;

import org.springframework.context.ApplicationContext;

/**
 * Created by Nikolay on 28.11.2014.
 */
public class MascotAppContext {

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        MascotAppContext.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <A> A getBean(String beanName, Class<A> aClass) {
        return applicationContext.getBean(beanName, aClass);
    }

    public static <A> A getBean(Class<A> aClass) {
        return applicationContext.getBean(aClass.getSimpleName(), aClass);
    }
}
