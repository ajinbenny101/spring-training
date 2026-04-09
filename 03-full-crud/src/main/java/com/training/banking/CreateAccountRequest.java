package com.training.banking;

/**
 * DTO for creating a new account (POST request body).
 *
 * This is what the client sends when they want to open a new account.
 * It includes an opening balance because every account starts with
 * some initial deposit.
 *
 * Example JSON:
 * {
 *   "accountNumber": "ACC001",
 *   "holderName": "Priya Sharma",
 *   "accountType": "SAVINGS",
 *   "openingBalance": 1000.00
 * }
 */
public class CreateAccountRequest {

    private String accountNumber;
    private String holderName;
    private String accountType;
    private double openingBalance;

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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(double openingBalance) {
        this.openingBalance = openingBalance;
    }
}
