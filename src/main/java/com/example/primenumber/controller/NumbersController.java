package com.example.primenumber.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.primenumber.service.NumberService;

@RestController
@RequestMapping("/api/v1/numbers")
public class NumbersController {

    @Autowired
    private NumberService numberService;

    @RequestMapping(value = "/primes", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
    public PrimeNumbersResult getPrimes(
            @RequestParam(name = "lowerLimit", required = false) final Integer lowerLimit,
            @RequestParam(name = "upperLimit") final Integer upperLimit) {

        PrimeNumbersResult result;
        if (lowerLimit == null) {
            result = new PrimeNumbersResult(upperLimit, numberService.getPrimes(upperLimit));
        } else {
            result = new PrimeNumbersResult(lowerLimit, upperLimit,
                    numberService.getPrimes(lowerLimit, upperLimit));
        }
        return result;
    }

    @RequestMapping(value = "/{number}", method = GET, produces = APPLICATION_JSON_UTF8_VALUE)
    public NumberDetailsResult getNumberDetails(@PathVariable("number") final Integer number) {
        return new NumberDetailsResult(number, numberService.getType(number));
    }
}