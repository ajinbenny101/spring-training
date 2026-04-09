package com.training.banking.dto;

/**
 * DTO for requests that involve a money amount (deposits, withdrawals).
 *
 * Think of this like a deposit/withdrawal slip at the bank -
 * you just write how much you want to deposit or take out.
 *
 * This class is PRE-BUILT for you - no changes needed here.
 */
public class AmountRequest {

    private double amount;

    // --- Getters and Setters ---

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
