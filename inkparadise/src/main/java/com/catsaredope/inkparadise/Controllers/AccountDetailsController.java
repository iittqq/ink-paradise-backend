package com.catsaredope.inkparadise.Controllers;

import com.catsaredope.inkparadise.Models.AccountDetails;
import com.catsaredope.inkparadise.Repositories.AccountDetailsRepository;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AccountDetailsController {

  @Autowired private AccountDetailsRepository accountDetailsRepository;

  @GetMapping("/account-details/find-by-accountId/{accountId}")
  public ResponseEntity<AccountDetails> getAccountDetailsByAccountId(
      @PathVariable(value = "accountId") long accountId) throws Exception {

    AccountDetails accountDetails = accountDetailsRepository.findByAccountId(accountId);

    return ResponseEntity.ok().body(accountDetails);
  }

  @PostMapping("/account-details/create-details/new")
  public AccountDetails createAccountDetails(@Valid @RequestBody AccountDetails accountDetails) {

    if (accountDetails.getAccountId() == 0
        || accountDetails.getBio() == null
        || accountDetails.getBirthday() == null
        || accountDetails.getContentFilter() == 0) {
      throw new IllegalArgumentException("Account details missing:" + accountDetails);
    }

    System.out.println("Account Details: ");
    System.out.println(accountDetails);
    return accountDetailsRepository.save(accountDetails);
  }

  @PutMapping("/account-details/update-details/{id}")
  public ResponseEntity<AccountDetails> updateAccountDetails(
      @PathVariable(value = "id") long id, @Valid @RequestBody AccountDetails accountDetails)
      throws Exception {
    AccountDetails account = accountDetailsRepository.findByAccountId(id);
    account.setAccountId(accountDetails.getAccountId());
    account.setBio(accountDetails.getBio());
    account.setContentFilter(accountDetails.getContentFilter());
    account.setProfilePicture(accountDetails.getProfilePicture());
    account.setHeaderPicture(accountDetails.getHeaderPicture());
    account.setBirthday(accountDetails.getBirthday());
    final AccountDetails updatedAccountDetails = accountDetailsRepository.save(account);
    return ResponseEntity.ok(updatedAccountDetails);
  }

  @DeleteMapping("/account-details/delete-details/{id}")
  public Map<String, Boolean> deleteAccountDetails(@PathVariable(value = "id") long accountId)
      throws Exception {

    AccountDetails accountDetails = accountDetailsRepository.findByAccountId(accountId);

    if (accountDetails == null) {
      throw new Exception("Account not found for this id :: " + accountId);
    }
    accountDetailsRepository.delete(accountDetails);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }
}
