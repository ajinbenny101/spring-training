package com.training.banking.exception;

/**
 * Thrown when someone tries to withdraw more money than
 * is available in their account.
 *
 * Think of it like going to an ATM and trying to take out
 * 500 pounds when you only have 200 - the machine says
 * "insufficient funds" and gives you nothing.
 */

// =======================================================================
// TODO 3: Make this class a custom exception
//
// Steps:
//   1. Make this class extend RuntimeException
//   2. Add a constructor that takes two parameters:
//      - String accountNumber
//      - double amount
//   3. Call super() with the message:
//      "Account {accountNumber} has insufficient funds for withdrawal of {amount}"
//      (replace {accountNumber} and {amount} with the actual values)
//
// Example: new InsufficientFundsException("12345678", 500.00)
//   should produce the message:
//   "Account 12345678 has insufficient funds for withdrawal of 500.0"
// =======================================================================

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String accountNumber, double amount) {
        super("Account " + accountNumber + " has insufficient funds for withdrawal of " + amount);
    }

}
