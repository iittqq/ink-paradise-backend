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

  @GetMapping("/account-details/{id}")
  public ResponseEntity<AccountDetails> getAccountDetailsById(
      @PathVariable(value = "id") long accountId) throws Exception {

    AccountDetails accountDetails = accountDetailsRepository.findByAccountId(accountId);

    return ResponseEntity.ok().body(accountDetails);
  }

  @PostMapping("/account-details/new")
  public AccountDetails createAccountDetails(@Valid @RequestBody AccountDetails accountDetails) {

    return accountDetailsRepository.save(accountDetails);
  }

  @PutMapping("/account-details/{id}")
  public ResponseEntity<AccountDetails> updateAccountDetails(
      @PathVariable(value = "id") long accountId,
      @Valid @RequestBody AccountDetails newAccountDetails)
      throws Exception {
    AccountDetails accountDetails = accountDetailsRepository.findByAccountId(accountId);
    accountDetails.setAccountId(newAccountDetails.getAccountId());
    accountDetails.setUsername(newAccountDetails.getUsername());
    accountDetails.setBio(newAccountDetails.getBio());
    accountDetails.setProfilePicture(newAccountDetails.getProfilePicture());
    accountDetails.setHeaderPicture(newAccountDetails.getHeaderPicture());
    accountDetails.setBirthday(newAccountDetails.getBirthday());
    final AccountDetails updatedAccountDetails = accountDetailsRepository.save(accountDetails);
    return ResponseEntity.ok(updatedAccountDetails);
  }

  @DeleteMapping("/account-details/{id}")
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
