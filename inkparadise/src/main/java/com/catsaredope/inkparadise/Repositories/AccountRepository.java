package com.catsaredope.inkparadise.Repositories;

import com.catsaredope.inkparadise.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	// Add custom query methods here if needed
	@Query("select a from Account a where a.email = ?1 and a.password = ?2")
	Account findByEmailAndPassword(@Param("email") String email, @Param("password") String password);
}
