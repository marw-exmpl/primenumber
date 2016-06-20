package com.example.primenumber.controller;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrimeNumbersResult {
    
    private final Integer lowerLimit;
    private final Integer upperLimit;
    private final List<Integer> primes;
    
    public PrimeNumbersResult(final Integer upperLimit, final List<Integer> primes) {
        this(null, upperLimit, primes);
    }
    
    public PrimeNumbersResult(final Integer lowerLimit, final Integer upperLimit, final List<Integer> primes) {
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.primes = primes;
    }

    public Integer getLowerLimit() {
        return lowerLimit;
    }

    public Integer getUpperLimit() {
        return upperLimit;
    }

    public List<Integer> getPrimes() {
        return primes;
    }
}