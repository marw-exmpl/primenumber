package com.example.primenumber.service;

import static com.example.primenumber.service.PrimeNumberCheckRecursiveTask.createTask;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.isOneOf;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.intThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.concurrent.ForkJoinPool;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.example.primenumber.checker.PrimeNumberChecker;

@RunWith(MockitoJUnitRunner.class)
public class PrimeNumberCheckRecursiveTaskTest {
    private static final Integer[] PRIME_NUMBERS = {2, 3, 5, 7, 11, 13, 17, 19};
    private static final int UPPER_LIMIT = 15;
    private static final int LOWER_LIMIT = 5;
    private static final Integer[] PRIME_NUMBERS_IN_BOTH_LIMITS = {5, 7, 11, 13};
    private static final int CUSTOM_THRESHOLD = 4;

    @Mock
    private PrimeNumberChecker primeNumberChecker;

    private final ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

    @Before
    public void setUp() {
        doReturn(true).when(primeNumberChecker).isPrime(intThat(isOneOf(PRIME_NUMBERS)));
        doReturn(false).when(primeNumberChecker).isPrime(intThat(not(isOneOf(PRIME_NUMBERS))));
    }
    
    @Test
    public void testThatTaskComputesCorrectResultUsingSequentialProcessing() {
        PrimeNumberCheckRecursiveTask underTest = createTask(primeNumberChecker, LOWER_LIMIT, UPPER_LIMIT);

        assertThat(pool.invoke(underTest), contains(PRIME_NUMBERS_IN_BOTH_LIMITS));
        
        for(int i = LOWER_LIMIT; i <= UPPER_LIMIT; i++) {
            verify(primeNumberChecker).isPrime(i);
        }
        verifyNoMoreInteractions(primeNumberChecker);
    }

    @Test
    public void testThatTaskComputesCorrectResultWhenLowerAndUpperLimitAreTheSame() {
        PrimeNumberCheckRecursiveTask underTest = createTask(primeNumberChecker, LOWER_LIMIT, LOWER_LIMIT);
        
        assertThat(pool.invoke(underTest), contains(LOWER_LIMIT));
        
        verify(primeNumberChecker).isPrime(LOWER_LIMIT);
        verifyNoMoreInteractions(primeNumberChecker);
    }

    @Test
    public void testThatTaskComputesCorrectResultWhenForkIsApplied() {
        PrimeNumberCheckRecursiveTask underTest = createTask(primeNumberChecker, LOWER_LIMIT, UPPER_LIMIT, CUSTOM_THRESHOLD);

        assertThat(pool.invoke(underTest), contains(PRIME_NUMBERS_IN_BOTH_LIMITS));
        
        for(int i = LOWER_LIMIT; i <= UPPER_LIMIT; i++) {
            verify(primeNumberChecker).isPrime(i);
        }
        verifyNoMoreInteractions(primeNumberChecker);
    }
}
