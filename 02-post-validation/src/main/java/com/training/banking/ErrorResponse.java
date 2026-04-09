package com.training.banking;

/**
 * Response body returned when something goes wrong.
 *
 * Think of this like the bank clerk shaking their head and handing
 * back your form with a note explaining what was wrong - maybe
 * you forgot to sign it, or the account number was invalid.
 *
 * This class is PRE-BUILT for you - no changes needed here.
 */
public class ErrorResponse {

    private String error;
    private String message;

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    // --- Getters ---

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
