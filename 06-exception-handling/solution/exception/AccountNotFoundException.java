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
public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String accountNumber) {
        super("Account " + accountNumber + " not found");
    }
}
