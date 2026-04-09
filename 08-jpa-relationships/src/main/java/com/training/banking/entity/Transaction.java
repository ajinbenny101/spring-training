package com.training.banking.entity;

import java.time.LocalDateTime;

// TODO 7: Add @Entity annotation to mark this class as a JPA entity.
// Also add @Id and @GeneratedValue(strategy = GenerationType.IDENTITY) to the id field below.
//
// Hint: import jakarta.persistence.Entity;
// Hint: import jakarta.persistence.Id;
// Hint: import jakarta.persistence.GeneratedValue;
// Hint: import jakarta.persistence.GenerationType;
public class Transaction {

    // TODO 7 (continued): Add @Id and @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private double amount;

    private LocalDateTime timestamp;

    private String description;

    // TODO 8: Add the following three annotations to this field:
    //   @ManyToOne
    //   @JoinColumn(name = "account_id")
    //   @JsonBackReference
    //
    // @ManyToOne tells JPA that many transactions belong to one account.
    // @JoinColumn(name = "account_id") creates an account_id column in the transactions table.
    // @JsonBackReference tells Jackson to skip this field in JSON output (prevents infinite loop).
    //
    // Hint: import jakarta.persistence.ManyToOne;
    // Hint: import jakarta.persistence.JoinColumn;
    // Hint: import com.fasterxml.jackson.annotation.JsonBackReference;
    private Account account;

    public Transaction() {
    }

    public Transaction(String type, double amount, String description, Account account) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.account = account;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
