package com.training.banking.service;

import com.training.banking.dto.CreateAccountRequest;
import com.training.banking.model.Account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * Service layer for account operations.
 *
 * This class is the "manager" of the bank. It contains all the business
 * logic - validation, account creation, data storage, lookups, and deletion.
 *
 * Notice: this class knows NOTHING about HTTP. No ResponseEntity, no status
 * codes, no @GetMapping. It just works with plain Java objects.
 *
 * Complete the TODOs below to implement the service.
 */

@Service
public class AccountService {

    // ============================================================
    // TODO 2: Add a private field to store accounts
    // ============================================================
    private List<Account> accounts = new ArrayList<>();
    //
    // Also add a counter for generating account numbers:
    private int nextId = 1;
    //
    // This ArrayList acts as our "database" for now. Every account
    // we create gets added here. Because Spring creates only ONE
    // AccountService instance, all controllers share this same list.
    // ============================================================


    // ============================================================
    // TODO 3: Implement createAccount
    // ============================================================
    public Account createAccount(CreateAccountRequest request){

        // Steps:
        //   a) Validate that holderName is not null or blank.
        //      If invalid, throw new IllegalArgumentException("holderName is required")
        if (request.getHolderName() == null || request.getHolderName().isBlank()) {
            throw new IllegalArgumentException("holderName is required");
        }
        //
        //   b) Validate that sortCode is not null or blank.
        //      If invalid, throw new IllegalArgumentException("sortCode is required")
        if (request.getSortCode() == null || request.getSortCode().isBlank()) {
            throw new IllegalArgumentException("sortCode is required");
        }
        //
        //   c) Validate that openingBalance is not negative.
        //      If invalid, throw new IllegalArgumentException("openingBalance cannot be negative")
        if (request.getOpeningBalance() < 0) {
            throw new IllegalArgumentException("openingBalance cannot be negative");
        }
        //
        //   d) Generate an account number: "ACC-" + nextId, then increment nextId
        String accountNumber = "ACC-" + nextId;
        nextId++;
        //
        //   e) Create a new Account object with the generated number,
        //      sortCode, holderName, and openingBalance from the request
        Account account = new Account(accountNumber, request.getSortCode(), request.getHolderName(), request.getOpeningBalance());
        //
        //   f) Add it to the accounts list
        accounts.add(account);
        //
        //   g) Return the new Account
        return account;
    }

    public List<Account> getAllAccounts() {
        return accounts;
    }

    public Account getAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public boolean deleteAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                accounts.remove(account);
                return true;
            }
        }
        return false;
    }

    // ============================================================
    // TODO 6: Implement deleteAccount
    // ============================================================
    // Method signature: public boolean deleteAccount(String accountNumber)
    //
    // Loop through the accounts list. If you find a matching account,
    // remove it from the list and return true.
    //
    // If no match is found, return false.
    //
    // Hint: use accounts.remove(account) inside the loop, but be
    // careful - you cannot remove from a list while iterating with
    // a for-each loop. Use an Iterator or find the account first,
    // then remove it after the loop.
    // ============================================================

}
