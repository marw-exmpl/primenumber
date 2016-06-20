package com.example.primenumber;

import java.lang.Thread.UncaughtExceptionHandler;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ForkJoinPoolFactoryBean;

import com.example.primenumber.util.BasicUncaughtExceptionHandler;

@Configuration
@ComponentScan("com.example.primenumber")
public class BaseConfiguration extends CachingConfigurerSupport {
    @Bean
    public UncaughtExceptionHandler primeNumberUncaughtExceptionHandler() {
        return new BasicUncaughtExceptionHandler();
    }

    @Bean
    public ForkJoinPoolFactoryBean primeNumberPool(
            UncaughtExceptionHandler primeNumberUncaughtExceptionHandler) {
        ForkJoinPoolFactoryBean bean = new ForkJoinPoolFactoryBean();
        bean.setCommonPool(false);
        bean.setUncaughtExceptionHandler(primeNumberUncaughtExceptionHandler);
        return bean;
    }
}