package com.example.primenumber.checker;

import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

@Service("basicDivisionPrimeNumberChecker")
public class BasicDivisionPrimeNumberCheckerImpl implements PrimeNumberChecker {

    @Override
    public boolean isPrime(final int number) {
        if (number < 2) {
            return false;
        }
        if (number == 2) {
            return true;
        }
        if (number % 2 == 0) {
            return false;
        }

        return IntStream.rangeClosed(2, (int) Math.sqrt(number)).allMatch(i -> number % i != 0);
    }
}