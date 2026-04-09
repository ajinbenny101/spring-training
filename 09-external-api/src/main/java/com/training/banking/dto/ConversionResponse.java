package com.training.banking.dto;

/**
 * The response returned when a currency conversion succeeds.
 *
 * Example:
 * {
 *   "from": "GBP",
 *   "to": "USD",
 *   "originalAmount": 100.00,
 *   "rate": 1.265,
 *   "convertedAmount": 126.50
 * }
 */
public class ConversionResponse {

    private String from;
    private String to;
    private double originalAmount;
    private double rate;
    private double convertedAmount;

    public ConversionResponse() {
    }

    public ConversionResponse(String from, String to, double originalAmount, double rate, double convertedAmount) {
        this.from = from;
        this.to = to;
        this.originalAmount = originalAmount;
        this.rate = rate;
        this.convertedAmount = convertedAmount;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public double getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(double originalAmount) {
        this.originalAmount = originalAmount;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }
}
