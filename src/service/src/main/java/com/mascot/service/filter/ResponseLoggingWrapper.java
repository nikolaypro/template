package com.mascot.service.filter;

import org.apache.commons.io.output.TeeOutputStream;
import org.apache.log4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Николай on 20.11.2016.
 */
public class ResponseLoggingWrapper extends HttpServletResponseWrapper {
    private final ByteArrayOutputStream bos = new ByteArrayOutputStream();
    private final JsonLogger jsonLogger;

    /**
     * @param response  response from which we want to extract stream data
     * @param jsonLogger
     */
    ResponseLoggingWrapper(HttpServletResponse response, JsonLogger jsonLogger) {
        super(response);
        this.jsonLogger = jsonLogger;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        final ServletOutputStream servletOutputStream = ResponseLoggingWrapper.super.getOutputStream();
        return new ServletOutputStream() {
            private TeeOutputStream tee = new TeeOutputStream(servletOutputStream, bos);

            @Override
            public void write(byte[] b) throws IOException {
                tee.write(b);
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                tee.write(b, off, len);
            }

            @Override
            public void flush() throws IOException {
                tee.flush();
                logRequest();
            }

            @Override
            public void write(int b) throws IOException {
                tee.write(b);
            }

            @Override
            public boolean isReady() {
                return servletOutputStream.isReady();
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
                servletOutputStream.setWriteListener(writeListener);
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
        byte[] toLog = toByteArray();
        if (toLog != null && toLog.length > 0)
            jsonLogger.log(new String(toLog));
    }

    /**
     * this method will clear the buffer, so
     *
     * @return captured bytes from stream
     */
    private byte[] toByteArray() {
        byte[] ret = bos.toByteArray();
        bos.reset();
        return ret;
    }

}