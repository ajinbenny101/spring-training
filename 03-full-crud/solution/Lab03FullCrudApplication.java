package com.training.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
public class Lab03FullCrudApplication {

    private List<Account> accounts = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(Lab03FullCrudApplication.class, args);
    }

    // POST /api/accounts - Create a new account
    @PostMapping("/api/accounts")
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest request) {
        Account account = new Account(
                request.getAccountNumber(),
                request.getHolderName(),
                request.getOpeningBalance(),
                request.getAccountType()
        );
        accounts.add(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    // GET /api/accounts - List all accounts
    @GetMapping("/api/accounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accounts);
    }

    // GET /api/accounts/{accountNumber} - Get one account
    @GetMapping("/api/accounts/{accountNumber}")
    public ResponseEntity<?> getAccount(@PathVariable String accountNumber) {
        Account account = findAccount(accountNumber);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT_FOUND", "Account not found: " + accountNumber));
        }
        return ResponseEntity.ok(account);
    }

    // PUT /api/accounts/{accountNumber} - Full update (holderName and accountType)
    @PutMapping("/api/accounts/{accountNumber}")
    public ResponseEntity<?> updateAccount(@PathVariable String accountNumber,
                                           @RequestBody UpdateAccountRequest request) {
        Account account = findAccount(accountNumber);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT_FOUND", "Account not found: " + accountNumber));
        }
        account.setHolderName(request.getHolderName());
        account.setAccountType(request.getAccountType());
        return ResponseEntity.ok(account);
    }

    // PATCH /api/accounts/{accountNumber}/deposit - Deposit money
    @PatchMapping("/api/accounts/{accountNumber}/deposit")
    public ResponseEntity<?> deposit(@PathVariable String accountNumber,
                                     @RequestBody AmountRequest request) {
        Account account = findAccount(accountNumber);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT_FOUND", "Account not found: " + accountNumber));
        }
        account.setBalance(account.getBalance() + request.getAmount());
        return ResponseEntity.ok(account);
    }

    // PATCH /api/accounts/{accountNumber}/withdraw - Withdraw money
    @PatchMapping("/api/accounts/{accountNumber}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable String accountNumber,
                                      @RequestBody AmountRequest request) {
        Account account = findAccount(accountNumber);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT_FOUND", "Account not found: " + accountNumber));
        }
        if (account.getBalance() < request.getAmount()) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("INSUFFICIENT_FUNDS",
                            "Cannot withdraw " + request.getAmount()
                                    + " from account with balance " + account.getBalance()));
        }
        account.setBalance(account.getBalance() - request.getAmount());
        return ResponseEntity.ok(account);
    }

    // DELETE /api/accounts/{accountNumber} - Close/remove account
    @DeleteMapping("/api/accounts/{accountNumber}")
    public ResponseEntity<?> deleteAccount(@PathVariable String accountNumber) {
        Account account = findAccount(accountNumber);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT_FOUND", "Account not found: " + accountNumber));
        }
        accounts.remove(account);
        return ResponseEntity.ok(Map.of("message", "Account " + accountNumber + " has been closed"));
    }

    // Helper - find an account by account number
    private Account findAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
}
