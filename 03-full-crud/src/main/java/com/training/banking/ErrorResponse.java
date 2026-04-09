package com.training.banking;

/**
 * DTO for error responses.
 *
 * When something goes wrong (account not found, insufficient funds, etc.),
 * we return this object as JSON instead of the normal response.
 * This gives the client a consistent error format they can rely on.
 */
public class ErrorResponse {

    private String error;
    private String message;

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
