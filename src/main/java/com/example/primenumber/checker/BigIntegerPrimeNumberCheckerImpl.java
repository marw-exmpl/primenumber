package com.example.primenumber.checker;

import java.math.BigInteger;

import org.springframework.stereotype.Service;

@Service("bigIntegerPrimeNumberChecker")
public class BigIntegerPrimeNumberCheckerImpl implements PrimeNumberChecker {
    private static final int CERTAINTY = 10;

    @Override
    public boolean isPrime(final int number) {
        return BigInteger.valueOf(number).isProbablePrime(CERTAINTY);
    }
}