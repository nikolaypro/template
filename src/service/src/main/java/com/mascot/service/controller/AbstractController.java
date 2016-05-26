package com.mascot.service.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by Nikolay on 21.12.2015.
 */
public class AbstractController {
    protected final Logger logger = Logger.getLogger(getClass());

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex) {
        logger.error("", ex);
        if (ex instanceof ArrayStoreException) { // for example
            return new ResponseEntity<WebError>(new WebError(ex.getMessage()), HttpStatus.CONFLICT);
        }
//        return new ResponseEntity<WebError>(new WebError(ex.getMessage()), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<WebError>(new WebError(ex.getMessage()), HttpStatus.CONFLICT);
    }

}
