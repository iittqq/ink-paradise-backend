package cats_are_dope.ink_paradise_backend.Services;

import cats_are_dope.ink_paradise_backend.Models.AccountDetails;
import cats_are_dope.ink_paradise_backend.Repositories.AccountDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsService {
  @Autowired private AccountDetailsRepository accountDetailsRepository;

  public AccountDetails findAccountDetailsByAccountId(long id) {
    return accountDetailsRepository.findByAccountId(id);
  }
}
