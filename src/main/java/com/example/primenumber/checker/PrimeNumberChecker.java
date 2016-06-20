package com.example.primenumber.checker;

import org.springframework.cache.annotation.Cacheable;

public interface PrimeNumberChecker {

    @Cacheable("primes")
    boolean isPrime(int number);
}