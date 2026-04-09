package com.training.banking.repository;

import com.training.banking.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    // No methods needed. Spring provides save(), findAll(),
    // findById(), deleteById(), count(), and more - automatically.
}
