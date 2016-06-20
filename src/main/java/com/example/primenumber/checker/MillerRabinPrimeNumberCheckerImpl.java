package com.example.primenumber.checker;

import org.apache.commons.math3.primes.Primes;
import org.springframework.stereotype.Service;

@Service("millerRabinPrimeNumberChecker")
public class MillerRabinPrimeNumberCheckerImpl implements PrimeNumberChecker {

    @Override
    public boolean isPrime(final int number) {
        return Primes.isPrime(number);
    }
}