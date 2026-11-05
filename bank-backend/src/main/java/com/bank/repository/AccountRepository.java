package com.bank.repository;

import com.bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
  Optional<Account> findByUsernameAndPassword(String username, String password);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);
}
