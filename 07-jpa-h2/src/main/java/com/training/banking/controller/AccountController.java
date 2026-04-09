package com.training.banking.controller;

import com.training.banking.dto.CreateAccountRequest;
import com.training.banking.dto.ErrorResponse;
import com.training.banking.entity.Account;
import com.training.banking.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for account operations.
 *
 * This controller is pre-built. It delegates all work to AccountService,
 * which delegates to AccountRepository, which talks to the H2 database.
 *
 * Notice how clean this controller is compared to previous labs.
 * No ArrayList, no manual searching, no index management. The service
 * and repository handle all of that.
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // POST /api/accounts - create a new account
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequest request) {
        Account account = new Account(
                request.getAccountNumber(),
                request.getHolderName(),
                request.getBalance()
        );

        Account saved = accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // GET /api/accounts - list all accounts
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    // GET /api/accounts/{id} - get one account by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Long id) {
        Optional<Account> account = accountService.getAccountById(id);

        if (account.isPresent()) {
            return ResponseEntity.ok(account.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("NOT_FOUND", "Account with ID " + id + " not found"));
    }

    // DELETE /api/accounts/{id} - delete an account
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        // Check if the account exists before deleting
        Optional<Account> account = accountService.getAccountById(id);

        if (account.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT_FOUND", "Account with ID " + id + " not found"));
        }

        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
