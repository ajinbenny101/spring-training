package com.training.banking.service;

import com.training.banking.dto.CreateAccountRequest;
import com.training.banking.exception.AccountNotFoundException;
import com.training.banking.exception.DuplicateAccountException;
import com.training.banking.exception.InsufficientFundsException;
import com.training.banking.model.Account;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service layer that handles all account business logic.
 *
 * Think of this as the bank's back office - the tellers (controllers)
 * talk to customers and pass requests here. The back office does
 * the actual work: checking if accounts exist, verifying balances,
 * and throwing exceptions when something is wrong.
 *
 * Notice how this class uses "throw" instead of "return error response."
 * The service does not know or care about HTTP status codes - it just
 * says "this is wrong" by throwing an exception. The global exception
 * handler turns that into the right HTTP response.
 *
 * This class is PRE-BUILT for you - no changes needed here.
 */
@Service
public class AccountService {

    // In-memory storage - like a filing cabinet in the back office
    private final Map<String, Account> accounts = new HashMap<>();

    /**
     * Creates a new account.
     * Throws DuplicateAccountException if the account number already exists.
     */
    public Account createAccount(CreateAccountRequest request) {
        if (accounts.containsKey(request.getAccountNumber())) {
            throw new DuplicateAccountException(request.getAccountNumber());
        }

        Account account = new Account(
                request.getAccountNumber(),
                request.getHolderName(),
                request.getOpeningBalance()
        );

        accounts.put(account.getAccountNumber(), account);
        return account;
    }

    /**
     * Finds an account by its account number.
     * Throws AccountNotFoundException if no account with that number exists.
     */
    public Account getAccount(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException(accountNumber);
        }
        return account;
    }

    /**
     * Deposits money into an account.
     * Throws AccountNotFoundException if the account does not exist.
     */
    public Account deposit(String accountNumber, double amount) {
        Account account = getAccount(accountNumber);
        account.setBalance(account.getBalance() + amount);
        return account;
    }

    /**
     * Withdraws money from an account.
     * Throws AccountNotFoundException if the account does not exist.
     * Throws InsufficientFundsException if the balance is too low.
     */
    public Account withdraw(String accountNumber, double amount) {
        Account account = getAccount(accountNumber);
        if (account.getBalance() < amount) {
            throw new InsufficientFundsException(accountNumber, amount);
        }
        account.setBalance(account.getBalance() - amount);
        return account;
    }
}
