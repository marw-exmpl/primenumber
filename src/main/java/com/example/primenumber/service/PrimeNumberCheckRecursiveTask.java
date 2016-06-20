package com.example.primenumber.service;

import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.example.primenumber.checker.PrimeNumberChecker;

public class PrimeNumberCheckRecursiveTask extends RecursiveTask<List<Integer>> {
    private static final long serialVersionUID = -5706353822185074100L;
    private static final int DEFAULT_SEQUENTIAL_THRESHOLD = 1000;

    public static PrimeNumberCheckRecursiveTask createTask(
            final PrimeNumberChecker checker, final int lowerLimit, final int upperLimit) {
        return createTask(checker, lowerLimit, upperLimit, DEFAULT_SEQUENTIAL_THRESHOLD);
    }

    public static PrimeNumberCheckRecursiveTask createTask(
            final PrimeNumberChecker checker, final int lowerLimit, final int upperLimit, final int threshold) {
        return new PrimeNumberCheckRecursiveTask(checker, lowerLimit, upperLimit, threshold);
    }

    private final int threshold;
    private final PrimeNumberChecker checker;
    private final int lowerLimit;
    private final int upperLimit;

    private PrimeNumberCheckRecursiveTask(
            final PrimeNumberChecker checker, final int lowerLimit, final int upperLimit, final int threshold) {
        this.checker = checker;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.threshold = threshold;
    }

    public PrimeNumberChecker getPrimeNumberChecker() {
        return checker;
    }

    public int getLowerLimit() {
        return lowerLimit;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    @Override
    protected List<Integer> compute() {
        List<Integer> result;

        int size = upperLimit - lowerLimit + 1;
        if (size <= threshold) {
            result = IntStream.rangeClosed(lowerLimit, upperLimit)
                    .filter(i -> checker.isPrime(i))
                    .boxed()
                    .collect(Collectors.toList());
        } else {
            int middle = lowerLimit + (upperLimit - lowerLimit) / 2;
            PrimeNumberCheckRecursiveTask leftTask =
                    new PrimeNumberCheckRecursiveTask(checker, lowerLimit, middle, threshold);
            PrimeNumberCheckRecursiveTask rightTask =
                    new PrimeNumberCheckRecursiveTask(checker, middle + 1, upperLimit, threshold);

            leftTask.fork();
            List<Integer> rightResult = rightTask.compute();
            List<Integer> leftResult = leftTask.join();

            result = Stream.concat(leftResult.stream(), rightResult.stream()).collect(Collectors.toList());
        }
        return result;
    }
}