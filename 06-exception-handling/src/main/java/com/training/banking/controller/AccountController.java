package com.training.banking.controller;

import com.training.banking.dto.AmountRequest;
import com.training.banking.dto.CreateAccountRequest;
import com.training.banking.model.Account;
import com.training.banking.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for bank account operations.
 *
 * Notice how CLEAN this controller is compared to earlier labs.
 * There are no if/else error checks, no manual ErrorResponse building,
 * no try/catch blocks. The controller just calls the service and
 * returns the result.
 *
 * If something goes wrong (account not found, duplicate account,
 * insufficient funds), the service throws an exception. The
 * GlobalExceptionHandler catches it and builds the error response.
 *
 * Compare this to Lab 02 where the controller had dozens of lines
 * of validation and error handling. This is the power of separating
 * concerns - the controller handles requests, the service handles
 * business logic, and the exception handler handles errors.
 *
 * This class is PRE-BUILT for you - no changes needed here.
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // POST /api/accounts - Create a new account
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequest request) {
        Account account = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    // GET /api/accounts/{accountNumber} - Get an account by number
    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        Account account = accountService.getAccount(accountNumber);
        return ResponseEntity.ok(account);
    }

    // PATCH /api/accounts/{accountNumber}/deposit - Deposit money
    @PatchMapping("/{accountNumber}/deposit")
    public ResponseEntity<Account> deposit(
            @PathVariable String accountNumber,
            @RequestBody AmountRequest request) {
        Account account = accountService.deposit(accountNumber, request.getAmount());
        return ResponseEntity.ok(account);
    }

    // PATCH /api/accounts/{accountNumber}/withdraw - Withdraw money
    @PatchMapping("/{accountNumber}/withdraw")
    public ResponseEntity<Account> withdraw(
            @PathVariable String accountNumber,
            @RequestBody AmountRequest request) {
        Account account = accountService.withdraw(accountNumber, request.getAmount());
        return ResponseEntity.ok(account);
    }
}
