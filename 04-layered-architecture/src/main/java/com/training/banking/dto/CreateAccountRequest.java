package com.training.banking.dto;

/**
 * DTO (Data Transfer Object) for creating a new bank account.
 *
 * Think of this like the paper form you fill in at a bank branch.
 * It holds the raw data from the customer before the bank checks it.
 *
 * Why is this separate from Account? Because the customer does NOT
 * get to choose their account number - the bank generates it. So
 * the request has different fields than the final Account object.
 *
 * This class is PRE-BUILT - no changes needed here.
 */
public class CreateAccountRequest {

    private String holderName;
    private String sortCode;
    private double openingBalance;

    // --- Getters and Setters ---

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(double openingBalance) {
        this.openingBalance = openingBalance;
    }
}
