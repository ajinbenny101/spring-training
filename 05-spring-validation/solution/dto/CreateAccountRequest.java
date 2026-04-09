package com.training.banking.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO (Data Transfer Object) for creating a new bank account.
 *
 * Think of this like the paper form you fill in at a bank branch.
 * Each field has rules printed next to it - "required", "8 digits only",
 * "format: XX-XX-XX". The annotations below are those printed rules,
 * and Spring is the clerk who checks them before accepting the form.
 *
 * SOLUTION FILE - this is the completed version with all annotations.
 */
public class CreateAccountRequest {

    // TODO 1: Add @NotBlank annotation to holderName
    @NotBlank
    private String holderName;

    // TODO 2: Add validation annotations to accountNumber
    @NotBlank
    @Size(min = 8, max = 8)
    @Pattern(regexp = "\\d{8}", message = "Account number must be 8 digits")
    private String accountNumber;

    // TODO 3: Add validation annotations to sortCode
    @NotBlank
    @Pattern(regexp = "\\d{2}-\\d{2}-\\d{2}", message = "Sort code must be in XX-XX-XX format")
    private String sortCode;

    // TODO 4: Add validation annotation to openingBalance
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
