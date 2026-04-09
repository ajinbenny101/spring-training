package com.training.banking;

/**
 * DTO for deposit and withdraw operations (PATCH request body).
 *
 * Both deposit and withdraw need the same thing - just an amount.
 * So we reuse one DTO for both endpoints.
 *
 * Example JSON:
 * {
 *   "amount": 500.00
 * }
 */
public class AmountRequest {

    private double amount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
