package com.example.primenumber.checker;

public class MillerRabinPrimeNumberCheckerImplTest extends AbstractPrimeNumberCheckerTest {

    private final PrimeNumberChecker primeNumberChecker = new MillerRabinPrimeNumberCheckerImpl();

    public MillerRabinPrimeNumberCheckerImplTest(final Integer number, final Boolean result) {
        super(number, result);
    }

    @Override
    protected PrimeNumberChecker getChecker() {
        return primeNumberChecker;
    }
}