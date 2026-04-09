package com.training.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Lab 09 - External API Call (Currency Conversion)
 *
 * This app calls an external exchange rate API to convert currencies.
 * Think of it as a bank teller who checks today's rates before converting your money.
 *
 * You will build:
 *   1. A configuration class that creates a RestTemplate bean
 *   2. A service that calls the exchange rate API and extracts the rate
 *   3. A controller that accepts conversion requests and returns results
 *
 * The main class is already done - start with AppConfig.java (TODO 1-2).
 */
@SpringBootApplication
public class Lab09ExternalApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lab09ExternalApiApplication.class, args);
    }
}
