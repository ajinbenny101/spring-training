package com.training.banking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;



// ============================================================

/**
 * DTO (Data Transfer Object) for creating a new bank account.
 *
 * Think of this like the paper form you fill in at a bank branch.
 * Each field has rules printed next to it - "required", "8 digits only",
 * "format: XX-XX-XX". The annotations below are those printed rules,
 * and Spring is the clerk who checks them before accepting the form.
 *
 * YOUR WORK: Add validation annotations to each field (see TODOs below).
 */
public class CreateAccountRequest {

    @NotBlank
    private String holderName;

    // ============================================================
    // TODO 2: Add validation annotations to accountNumber
    // ============================================================
    // This field needs THREE annotations:
    //
    //   @NotBlank
    //   - The field is required (can't be null or empty)
    //
    //   @Size(min = 8, max = 8)
    //   - Must be exactly 8 characters long
    //
    //   @Pattern(regexp = "\\d{8}", message = "Account number must be 8 digits")
    //   - Must be exactly 8 digits (no letters, no spaces)
    //   - The "message" is what the user sees if validation fails
    //
    // Add all three above the field.
    // ============================================================
    @NotBlank
    @Size(min = 8, max = 8)
    @Pattern(regexp = "\\d{8}", message = "Account number must be 8 digits")
    private String accountNumber;

    // ============================================================
    // TODO 3: Add validation annotations to sortCode
    // ============================================================
    // This field needs TWO annotations:
    //
    //   @NotBlank
    //   - The field is required
    //
    //   @Pattern(regexp = "\\d{2}-\\d{2}-\\d{2}", message = "Sort code must be in XX-XX-XX format")
    //   - Must match the format: two digits, dash, two digits, dash, two digits
    //   - Example: "12-34-56"
    //
    // Add both above the field.
    // ============================================================
    @NotBlank
    @Pattern(regexp = "\\d{2}-\\d{2}-\\d{2}", message = "Sort code must be in XX-XX-XX format")
    private String sortCode;

    // ============================================================
    // TODO 4: Add validation annotation to openingBalance
    // ============================================================
    // This field needs ONE annotation:
    //
    //   @DecimalMin(value = "0.0", message = "Opening balance must be 0 or positive")
    //   - The balance must be zero or greater
    //   - No one opens a bank account with negative money!
    //
    // Add it above the field.
    // ============================================================
    @DecimalMin(value = "0.0", message = "Opening balance must be 0 or positive")
    private double openingBalance;

    // --- Getters and Setters ---

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(double openingBalance) {
        this.openingBalance = openingBalance;
    }
}
