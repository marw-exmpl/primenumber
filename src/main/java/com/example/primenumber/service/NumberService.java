package com.example.primenumber.service;

import java.util.List;

import com.example.primenumber.NumberType;

public interface NumberService {
    
    NumberType getType(int number);

    List<Integer> getPrimes(int upperLimit);

    List<Integer> getPrimes(int lowerLimit, int upperLimit);
}