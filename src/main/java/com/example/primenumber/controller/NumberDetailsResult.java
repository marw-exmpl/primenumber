package com.example.primenumber.controller;

import com.example.primenumber.NumberType;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NumberDetailsResult {
    private final Integer number;
    private final NumberType type;
    
    public NumberDetailsResult(final Integer number, final NumberType type) {
        this.number = number;
        this.type = type;
    }

    public Integer getNumber() {
        return number;
    }

    public NumberType getType() {
        return type;
    }
}