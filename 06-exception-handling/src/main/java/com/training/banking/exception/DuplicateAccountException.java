package com.training.banking.exception;

/**
 * Thrown when someone tries to create an account with a number
 * that already exists in the system.
 *
 * Think of it like trying to open a bank account with the same
 * account number as someone who already has one - the bank
 * says "sorry, that number is already taken."
 */

// =======================================================================
// TODO 2: Make this class a custom exception
//
// Steps:
//   1. Make this class extend RuntimeException
//   2. Add a constructor that takes a String parameter called accountNumber
//   3. Call super() with the message: "Account {accountNumber} already exists"
//      (replace {accountNumber} with the actual value)
//
// Example: new DuplicateAccountException("12345678")
//   should produce the message: "Account 12345678 already exists"
// =======================================================================

public class DuplicateAccountException extends RuntimeException {

    public DuplicateAccountException(String accountNumber) {
        super("Account " + accountNumber + " already exists");
    }

}
