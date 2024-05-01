package com.catsaredope.inkparadise.Services;

import com.catsaredope.inkparadise.Models.AccountDetails;
import com.catsaredope.inkparadise.Repositories.AccountDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsService {
  @Autowired private AccountDetailsRepository accountDetailsRepository;

  public AccountDetails findAccountDetailsByAccountId(long id) {
    return accountDetailsRepository.findByAccountId(id);
  }
}
