package com.training.banking.dto;

/**
 * The response returned when something goes wrong.
 *
 * Example:
 * {
 *   "error": "CONVERSION_ERROR",
 *   "message": "Could not convert from XYZ to USD"
 * }
 */
public class ErrorResponse {

    private String error;
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
