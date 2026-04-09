package com.training.banking.exception;

/**
 * Thrown when someone tries to look up an account that does not exist.
 *
 * Think of it like walking up to the bank counter and asking about
 * account "99999999" - the teller checks the system, finds nothing,
 * and says "sorry, that account does not exist."
 *
 * Instead of the controller building an error response itself,
 * it just throws this exception. The global exception handler
 * (the "complaints department") catches it and builds a clean
 * error response automatically.
 */

// =======================================================================
// TODO 1: Make this class a custom exception
//
// Steps:
//   1. Make this class extend RuntimeException
//   2. Add a constructor that takes a String parameter called accountNumber
//   3. Call super() with the message: "Account {accountNumber} not found"
//      (replace {accountNumber} with the actual value)
//
// Example: new AccountNotFoundException("12345678")
//   should produce the message: "Account 12345678 not found"
//
// Hint: use super("Account " + accountNumber + " not found");
// =======================================================================

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String accountNumber) {
        super("Account " + accountNumber + " not found");
    }

}
