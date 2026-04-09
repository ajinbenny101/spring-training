package com.training.banking.entity;

import java.util.ArrayList;
import java.util.List;

// TODO 1: Add @Entity annotation to mark this class as a JPA entity.
// Hint: import jakarta.persistence.Entity;
public class Customer {

    // TODO 2: Add @Id and @GeneratedValue(strategy = GenerationType.IDENTITY)
    // to make this field the auto-generated primary key.
    // Hint: import jakarta.persistence.Id;
    // Hint: import jakarta.persistence.GeneratedValue;
    // Hint: import jakarta.persistence.GenerationType;
    private Long id;

    private String name;

    private String email;

    // TODO 3: Add the following two annotations to this field:
    //   @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    //   @JsonManagedReference
    //
    // @OneToMany tells JPA that one customer has many accounts.
    // mappedBy = "customer" points to the 'customer' field in the Account entity.
    // cascade = CascadeType.ALL means saving a customer also saves its accounts.
    // @JsonManagedReference tells Jackson to include accounts in the JSON output.
    //
    // Hint: import jakarta.persistence.OneToMany;
    // Hint: import jakarta.persistence.CascadeType;
    // Hint: import com.fasterxml.jackson.annotation.JsonManagedReference;
    private List<Account> accounts = new ArrayList<>();

    public Customer() {
    }

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
