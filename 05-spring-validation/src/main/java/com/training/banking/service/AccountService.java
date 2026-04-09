package com.training.banking.service;

import com.training.banking.dto.CreateAccountRequest;
import com.training.banking.model.Account;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer for account operations.
 *
 * Think of this as the back-office staff at the bank. The controller
 * (front desk) receives the form, and the service (back office)
 * actually processes it - creating the account record, storing it,
 * and retrieving it when asked.
 *
 * This class uses a simple ArrayList to store accounts in memory.
 * No database needed for this lab.
 *
 * This class is PRE-BUILT for you - no changes needed here.
 */
@Service
public class AccountService {

    private final List<Account> accounts = new ArrayList<>();

    /**
     * Creates a new account from the validated request data.
     *
     * By the time this method is called, Spring has already validated
     * the request. We can trust that the data is clean.
     */
    public Account createAccount(CreateAccountRequest request) {
        Account account = new Account(
                request.getAccountNumber(),
                request.getSortCode(),
                request.getHolderName(),
                request.getOpeningBalance()
        );
        accounts.add(account);
        return account;
    }

    /**
     * Returns all accounts created so far.
     */
    public List<Account> getAllAccounts() {
        return accounts;
    }
}
