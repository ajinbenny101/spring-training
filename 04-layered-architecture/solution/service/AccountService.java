package com.training.banking.service;

import com.training.banking.dto.CreateAccountRequest;
import com.training.banking.model.Account;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer for account operations.
 *
 * This class is the "manager" of the bank. It contains all the business
 * logic - validation, account creation, data storage, lookups, and deletion.
 *
 * Notice: this class knows NOTHING about HTTP. No ResponseEntity, no status
 * codes, no @GetMapping. It just works with plain Java objects.
 */
@Service
public class AccountService {

    private final List<Account> accounts = new ArrayList<>();
    private int nextId = 1;

    /**
     * Validates the request, creates a new account, and stores it.
     *
     * @param request the account creation request from the client
     * @return the newly created Account
     * @throws IllegalArgumentException if any required field is missing or invalid
     */
    public Account createAccount(CreateAccountRequest request) {
        // Validate holderName
        if (request.getHolderName() == null || request.getHolderName().isBlank()) {
            throw new IllegalArgumentException("holderName is required");
        }

        // Validate sortCode
        if (request.getSortCode() == null || request.getSortCode().isBlank()) {
            throw new IllegalArgumentException("sortCode is required");
        }

        // Validate openingBalance
        if (request.getOpeningBalance() < 0) {
            throw new IllegalArgumentException("openingBalance cannot be negative");
        }

        // Generate account number
        String accountNumber = "ACC-" + nextId;
        nextId++;

        // Create and store the account
        Account account = new Account(
                accountNumber,
                request.getSortCode(),
                request.getHolderName(),
                request.getOpeningBalance()
        );

        accounts.add(account);

        return account;
    }

    /**
     * Returns all accounts in the system.
     */
    public List<Account> getAllAccounts() {
        return accounts;
    }

    /**
     * Finds an account by its account number.
     *
     * @param accountNumber the account number to search for
     * @return the matching Account, or null if not found
     */
    public Account getAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Deletes an account by its account number.
     *
     * @param accountNumber the account number to delete
     * @return true if the account was found and removed, false otherwise
     */
    public boolean deleteAccount(String accountNumber) {
        Account toRemove = null;
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                toRemove = account;
                break;
            }
        }

        if (toRemove != null) {
            accounts.remove(toRemove);
            return true;
        }

        return false;
    }
}
