package com.training.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;




@SpringBootApplication
@RestController
public class Lab03FullCrudApplication {

    // Storage for all accounts - an ArrayList that grows as accounts are added.
    // Unlike a fixed-size array, this list expands automatically.
    private List<Account> accounts = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(Lab03FullCrudApplication.class, args);
    }



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

    @GetMapping("/api/accounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/api/accounts/{accountNumber}")
    public ResponseEntity<?> getAccount(@PathVariable String accountNumber) {
        Account account = findAccount(accountNumber);
        if (account == null) {
            ErrorResponse errorResponse = new ErrorResponse("NOT_FOUND", "Account not found: " + accountNumber);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        return ResponseEntity.ok(account);
    }

    @PutMapping("/api/accounts/{accountNumber}")
        public ResponseEntity<?> updateAccount(@PathVariable String accountNumber, @RequestBody UpdateAccountRequest request) {
        Account account = findAccount(accountNumber);
        if (account == null) {
            ErrorResponse errorResponse = new ErrorResponse("NOT_FOUND", "Account not found: " + accountNumber);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        account.setHolderName(request.getHolderName());
        account.setAccountType(request.getAccountType());
        return ResponseEntity.ok(account);

    }
    
    @PatchMapping("/api/accounts/{accountNumber}/deposit")
    public ResponseEntity<?> deposit(@PathVariable String accountNumber, @RequestBody AmountRequest request) {
        Account account = findAccount(accountNumber);
        if (account == null) {
            ErrorResponse errorResponse = new ErrorResponse("NOT_FOUND", "Account not found: " + accountNumber);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        account.setBalance(account.getBalance() + request.getAmount());
        return ResponseEntity.ok(account);
    }

    @PatchMapping("/api/accounts/{accountNumber}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable String accountNumber, @RequestBody AmountRequest request) {
        Account account = findAccount(accountNumber);
        if (account == null) {
            ErrorResponse errorResponse = new ErrorResponse("NOT_FOUND", "Account not found: " + accountNumber);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        if (account.getBalance() < request.getAmount()) {
            ErrorResponse errorResponse = new ErrorResponse(
                    "INSUFFICIENT_FUNDS",
                    "Cannot withdraw " + request.getAmount() + " from account with balance " + account.getBalance()
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }
        account.setBalance(account.getBalance() - request.getAmount());
        return ResponseEntity.ok(account);
    }

    @DeleteMapping("/api/accounts/{accountNumber}")
    public ResponseEntity<?> deleteAccount(@PathVariable String accountNumber) {
        Account account = findAccount(accountNumber);
        if (account == null) {
            ErrorResponse errorResponse = new ErrorResponse("NOT_FOUND", "Account not found: " + accountNumber);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        accounts.remove(account);
        return ResponseEntity.ok(Map.of("message", "Account " + accountNumber + " has been closed"));
    }   


  
    
    // ================================================================


    // ================================================================
    // HELPER: Find an account by account number
    // ================================================================
    // This method is used by almost every endpoint above.
    // It loops through the accounts list and returns the matching account,
    // or null if no account has that number.
    //
    // Steps:
    //   a) Loop through the accounts list (for-each loop)
    //   b) Check if account.getAccountNumber().equals(accountNumber)
    //   c) If it matches, return that account
    //   d) If the loop finishes without finding one, return null
    // ================================================================
    private Account findAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
}
