package com.catsaredope.inkparadise.Repositories;

import com.catsaredope.inkparadise.Models.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetails, Long> {
  AccountDetails findById(long id);
}
