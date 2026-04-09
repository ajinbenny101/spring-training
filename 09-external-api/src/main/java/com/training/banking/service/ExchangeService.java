package com.training.banking.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Service that calls an external exchange rate API to get currency conversion rates.
 *
 * This is like a bank clerk who picks up the phone, calls the exchange rate hotline,
 * writes down the rate, and brings it back to you.
 *
 * The external API: https://open.er-api.com/v6/latest/{currency}
 * It returns a JSON object with a "rates" map containing exchange rates for all currencies.
 */
@Service
public class ExchangeService {

    // ----------------------------------------------------------------
    // TODO 3: Inject RestTemplate via constructor injection
    //
    // You need RestTemplate to make HTTP calls. Spring will automatically
    // give you the one created in AppConfig.
    //
    // Steps:
    //   1. Add a field: private final RestTemplate restTemplate;
    //   2. Add a constructor that takes RestTemplate as a parameter
    //      and assigns it to the field.
    //
    // Example:
    //   private final RestTemplate restTemplate;
    //
    //   public ExchangeService(RestTemplate restTemplate) {
    //       this.restTemplate = restTemplate;
    //   }
    //
    // Write your field and constructor here:
    // ----------------------------------------------------------------



    // ----------------------------------------------------------------
    // TODO 4: Implement the getRate method
    //
    // This method calls the exchange rate API and returns the rate
    // for converting from one currency to another.
    //
    // Steps:
    //   1. Build the URL: "https://open.er-api.com/v6/latest/" + from
    //   2. Call the API:
    //        Map response = restTemplate.getForObject(url, Map.class);
    //   3. Check if response is null - if so, throw new RuntimeException("No response from API")
    //   4. Extract the rates map:
    //        Map<String, Object> rates = (Map<String, Object>) response.get("rates");
    //   5. Check if rates is null or doesn't contain the "to" currency
    //        - if so, throw new RuntimeException("Currency not found: " + to)
    //   6. Get the rate and return it:
    //        return ((Number) rates.get(to)).doubleValue();
    //
    // The cast to Number works because JSON numbers can be Integer or Double.
    // Casting to Number first, then calling doubleValue(), handles both cases.
    //
    // Replace the method body below with your implementation:
    // ----------------------------------------------------------------
    public double getRate(String from, String to) {
        // Replace this entire method body with your implementation
        throw new UnsupportedOperationException("TODO 4: Implement getRate - call the exchange rate API");
    }
}
