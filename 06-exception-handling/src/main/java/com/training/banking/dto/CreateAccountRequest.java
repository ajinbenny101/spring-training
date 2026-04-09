package com.training.banking.dto;

/**
 * DTO (Data Transfer Object) for creating a new bank account.
 *
 * Think of this like the paper form you fill in at a bank branch.
 * It holds the raw data from the customer before the bank processes it.
 *
 * This class is PRE-BUILT for you - no changes needed here.
 */
public class CreateAccountRequest {

    private String accountNumber;
    private String holderName;
    private double openingBalance;

    // --- Getters and Setters ---

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

    public double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(double openingBalance) {
        this.openingBalance = openingBalance;
    }
}
