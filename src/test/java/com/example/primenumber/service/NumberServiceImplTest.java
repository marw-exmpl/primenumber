package com.example.primenumber.service;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.example.primenumber.NumberType;
import com.example.primenumber.checker.PrimeNumberChecker;
import com.google.common.collect.Lists;

@RunWith(MockitoJUnitRunner.class)
public class NumberServiceImplTest {
    private static final List<Integer> RESULT = Lists.newArrayList(2, 3, 5, 7);
    private static final int DEFAULT_LOWER_LIMIT = 2;
    private static final int LOWER_LIMIT = 5;
    private static final int UPPER_LIMIT = 10;
    private static final int ONE = 1;
    private static final int PRIME_NUMBER = 5;
    private static final int COMPOSITE_NUMBER = 10;

    @Mock
    private PrimeNumberChecker primeNumberChecker;
    @Mock
    private ForkJoinPool primeNumberPool;
    @Captor
    private ArgumentCaptor<PrimeNumberCheckRecursiveTask> taskCaptor;
    @InjectMocks
    private NumberServiceImpl underTest;

    @Before
    public void setUp() {
        when(primeNumberPool.invoke(Mockito.<ForkJoinTask<List<Integer>>>any())).thenReturn(RESULT);
    }
    
    @Test
    public void testThatGetTypeReturnsCorrectResult() {
        when(primeNumberChecker.isPrime(PRIME_NUMBER)).thenReturn(true);
        when(primeNumberChecker.isPrime(COMPOSITE_NUMBER)).thenReturn(false);
        
        assertThat(underTest.getType(ONE), is(NumberType.UNIT));
        assertThat(underTest.getType(PRIME_NUMBER), is(NumberType.PRIME));
        assertThat(underTest.getType(COMPOSITE_NUMBER), is(NumberType.COMPOSITE));
        
        verify(primeNumberChecker).isPrime(PRIME_NUMBER);
        verify(primeNumberChecker).isPrime(COMPOSITE_NUMBER);
        verifyNoMoreInteractions(primeNumberChecker);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatIsPrimeThrowsExceptionWhenNumberIsZero() {
        underTest.getType(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatIsPrimeThrowsExceptionWhenNumberIsNegative() {
        underTest.getType(-1);
    }

    @Test
    public void testThatGetPrimesWithUpperLimitReturnsCorrectResult() {
        assertThat(underTest.getPrimes(UPPER_LIMIT), sameInstance(RESULT));

        verify(primeNumberPool).invoke(taskCaptor.capture());
        
        PrimeNumberCheckRecursiveTask task = taskCaptor.getValue();
        assertThat(task, notNullValue());
        assertThat(task.getPrimeNumberChecker(), sameInstance(primeNumberChecker));
        assertThat(task.getLowerLimit(), equalTo(DEFAULT_LOWER_LIMIT));
        assertThat(task.getUpperLimit(), equalTo(UPPER_LIMIT));
    }

    @Test
    public void testThatGetPrimesWithUpperLimitBelowTwoReturnsEmptyList() {
        assertThat(underTest.getPrimes(1), empty());
        
        verifyZeroInteractions(primeNumberPool);
    }

    @Test
    public void testThatGetPrimesWithRangeReturnsCorrectResult() {
        assertThat(underTest.getPrimes(LOWER_LIMIT, UPPER_LIMIT), sameInstance(RESULT));
        
        verify(primeNumberPool).invoke(taskCaptor.capture());
        
        PrimeNumberCheckRecursiveTask task = taskCaptor.getValue();
        assertThat(task, notNullValue());
        assertThat(task.getPrimeNumberChecker(), sameInstance(primeNumberChecker));
        assertThat(task.getLowerLimit(), equalTo(LOWER_LIMIT));
        assertThat(task.getUpperLimit(), equalTo(UPPER_LIMIT));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testThatGetPrimesWithRangeThrowsExceptionWhenLowerLimitIsZero() {
        underTest.getPrimes(0, UPPER_LIMIT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatGetPrimesWithRangeThrowsExceptionWhenLowerLimitIsNegative() {
        underTest.getPrimes(-1, UPPER_LIMIT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatGetPrimesWithRangeThrowsExceptionWhenUpperLimitIsZero() {
        underTest.getPrimes(LOWER_LIMIT, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatGetPrimesWithRangeThrowsExceptionWhenUpperLimitIsNegative() {
        underTest.getPrimes(LOWER_LIMIT, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatGetPrimesWithRangeThrowsExceptionWhenUpperLimitIsLessThanThanLowerLimit() {
        underTest.getPrimes(UPPER_LIMIT, LOWER_LIMIT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThatGetPrimesWithRangeThrowsExceptionWhenRangeExceedsLimit() {
        underTest.getPrimes(LOWER_LIMIT, LOWER_LIMIT + 1000_000);
    }
}