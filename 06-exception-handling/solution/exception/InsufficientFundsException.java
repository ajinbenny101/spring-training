package com.training.banking.exception;

/**
 * Thrown when someone tries to withdraw more money than
 * is available in their account.
 *
 * Think of it like going to an ATM and trying to take out
 * 500 pounds when you only have 200 - the machine says
 * "insufficient funds" and gives you nothing.
 */
public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String accountNumber, double amount) {
        super("Account " + accountNumber + " has insufficient funds for withdrawal of " + amount);
    }
}
