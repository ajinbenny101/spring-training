package com.training.banking.dto;

import java.time.LocalDateTime;

/**
 * Standard error response returned when something goes wrong.
 *
 * Think of this like a formal complaint receipt from the bank -
 * it has a category (error), a description (message), and
 * the date/time it happened (timestamp).
 *
 * This class is PRE-BUILT for you - no changes needed here.
 */
public class ErrorResponse {

    private String error;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    // --- Getters ---

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
