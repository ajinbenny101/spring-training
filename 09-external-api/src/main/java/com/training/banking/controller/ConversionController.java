package com.training.banking.controller;

import com.training.banking.dto.ConversionResponse;
import com.training.banking.dto.ErrorResponse;
import com.training.banking.service.ExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    // ----------------------------------------------------------------
    public ResponseEntity<?> convert(@RequestParam String from,
                                     @RequestParam String to,
                                     @RequestParam double amount) {

        try {

            // ----------------------------------------------------------------
            // TODO 7: Call the service and build the response
            //
            // Steps:
            //   1. Get the rate: double rate = exchangeService.getRate(from, to);
            //   2. Calculate:    double convertedAmount = amount * rate;
            //   3. Build the response:
            //        ConversionResponse response = new ConversionResponse(
            //            from, to, amount, rate, convertedAmount
            //        );
            //   4. Return it:   return ResponseEntity.ok(response);
            //
            // Write your code here:
            // ----------------------------------------------------------------

            // Remove this line once you complete TODO 7:
            return ResponseEntity.ok("TODO: implement conversion logic");

        } catch (Exception e) {

            // ----------------------------------------------------------------
            // TODO 8: Return a 400 error response
            //
            // If anything goes wrong (bad currency, API down, etc.),
            // we end up here. Return a clean error:
            //
            //   return ResponseEntity.badRequest()
            //       .body(new ErrorResponse("CONVERSION_ERROR",
            //           "Could not convert from " + from + " to " + to));
            //
            // Write your error response here:
            // ----------------------------------------------------------------

            // Remove this line once you complete TODO 8:
            return ResponseEntity.badRequest().body("TODO: implement error handling");
        }
    }
}
