package com.training.banking.dto;

/**
 * Response body returned when something goes wrong.
 *
 * Think of this like the bank clerk handing back your form
 * with a note explaining what was wrong - maybe you forgot
 * to write your name, or the sort code was missing.
 *
 * This class is PRE-BUILT - no changes needed here.
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
