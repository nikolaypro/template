package com.mascot.service;

import com.mascot.common.AppConfConstants;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Nikolay on 27.10.2014.
 */
public class MascotDispatchServlet extends DispatcherServlet {

    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        Logger.getLogger(MascotDispatchServlet.class).info(RequestContextUtils.getWebApplicationContext(request));
//        Logger.getLogger(MascotDispatchServlet.class).info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        super.doService(request, response);
    }

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        Logger.getLogger(MascotDispatchServlet.class).info(RequestContextUtils.getWebApplicationContext(request));
        super.doDispatch(request, response);
    }

    @Override
    public String getContextConfigLocation() {
//        final String contextConfigLocation = super.getContextConfigLocation();
        return "classpath:" + AppConfConstants.WEB_INF_MASCOT_CONTEXT_XML;
    }

}
