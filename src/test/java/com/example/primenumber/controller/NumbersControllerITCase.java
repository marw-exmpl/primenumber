package com.example.primenumber.controller;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.primenumber.Application;
import com.example.primenumber.NumberType;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest({ "server.port=0", "management.port=0" })
public class NumbersControllerITCase {
    private static final String NUMBER_TYPE_URL = "/api/v1//numbers/%d";
    private static final String PRIME_NUMBERS_URL = "/api/v1//numbers/primes";
    private static final String LOWER_LIMIT_PARAMETER = "lowerLimit";
    private static final String UPPER_LIMIT_PARAMETER = "upperLimit";
    private static final Integer LOWER_LIMIT = 5;
    private static final Integer UPPER_LIMIT = 20;
    private static final Integer[] PRIME_NUMBERS_IN_LIMIT = { 5, 7, 11, 13, 17, 19 };

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testThatGetNumberDetailsReturnsCorrectResult() throws Exception {
        mockMvc.perform(
                get(String.format(NUMBER_TYPE_URL, LOWER_LIMIT))
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.number", equalTo(LOWER_LIMIT)))
                .andExpect(jsonPath("$.type", equalTo(NumberType.PRIME.name())));
    }

    @Test
    public void testThatGetNumberDetailsFailsWithBadRequestWhenParameterIsNotNaturalNumber() throws Exception {
        mockMvc.perform(
                get(String.format(NUMBER_TYPE_URL, -1))
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testThatGetPrimeNumbersReturnsCorrectResult() throws Exception {
        mockMvc.perform(
                get(PRIME_NUMBERS_URL)
                    .param(LOWER_LIMIT_PARAMETER, String.valueOf(LOWER_LIMIT))
                    .param(UPPER_LIMIT_PARAMETER, String.valueOf(UPPER_LIMIT))
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.lowerLimit", equalTo(LOWER_LIMIT)))
                .andExpect(jsonPath("$.upperLimit", equalTo(UPPER_LIMIT)))
                .andExpect(jsonPath("$.primes", contains(PRIME_NUMBERS_IN_LIMIT)));
    }

    @Test
    public void testThatGetPrimeNumbersFailsWithBadRequestWhenUpperLimitIsNotNaturalNumber() throws Exception {
        mockMvc.perform(
                get(PRIME_NUMBERS_URL)
                    .param(UPPER_LIMIT_PARAMETER, String.valueOf(-1))
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}