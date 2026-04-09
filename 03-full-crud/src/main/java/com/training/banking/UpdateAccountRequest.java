package com.training.banking;

/**
 * DTO for updating account details (PUT request body).
 *
 * Only holderName and accountType can be changed via PUT.
 * The accountNumber is the identifier (in the URL path),
 * and the balance is only changed through deposit/withdraw.
 *
 * Example JSON:
 * {
 *   "holderName": "Priya Sharma-Patel",
 *   "accountType": "CURRENT"
 * }
 */
public class UpdateAccountRequest {

    private String holderName;
    private String accountType;

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
}
