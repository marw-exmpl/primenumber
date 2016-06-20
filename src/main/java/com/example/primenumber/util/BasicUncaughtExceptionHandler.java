package com.example.primenumber.util;

import java.lang.Thread.UncaughtExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicUncaughtExceptionHandler implements UncaughtExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(BasicUncaughtExceptionHandler.class);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        logger.error("Captured uncaught exception in thread: " + t.getName(), e);
    }
}