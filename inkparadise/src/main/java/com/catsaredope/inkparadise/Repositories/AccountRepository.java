package com.catsaredope.inkparadise.Repositories;

import com.catsaredope.inkparadise.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	// Add custom query methods here if needed
	@Query("SELECT a FROM Account a WHERE a.email = :#{#account.email} AND a.password = :#{account.password}")
	Account findByEmailAndPassword(@Param("account") Account account);
}
