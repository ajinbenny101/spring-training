package com.training.banking;

/**
 * DTO (Data Transfer Object) for the interest rate response.
 *
 * This object is what gets converted to JSON and sent back to the client.
 * Spring Boot automatically converts this Java object into JSON using Jackson.
 */
public class RateResponse {

    private String accountType;
    private double annualInterestRate;
    private double minimumBalance;
    private String currency;
    private String timestamp;

    public RateResponse(String accountType, double annualInterestRate, double minimumBalance, String currency, String timestamp) {
        this.accountType = accountType;
        this.annualInterestRate = annualInterestRate;
        this.minimumBalance = minimumBalance;
        this.currency = currency;
        this.timestamp = timestamp;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }

    public String getCurrency() {
        return currency;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
