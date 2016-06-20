package com.example.primenumber.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
public class NumbersErrorController implements ErrorController {
    private static final String PATH = "/error";
    private static final String ERROR_ATTRIBUTE = "error";
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String TIMESTAMP_ATTRIBUTE = "timestamp";

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(value = PATH, produces = APPLICATION_JSON_UTF8_VALUE)
    public ErrorMessage error(final HttpServletRequest request, final HttpServletResponse response) {
        Map<String, Object> attributes =
                errorAttributes.getErrorAttributes(new ServletRequestAttributes(request), false);

        return new ErrorMessage(
                response.getStatus(),
                (String) attributes.get(ERROR_ATTRIBUTE),
                (String) attributes.get(MESSAGE_ATTRIBUTE),
                attributes.get(TIMESTAMP_ATTRIBUTE).toString());
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}