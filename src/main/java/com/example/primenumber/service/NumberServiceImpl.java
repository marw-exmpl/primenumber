package com.example.primenumber.service;

import static com.example.primenumber.NumberType.COMPOSITE;
import static com.example.primenumber.NumberType.PRIME;
import static com.example.primenumber.NumberType.UNIT;
import static com.google.common.base.Preconditions.checkArgument;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.primenumber.NumberType;
import com.example.primenumber.checker.PrimeNumberChecker;

@Service
public class NumberServiceImpl implements NumberService {
    private int MAX_LIMIT = 500_000;
    
    @Autowired
    @Qualifier("millerRabinPrimeNumberChecker")
    private PrimeNumberChecker primeNumberChecker;
    
    @Autowired
    private ForkJoinPool primeNumberPool;
    
    @Override
    public NumberType getType(int number) {
        checkArgument(number >= 1, "Provided parameter is not a natural number, value: " + number);

        if (number == 1) {
            return UNIT;
        }

        return primeNumberChecker.isPrime(number) ? PRIME : COMPOSITE;
    }

    @Override
    public List<Integer> getPrimes(int lowerLimit, int upperLimit) {
        checkArgument(lowerLimit >= 1, "Cannot get primes when lower limit is not a natural number, value: " + lowerLimit);
        checkArgument(upperLimit >= 1, "Cannot get primes when upper limit is not a natural number, value: " + upperLimit);
        checkArgument(upperLimit >= lowerLimit, String.format(
                "Cannot get primes when upper limit is lower than lower limit, lower: %d, upper %d", lowerLimit, upperLimit));
        checkArgument(upperLimit - lowerLimit <= MAX_LIMIT, String.format(
                "Cannot get primes for range exceeding %d, lower: %d, upper %d", MAX_LIMIT, lowerLimit, upperLimit));

        return primeNumberPool.invoke(PrimeNumberCheckRecursiveTask.createTask(primeNumberChecker, lowerLimit, upperLimit));
    }

    @Override
    public List<Integer> getPrimes(int upperLimit) {
        checkArgument(upperLimit >= 1, "Cannot get primes when upper limit is not a natural number, value: " + upperLimit);

        if (upperLimit == 1) {
            return Collections.emptyList();
        }
        return getPrimes(2, upperLimit);
    }
}