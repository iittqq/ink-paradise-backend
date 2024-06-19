package cats_are_dope.ink_paradise_backend.Repositories;

import cats_are_dope.ink_paradise_backend.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
  // Add custom query methods here if needed
  Account findByEmailAndPassword(String email, String password);

  Account findByVerificationCode(String code);
}
