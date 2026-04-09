package com.training.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@SpringBootApplication
@RestController
public class Lab01GetLogicApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lab01GetLogicApplication.class, args);
    }

    @GetMapping("/api/rates")
    public ResponseEntity<?> getRates(
            @RequestParam(required = false) String accountType,
            @RequestParam(required = false) String currency) {

        // Handle currency - default to GBP if blank or null
        if (currency == null || currency.isBlank()) {
            currency = "GBP";
        }
        currency = currency.toUpperCase();

        // Parse the accountType string into an AccountType enum
        AccountType type;

        if (accountType == null || accountType.isBlank()) {
            type = AccountType.CURRENT;
        } else {
            try {
                type = AccountType.valueOf(accountType.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Return 400 error if the accountType is invalid
                ErrorResponse error = new ErrorResponse(
                        "INVALID_ACCOUNT_TYPE",
                        "Allowed types: SAVINGS, CURRENT, ISA"
                );
                return ResponseEntity.badRequest().body(error);
            }
        }

        // Get rate data and return 200 OK
        RateResponse response = getRateInfo(type, currency);
        return ResponseEntity.ok(response);
    }

    private RateResponse getRateInfo(AccountType type, String currency) {
        String timestamp = Instant.now().toString();

        return switch (type) {
            case SAVINGS -> new RateResponse(type.name(), 1.5, 500.00, currency, timestamp);
            case CURRENT -> new RateResponse(type.name(), 0.1, 0.00, currency, timestamp);
            case ISA -> new RateResponse(type.name(), 2.0, 1000.00, currency, timestamp);
        };
    }
}