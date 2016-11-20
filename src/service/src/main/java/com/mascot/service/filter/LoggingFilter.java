package com.mascot.service.filter;

/**
 * Created by Николай on 20.11.2016.
 */

import org.apache.log4j.Logger;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.mascot.service.filter.JsonLogger.HTTP_REQUEST_LOGGER_NAME;

/**
 * Http logging filter, which wraps around request and response in
 * each http call and logs
 * whole request and response bodies. It is enabled by
 * putting this instance into filter chain
 * by overriding getServletFilters() in
 * AbstractAnnotationConfigDispatcherServletInitializer.
 */
public class LoggingFilter extends AbstractRequestLoggingFilter {

    private static final Logger logger = Logger.getLogger(HTTP_REQUEST_LOGGER_NAME);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        long id = System.currentTimeMillis();
        RequestLoggingWrapper requestLoggingWrapper = new RequestLoggingWrapper(request, new JsonLogger(id, "request"));
        ResponseLoggingWrapper responseLoggingWrapper = new ResponseLoggingWrapper(response, new JsonLogger(id, "response"));
        logger.info(id + ": http request " + request.getRequestURI());
        super.doFilterInternal(requestLoggingWrapper, responseLoggingWrapper, filterChain);
        logger.info(id + ": http response " + " finished in " + (System.currentTimeMillis() - id) + "ms");
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {

    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {

    }
}