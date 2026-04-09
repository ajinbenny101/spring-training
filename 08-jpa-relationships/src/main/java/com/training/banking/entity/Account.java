package com.training.banking.entity;

import java.util.ArrayList;
import java.util.List;

// TODO 4: Add @Entity annotation to mark this class as a JPA entity.
// Also add @Id and @GeneratedValue(strategy = GenerationType.IDENTITY) to the id field below.
//
// Hint: import jakarta.persistence.Entity;
// Hint: import jakarta.persistence.Id;
// Hint: import jakarta.persistence.GeneratedValue;
// Hint: import jakarta.persistence.GenerationType;
public class Account {

    // TODO 4 (continued): Add @Id and @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;

    private double balance;

    // TODO 5: Add the following three annotations to this field:
    //   @ManyToOne
    //   @JoinColumn(name = "customer_id")
    //   @JsonBackReference
    //
    // @ManyToOne tells JPA that many accounts belong to one customer.
    // @JoinColumn(name = "customer_id") creates a customer_id column in the accounts table.
    // @JsonBackReference tells Jackson to skip this field in JSON output (prevents infinite loop).
    //
    // Hint: import jakarta.persistence.ManyToOne;
    // Hint: import jakarta.persistence.JoinColumn;
    // Hint: import com.fasterxml.jackson.annotation.JsonBackReference;
    private Customer customer;

    // TODO 6: Add the following two annotations to this field:
    //   @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    //   @JsonManagedReference
    //
    // Same pattern as Customer -> Accounts, but now Account -> Transactions.
    // One account has many transactions.
    // mappedBy = "account" points to the 'account' field in the Transaction entity.
    //
    // Hint: import jakarta.persistence.OneToMany;
    // Hint: import jakarta.persistence.CascadeType;
    // Hint: import com.fasterxml.jackson.annotation.JsonManagedReference;
    private List<Transaction> transactions = new ArrayList<>();

    public Account() {
    }

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0.0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
