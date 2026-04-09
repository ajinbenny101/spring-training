package com.training.banking.controller;

import com.training.banking.dto.CreateAccountRequest;
import com.training.banking.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/accounts")
public class AdminController {

    private final List<Account> accounts = new ArrayList<>(List.of(
            new Account("10001000", "Asha Patel", 2500.00),
            new Account("10002000", "Ravi Sharma", 18000.00)
    ));

    @GetMapping
    public ResponseEntity<List<Account>> getAccounts() {
        return ResponseEntity.ok(accounts);
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequest request) {
        Account account = new Account(
                request.getAccountNumber(),
                request.getHolderName(),
                request.getBalance()
        );
        accounts.add(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }
}
