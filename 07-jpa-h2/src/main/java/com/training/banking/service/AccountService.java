package com.training.banking.service;

import com.training.banking.entity.Account;
import com.training.banking.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for account operations.
 *
 * In previous labs, this logic lived inside the controller with an ArrayList.
 * Now the service talks to a JpaRepository, which talks to the database.
 * The controller calls the service, the service calls the repository,
 * and the repository handles all the SQL behind the scenes.
 */
@Service
public class AccountService {

    private final AccountRepository repository;
    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }


    // ----------------------------------------------------------------
    // TODO 4: Inject AccountRepository via constructor.
    //
    // 1. Declare a private final field:
    //      private final AccountRepository repository;
    //
    // 2. Create a constructor that accepts an AccountRepository:
    //      public AccountService(AccountRepository repository) {
    //          this.repository = repository;
    //      }
    //
    // Spring sees the @Service annotation, creates an instance of this
    // class, and automatically passes the AccountRepository into the
    // constructor. This is called "constructor injection."
    //
    // It's like a new employee showing up on day one and the company
    // handing them all the tools they need to do their job.
    // ----------------------------------------------------------------


    // ----------------------------------------------------------------
    // TODO 5: Implement createAccount.
    //
    // Use repository.save(account) to save the account to the database.
    // The save() method returns the saved entity with the auto-generated
    // ID filled in. Return that saved entity.
    //
    // Replace the "return null;" with your implementation.
    // ----------------------------------------------------------------
    public Account createAccount(Account account) {
        repository.save(account);
        return account;

    }

    // ----------------------------------------------------------------
    // TODO 6: Implement getAllAccounts.
    //
    // Use repository.findAll() to get all accounts from the database.
    // It returns a List<Account>. Just return it directly.
    //
    // Replace the "return List.of();" with your implementation.
    // ----------------------------------------------------------------
    public List<Account> getAllAccounts() {
        return repository.findAll();
    }

    // ----------------------------------------------------------------
    // TODO 7: Implement getAccountById.
    //
    // Use repository.findById(id) to find an account by its primary key.
    // This returns an Optional<Account> - it might contain an account
    // (if found) or be empty (if no account with that ID exists).
    //
    // Return the Optional directly. The controller will handle the
    // "found vs not found" logic.
    //
    // Replace the "return Optional.empty();" with your implementation.
    // ----------------------------------------------------------------
    public Optional<Account> getAccountById(Long id) {
        return repository.findById(id);
    }

    // ----------------------------------------------------------------
    // TODO 8: Implement deleteAccount.
    //
    // Use repository.deleteById(id) to delete the account from the
    // database. This method returns void (nothing).
    //
    // Note: the controller checks if the account exists before
    // calling this method, so you don't need to worry about
    // deleting a non-existent ID here.
    //
    // Add your implementation below.
    // ----------------------------------------------------------------
    public void deleteAccount(Long id) {
        repository.deleteById(id);
    }
}
