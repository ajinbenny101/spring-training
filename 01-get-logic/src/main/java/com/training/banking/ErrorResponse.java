package com.training.banking;

/**
 * DTO for error responses.
 *
 * When something goes wrong (like an invalid account type),
 * we return this object as JSON instead of the normal response.
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
