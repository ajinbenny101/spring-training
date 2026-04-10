package com.training.banking.controller;

import com.training.banking.dto.ConversionResponse;
import com.training.banking.dto.ErrorResponse;
import com.training.banking.service.ExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Controller that handles currency conversion requests.
 *
 * A user sends a request like:
 *   GET /api/convert?from=GBP&to=USD&amount=100
 *
 * The controller asks the ExchangeService for the rate,
 * does the math, and returns the result.
 *
 * If anything goes wrong (bad currency, API down), it returns
 * a clean error instead of a stack trace.
 */
@RestController
public class ConversionController {
    private final ExchangeService exchangeService;  

    public ConversionController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }
    // ----------------------------------------------------------------
    // TODO 5: Inject ExchangeService via constructor injection
    //
    // Same pattern as TODO 3 - you need ExchangeService to get rates.
    //
    // Steps:
    //   1. Add a field: private final ExchangeService exchangeService;
    //   2. Add a constructor that takes ExchangeService as a parameter
    //      and assigns it to the field.
    //
    // Write your field and constructor here:
    // ----------------------------------------------------------------



    // ----------------------------------------------------------------
    // TODO 6: Add the @GetMapping annotation
    //
    // This method should handle GET requests to /api/convert.
    //
    // Add this annotation above the method:
    //   @GetMapping("/api/convert")
    //
    // You will need this import:
    //   import org.springframework.web.bind.annotation.GetMapping;
    //
    // The @RequestParam annotations are already in place - they tell
    // Spring to pull "from", "to", and "amount" from the query string.
       @GetMapping("/api/convert")
    public ResponseEntity<?> convert(@RequestParam String from,
                                     @RequestParam String to,
                                     @RequestParam double amount) {

        try {
            double rate = exchangeService.getRate(from, to);
            double convertedAmount = amount * rate;
            ConversionResponse response = new ConversionResponse(
                from, to, amount, rate, convertedAmount
            );
            return ResponseEntity.ok(response);

        } catch (Exception e) {

            return ResponseEntity.badRequest()
                .body(new ErrorResponse("CONVERSION_ERROR",
                    "Could not convert from " + from + " to " + to));
        }
    }
}