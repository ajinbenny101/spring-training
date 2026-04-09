package com.training.banking;

/**
 * Represents a verified bank account stored in the system.
 *
 * Think of this as the official record the bank keeps after
 * they have checked your form and approved the account opening.
 * Once created, the core details do not change.
 *
 * This class is PRE-BUILT for you - no changes needed here.
 */
public class Account {

    private String accountNumber;
    private String sortCode;
    private String holderName;
    private double balance;

    public Account(String accountNumber, String sortCode, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.sortCode = sortCode;
        this.holderName = holderName;
        this.balance = balance;
    }

    // --- Getters ---

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getSortCode() {
        return sortCode;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }
}
