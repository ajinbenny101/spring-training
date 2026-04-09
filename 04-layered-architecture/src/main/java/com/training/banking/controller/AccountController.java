package com.training.banking.controller;

import com.training.banking.dto.CreateAccountRequest;
import com.training.banking.dto.ErrorResponse;
import com.training.banking.model.Account;
import com.training.banking.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



/**
 * Controller layer for account endpoints.
 *
 * This class is the "teller" of the bank. It handles HTTP requests,
 * calls the service to do the actual work, and sends back HTTP responses.
 *
 * Notice: this class has NO ArrayList, NO validation logic, NO account
 * number generation. All of that lives in the service layer.
 *
 * Complete the TODOs below to wire everything up.
 */
// ============================================================
// TODO 1: Add class-level annotations
// ============================================================
// Add TWO annotations to this class:
//
//   @RestController - tells Spring this class handles HTTP requests
//   @RequestMapping("/api/accounts") - sets the base URL path
//
// With @RequestMapping on the class, all methods inside share that
// base path. So a @GetMapping here maps to GET /api/accounts.
// ============================================================
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    // ============================================================
    // TODO 2: Add AccountService field and constructor
    // ============================================================
    // Declare a field:
    
    private final AccountService accountService;
    //
    // Create a constructor that takes AccountService as a parameter
    // and assigns it to the field. Add @Autowired on the constructor.
    //
    @Autowired
    public AccountController(AccountService accountService) {
           this.accountService = accountService;
       }
    //
    // This is constructor injection. When Spring creates the controller,
    // it sees the constructor needs an AccountService, finds the one it
    // already created (because of @Service), and passes it in.
    //
    // Hint: you need to import org.springframework.beans.factory.annotation.Autowired
    // ============================================================

@PostMapping
public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest request) {
    try {
        Account account = accountService.createAccount(request);
        return ResponseEntity.status(201).body(account);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse("BAD_REQUEST", e.getMessage()));
    }
}

@GetMapping()
public ResponseEntity<List<Account>> getAllAccounts() {
    return ResponseEntity.ok(accountService.getAllAccounts());
}

@GetMapping("/{accountNumber}")
public ResponseEntity<?> getAccount(@PathVariable String accountNumber) {
    Account account = accountService.getAccount(accountNumber);
    if (account == null) {
        return ResponseEntity.status(404).body(new ErrorResponse("NOT_FOUND", "Account not found: " + accountNumber));
    }
    return ResponseEntity.ok(account);
}


@DeleteMapping("/{accountNumber}")
public ResponseEntity<?> deleteAccount(@PathVariable String accountNumber) {
    boolean deleted = accountService.deleteAccount(accountNumber);
    if (!deleted) {
        return ResponseEntity.status(404).body(new ErrorResponse("NOT_FOUND", "Account not found: " + accountNumber));
    }
    return ResponseEntity.ok(Map.of("message", "Account deleted: " + accountNumber));
}


    // ============================================================
    // TODO 6: Implement DELETE /{accountNumber} (delete account)
    // ============================================================
    // Add a method with @DeleteMapping("/{accountNumber}") that:
    //   a) Takes a @PathVariable String accountNumber parameter
    //   b) Calls accountService.deleteAccount(accountNumber)
    //   c) If the result is false (account not found), returns 404
    //      with an ErrorResponse containing error="NOT_FOUND" and
    //      message="Account not found: " + accountNumber
    //   d) If the result is true, returns 200 with a simple message.
    //      You can return a Map or just a string.
    //
    // Hint for the success response, you can use:
    //   return ResponseEntity.ok(Map.of("message", "Account deleted: " + accountNumber));
    //   (import java.util.Map)
    // ============================================================

}
