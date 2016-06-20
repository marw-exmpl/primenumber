package com.example.primenumber.checker;

public class BigIntegerPrimeNumberCheckerImplTest extends AbstractPrimeNumberCheckerTest {

    private final PrimeNumberChecker primeNumberChecker = new BigIntegerPrimeNumberCheckerImpl();

    public BigIntegerPrimeNumberCheckerImplTest(final Integer number, final Boolean result) {
        super(number, result);
    }

    @Override
    protected PrimeNumberChecker getChecker() {
        return primeNumberChecker;
    }
}