package com.training.banking.dto;

/**
 * DTO for creating a new account.
 * The client sends this JSON, and we create an Account entity from it.
 * Notice there is no "id" field - the database generates that automatically.
 */
public class CreateAccountRequest {

    private String accountNumber;
    private String holderName;
    private double balance;

    public CreateAccountRequest() {
    }

    public CreateAccountRequest(String accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
