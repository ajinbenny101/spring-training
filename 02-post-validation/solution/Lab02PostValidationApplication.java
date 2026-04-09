package com.training.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Lab 02 - POST with Manual Validation (SOLUTION)
 *
 * This is the fully working solution. Compare your work against this
 * file if you get stuck.
 */
@SpringBootApplication
@RestController
@RequestMapping("/api/accounts")
public class Lab02PostValidationApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lab02PostValidationApplication.class, args);
    }

    // Storage - a fixed-size array, like a filing cabinet with 20 slots
    private Account[] accounts = new Account[20];
    private int count = 0;

    // ========================================================================
    // POST /api/accounts - Create a new bank account
    // ========================================================================

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest request) {

        // TODO 1: Validate all fields
        if (isBlank(request.getHolderName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD_REQUEST", "Holder name is required"));
        }

        if (isBlank(request.getAccountNumber())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD_REQUEST", "Account number is required"));
        }

        if (!isValidAccountNumber(request.getAccountNumber())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD_REQUEST", "Account number must be exactly 8 digits"));
        }

        if (isBlank(request.getSortCode())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD_REQUEST", "Sort code is required"));
        }

        if (!isValidSortCode(request.getSortCode())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD_REQUEST", "Sort code must be in format XX-XX-XX (e.g. 12-34-56)"));
        }

        if (request.getOpeningBalance() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("BAD_REQUEST", "Opening balance must not be negative"));
        }

        // TODO 2: Check for duplicate account number
        for (int i = 0; i < count; i++) {
            if (accounts[i].getAccountNumber().equals(request.getAccountNumber())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ErrorResponse("DUPLICATE_ACCOUNT",
                                "Account " + request.getAccountNumber() + " already exists"));
            }
        }

        // TODO 3: Check if the store is full
        if (count >= accounts.length) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("STORE_FULL", "Maximum account limit reached"));
        }

        // TODO 4: Create the Account and store it
        Account account = new Account(
                request.getAccountNumber(),
                request.getSortCode(),
                request.getHolderName(),
                request.getOpeningBalance()
        );
        accounts[count] = account;
        count++;

        // TODO 5: Return 201 Created with a SuccessResponse
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessResponse("Account created successfully", account));
    }

    // ========================================================================
    // GET /api/accounts/{accountNumber} - Retrieve an account
    // ========================================================================

    // TODO 6: Implement the GET endpoint
    @GetMapping("/{accountNumber}")
    public ResponseEntity<?> getAccount(@PathVariable String accountNumber) {
        for (int i = 0; i < count; i++) {
            if (accounts[i].getAccountNumber().equals(accountNumber)) {
                return ResponseEntity.ok(accounts[i]);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("NOT_FOUND", "Account " + accountNumber + " not found"));
    }

    // ========================================================================
    // Helper methods
    // ========================================================================

    // TODO 7: isBlank
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    // TODO 8: isValidAccountNumber
    private boolean isValidAccountNumber(String accountNumber) {
        return accountNumber.length() == 8 && accountNumber.matches("\\d{8}");
    }

    // TODO 9: isValidSortCode
    private boolean isValidSortCode(String sortCode) {
        return sortCode.matches("\\d{2}-\\d{2}-\\d{2}");
    }
}
