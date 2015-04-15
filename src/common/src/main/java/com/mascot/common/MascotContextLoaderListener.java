package com.mascot.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

/**
 * Created by Nikolay on 28.11.2014.
 */
public class MascotContextLoaderListener extends ContextLoaderListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        ApplicationContext appContext = new ClassPathXmlApplicationContext(AppConfConstants.WEB_INF_MASCOT_CONTEXT_XML);
        MascotAppContext.setApplicationContext(appContext);

    }
}
