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

/**
 * Controller layer for account endpoints.
 *
 * This class is the "teller" of the bank. It handles HTTP requests,
 * calls the service to do the actual work, and sends back HTTP responses.
 *
 * Notice: this class has NO ArrayList, NO validation logic, NO account
 * number generation. All of that lives in the service layer.
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * POST /api/accounts - Create a new account.
     */
    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest request) {
        try {
            Account account = accountService.createAccount(request);
            return ResponseEntity.status(201).body(account);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("BAD_REQUEST", e.getMessage()));
        }
    }

    /**
     * GET /api/accounts - List all accounts.
     */
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    /**
     * GET /api/accounts/{accountNumber} - Get one account by account number.
     */
    @GetMapping("/{accountNumber}")
    public ResponseEntity<?> getAccount(@PathVariable String accountNumber) {
        Account account = accountService.getAccount(accountNumber);

        if (account == null) {
            return ResponseEntity.status(404)
                    .body(new ErrorResponse("NOT_FOUND", "Account not found: " + accountNumber));
        }

        return ResponseEntity.ok(account);
    }

    /**
     * DELETE /api/accounts/{accountNumber} - Delete an account.
     */
    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<?> deleteAccount(@PathVariable String accountNumber) {
        boolean deleted = accountService.deleteAccount(accountNumber);

        if (!deleted) {
            return ResponseEntity.status(404)
                    .body(new ErrorResponse("NOT_FOUND", "Account not found: " + accountNumber));
        }

        return ResponseEntity.ok(Map.of("message", "Account deleted: " + accountNumber));
    }
}
