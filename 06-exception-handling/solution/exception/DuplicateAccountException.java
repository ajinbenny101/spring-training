package com.training.banking.exception;

/**
 * Thrown when someone tries to create an account with a number
 * that already exists in the system.
 *
 * Think of it like trying to open a bank account with the same
 * account number as someone who already has one - the bank
 * says "sorry, that number is already taken."
 */
public class DuplicateAccountException extends RuntimeException {

    public DuplicateAccountException(String accountNumber) {
        super("Account " + accountNumber + " already exists");
    }
}
