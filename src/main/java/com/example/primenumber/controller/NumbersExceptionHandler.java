package com.example.primenumber.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.primenumber.util.BasicUncaughtExceptionHandler;

@ControllerAdvice
public class NumbersExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(BasicUncaughtExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public void handleException(final Exception e, final HttpServletResponse response) throws IOException {
        logger.warn(INTERNAL_SERVER_ERROR.getReasonPhrase(), e);
        response.sendError(INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @ExceptionHandler({
        TypeMismatchException.class,
        MissingServletRequestParameterException.class,
        IllegalArgumentException.class
    })
    public void handleBadRequest(final Exception e, final HttpServletResponse response) throws IOException {
        logger.info(BAD_REQUEST.getReasonPhrase(), e);
        response.sendError(BAD_REQUEST.value(), e.getMessage());
    }
}