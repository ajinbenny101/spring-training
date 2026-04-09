package com.training.banking.model;

/**
 * Represents a bank account stored in the system.
 *
 * Think of this as the official record the bank keeps after
 * they have checked your form and approved the account opening.
 *
 * This class is PRE-BUILT for you - no changes needed here.
 */
public class Account {

    private String accountNumber;
    private String holderName;
    private double balance;

    public Account(String accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    // --- Getters and Setters ---

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
