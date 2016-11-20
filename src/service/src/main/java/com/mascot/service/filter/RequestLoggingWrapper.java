package com.mascot.service.filter;

/**
 * Created by Николай on 20.11.2016.
 */

import org.apache.commons.io.input.TeeInputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Request logging wrapper using proxy split stream to extract request body
 */
public class RequestLoggingWrapper extends HttpServletRequestWrapper {
    private final ByteArrayOutputStream bos = new ByteArrayOutputStream();
    private final JsonLogger jsonLogger;

    /**
     * @param request   request from which we want to extract post data
     */
    RequestLoggingWrapper(HttpServletRequest request, JsonLogger jsonLogger) {
        super(request);
        this.jsonLogger = jsonLogger;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ServletInputStream servletInputStream = RequestLoggingWrapper.super.getInputStream();
        return new ServletInputStream() {
            private TeeInputStream tee = new TeeInputStream(servletInputStream, bos);

            @Override
            public int read() throws IOException {
                return tee.read();
            }

            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                return tee.read(b, off, len);
            }

            @Override
            public int read(byte[] b) throws IOException {
                return tee.read(b);
            }

            @Override
            public boolean isFinished() {
                return servletInputStream.isFinished();
            }

            @Override
            public boolean isReady() {
                return servletInputStream.isReady();
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                servletInputStream.setReadListener(readListener);
            }

            @Override
            public void close() throws IOException {
                super.close();
                // do the logging
                logRequest();
            }
        };
    }

    private void logRequest() {
        jsonLogger.log(new String(toByteArray()));
    }

    private byte[] toByteArray() {
        return bos.toByteArray();
    }

}