package cats_are_dope.ink_paradise_backend.Controllers;

import cats_are_dope.ink_paradise_backend.Models.AccountDetails;
import cats_are_dope.ink_paradise_backend.Repositories.AccountDetailsRepository;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

  @Autowired
  
  private AccountDetailsRepository accountDetailsRepository;

  @GetMapping("/account-details/find-by-accountId/{accountId}")
  public ResponseEntity<AccountDetails> getAccountDetailsByAccountId(
      @PathVariable(value = "accountId") long accountId) throws Exception {

    AccountDetails accountDetails = accountDetailsRepository.findByAccountId(accountId);

    return ResponseEntity.ok().body(accountDetails);
  }

  @PostMapping("/account-details/create-details/new")
  public ResponseEntity<String> createAccountDetails(
      @Valid @RequestBody AccountDetails accountDetails) {

    if (accountDetails.getAccountId() == 0
        || accountDetails.getBio() == null
        || accountDetails.getContentFilter() == 0) {
      throw new IllegalArgumentException("Account details missing:" + accountDetails);
    }

    accountDetailsRepository.save(accountDetails);

    return ResponseEntity.status(HttpStatus.CREATED).body("Account Details successfully created");
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
    account.setReaderMode(accountDetails.getReaderMode());
    account.setTheme(accountDetails.getTheme());
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
