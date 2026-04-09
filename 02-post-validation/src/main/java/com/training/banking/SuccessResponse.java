package com.training.banking;

/**
 * Response body returned when an account is created successfully.
 *
 * Think of this like the confirmation slip the bank hands back to you
 * after your account has been opened - it says "all good" and shows
 * a summary of what was created.
 *
 * This class is PRE-BUILT for you - no changes needed here.
 */
public class SuccessResponse {

    private String message;
    private Account account;

    public SuccessResponse(String message, Account account) {
        this.message = message;
        this.account = account;
    }

    // --- Getters ---

    public String getMessage() {
        return message;
    }

    public Account getAccount() {
        return account;
    }
}
