package com.training.banking;

/**
 * DTO (Data Transfer Object) for creating a new bank account.
 *
 * Think of this like the paper form you fill in at a bank branch.
 * It holds the raw data from the customer before the bank checks it.
 *
 * This class is PRE-BUILT for you - no changes needed here.
 */
public class CreateAccountRequest {

    private String accountNumber;
    private String sortCode;
    private String holderName;
    private double openingBalance;

    // --- Getters and Setters ---

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
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
