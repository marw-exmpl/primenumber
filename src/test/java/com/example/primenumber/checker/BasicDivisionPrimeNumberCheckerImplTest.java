package com.example.primenumber.checker;

public class BasicDivisionPrimeNumberCheckerImplTest extends AbstractPrimeNumberCheckerTest {

    private final PrimeNumberChecker primeNumberChecker = new BasicDivisionPrimeNumberCheckerImpl();

    public BasicDivisionPrimeNumberCheckerImplTest(final Integer number, final Boolean result) {
        super(number, result);
    }

    @Override 
    protected PrimeNumberChecker getChecker() {
        return primeNumberChecker;
    }
}