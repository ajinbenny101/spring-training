package com.training.banking.controller;

import com.training.banking.dto.CreateAccountRequest;
import com.training.banking.dto.CreateCustomerRequest;
import com.training.banking.dto.CreateTransactionRequest;
import com.training.banking.entity.Account;
import com.training.banking.entity.Customer;
import com.training.banking.entity.Transaction;
import com.training.banking.repository.AccountRepository;
import com.training.banking.repository.CustomerRepository;
import com.training.banking.repository.TransactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class BankingController {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public BankingController(CustomerRepository customerRepository,
                             AccountRepository accountRepository,
                             TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    // 1) POST /api/customers - create a customer
    @PostMapping("/api/customers")
    public ResponseEntity<?> createCustomer(@RequestBody CreateCustomerRequest request) {
        Customer customer = new Customer(request.getName(), request.getEmail());
        Customer saved = customerRepository.save(customer);
        return ResponseEntity.ok(saved);
    }

    // 2) GET /api/customers/{customerId} - get customer with their accounts
    @GetMapping("/api/customers/{customerId}")
    public ResponseEntity<?> getCustomer(@PathVariable Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "Customer not found"));
        }
        return ResponseEntity.ok(customer.get());
    }

    // 3) POST /api/customers/{customerId}/accounts - create account for customer
    @PostMapping("/api/customers/{customerId}/accounts")
    public ResponseEntity<?> createAccount(@PathVariable Long customerId,
                                           @RequestBody CreateAccountRequest request) {
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "Customer not found"));
        }

        Customer customer = customerOpt.get();
        Account account = new Account(request.getAccountNumber());
        account.setCustomer(customer);
        Account saved = accountRepository.save(account);
        return ResponseEntity.ok(saved);
    }

    // 4) POST /api/accounts/{accountId}/transactions - create transaction
    @PostMapping("/api/accounts/{accountId}/transactions")
    public ResponseEntity<?> createTransaction(@PathVariable Long accountId,
                                               @RequestBody CreateTransactionRequest request) {
        Optional<Account> accountOpt = accountRepository.findById(accountId);
        if (accountOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "Account not found"));
        }

        Account account = accountOpt.get();

        // Update balance based on transaction type
        if ("DEPOSIT".equalsIgnoreCase(request.getType())) {
            account.setBalance(account.getBalance() + request.getAmount());
        } else if ("WITHDRAWAL".equalsIgnoreCase(request.getType())) {
            if (account.getBalance() < request.getAmount()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Insufficient funds"));
            }
            account.setBalance(account.getBalance() - request.getAmount());
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid transaction type. Use DEPOSIT or WITHDRAWAL."));
        }

        Transaction transaction = new Transaction(
                request.getType().toUpperCase(),
                request.getAmount(),
                request.getDescription(),
                account
        );

        account.getTransactions().add(transaction);
        accountRepository.save(account);

        return ResponseEntity.ok(transaction);
    }

    // 5) GET /api/accounts/{accountId}/transactions - get all transactions for account
    @GetMapping("/api/accounts/{accountId}/transactions")
    public ResponseEntity<?> getTransactions(@PathVariable Long accountId) {
        Optional<Account> accountOpt = accountRepository.findById(accountId);
        if (accountOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "Account not found"));
        }

        List<Transaction> transactions = transactionRepository.findByAccountId(accountId);
        return ResponseEntity.ok(transactions);
    }
}
