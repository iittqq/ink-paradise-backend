package com.catsaredope.inkparadise.Repositories;

import com.catsaredope.inkparadise.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
  // Add custom query methods here if needed
  Account findByEmailAndPassword(String email, String password);
}
