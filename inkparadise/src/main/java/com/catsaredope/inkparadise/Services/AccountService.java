package com.catsaredope.inkparadise.Services;

import com.catsaredope.inkparadise.Models.Account;
import com.catsaredope.inkparadise.Repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
  @Autowired private AccountRepository accountRepository;

  public Account findAccountByEmailAndPassword(String email, String password) {
    return accountRepository.findByEmailAndPassword(email, password);
  }
}
