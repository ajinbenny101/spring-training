package com.training.banking.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.training.banking.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}

// ----------------------------------------------------------------
// TODO 3: Create an interface that extends JpaRepository.
//
// This is the most magical part of Spring Data JPA. You write an
// interface (not a class!) that extends JpaRepository, and Spring
// automatically creates a full implementation with methods like:
//   - save(Account account)     - saves to database
//   - findAll()                 - returns all accounts
//   - findById(Long id)         - finds one by primary key
//   - deleteById(Long id)       - deletes by primary key
//   - count()                   - counts all records
//   ...and many more.
//
// You need to:
// 1. Import org.springframework.data.jpa.repository.JpaRepository
// 2. Import com.training.banking.entity.Account
// 3. Change the line below to:
//      public interface AccountRepository extends JpaRepository<Account, Long> {
//      }
//
// The two type parameters are:
//   - Account = the entity type this repository manages
//   - Long    = the type of the entity's primary key (@Id field)
//
// That's it. No methods to write. Spring provides everything.
// ----------------------------------------------------------------
