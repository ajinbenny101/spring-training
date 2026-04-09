package com.training.banking;

/**
 * Represents a bank account.
 *
 * This class has both getters AND setters because we need to update
 * account fields for PUT and PATCH operations. In Lab 02, our model
 * only had getters since we never modified it after creation.
 *
 * Think of this as the actual bank account record - it holds all the
 * data about one customer's account.
 */
public class Account {

    private String accountNumber;
    private String holderName;
    private double balance;
    private String accountType; // "SAVINGS" or "CURRENT"

    // Constructor - used when creating a new account
    public Account(String accountNumber, String holderName, double balance, String accountType) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
        this.accountType = accountType;
    }

    // Getters - used when Spring converts this object to JSON

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    // Setters - needed for PUT and PATCH operations
    // PUT uses setHolderName() and setAccountType()
    // PATCH (deposit/withdraw) uses setBalance()

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
