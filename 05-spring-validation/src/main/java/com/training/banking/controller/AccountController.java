package com.training.banking.controller;

import com.training.banking.dto.CreateAccountRequest;
import com.training.banking.model.Account;
import com.training.banking.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for bank account operations.
 *
 * Notice the @Valid annotation on the @RequestBody parameter in the POST method.
 * That single annotation is what triggers Spring's validation engine.
 * Without it, the annotations on CreateAccountRequest would be ignored.
 *
 * Think of @Valid as flipping the "check this form" switch. The validation
 * rules are written on the form (the DTO), but someone has to actually
 * read them and enforce them. @Valid is that someone.
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

    /**
     * POST /api/accounts - Create a new bank account.
     *
     * The @Valid annotation tells Spring to validate the request body
     * BEFORE this method runs. If validation fails, Spring throws
     * MethodArgumentNotValidException and this method never executes.
     *
     * It's like a bouncer at the door - if your ID is invalid,
     * you don't get in. The bouncer (Spring) handles the rejection,
     * not the bartender (this method).
     */
    @PostMapping
    public ResponseEntity<Account> create(@Valid @RequestBody CreateAccountRequest request) {
        Account account = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    /**
     * GET /api/accounts - List all accounts.
     *
     * No validation needed here - it's a simple read operation.
     */
    @GetMapping
    public ResponseEntity<List<Account>> getAll() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
}
