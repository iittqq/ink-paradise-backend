package cats_are_dope.ink_paradise_backend.Controllers;

import cats_are_dope.ink_paradise_backend.Models.Account;
import cats_are_dope.ink_paradise_backend.Models.LoginRequest;
import cats_are_dope.ink_paradise_backend.Models.UpdatePasswordRequest;
import cats_are_dope.ink_paradise_backend.Models.UpdateUsernameRequest;
import cats_are_dope.ink_paradise_backend.Repositories.AccountRepository;
import cats_are_dope.ink_paradise_backend.Services.AccountService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AccountController {
  @Autowired private AccountRepository accountRepository;

  @GetMapping("/accounts")
  public List<Account> getAllAccounts() {
    return accountRepository.findAll();
  }

  @GetMapping("/accounts/{id}")
  public ResponseEntity<Account> getAccountById(@PathVariable(value = "id") Long accountId)
      throws Exception {
    if (accountId == null) {
      throw new IllegalArgumentException("Account id cannot be null");
    }
    Account account =
        accountRepository
            .findById(accountId)
            .orElseThrow(() -> new Exception("Account not found for this id :: " + accountId));
    return ResponseEntity.ok().body(account);
  }

  @Autowired private AccountService accountService;

  @PostMapping("/accounts/new")
  public Account createAccount(@Valid @RequestBody Account account, HttpServletRequest request)
      throws UnsupportedEncodingException, MessagingException {
    if (account.getEmail() == null || account.getPassword() == null) {
      throw new IllegalArgumentException("Account email, password, and username cannot be null");
    }
    accountService.registerAccount(account, getSiteURL(request));
    return accountRepository.save(account);
  }

  private String getSiteURL(HttpServletRequest request) {
    String siteURL = request.getRequestURL().toString();
    return siteURL.replace(request.getServletPath(), "");
  }

  @GetMapping("/accounts/verify")
  public void verifyAccount(
      @RequestParam("code") String code, HttpServletResponse httpServletResponse) {
    System.out.println("Code: " + code);
    if (accountService.verify(code)) {
      httpServletResponse.setHeader("Location", "http://18.117.15.147/");
      httpServletResponse.setStatus(302);
    }
  }

  @PostMapping("/accounts/login")
  public Account login(@RequestBody LoginRequest loginRequest) {
    String email = loginRequest.getEmail();
    String password = loginRequest.getPassword();

    return accountService.findAccountByEmailAndPassword(email, password);
  }

  @PutMapping("/accounts/update/{id}")
  public ResponseEntity<Account> updateAccount(
      @PathVariable(value = "id") Long accountId, @Valid @RequestBody Account accountDetails)
      throws Exception {
    if (accountId == null) {
      throw new IllegalArgumentException("Account id cannot be null");
    }
    Account account =
        accountRepository
            .findById(accountId)
            .orElseThrow(() -> new Exception("Account not found for this id :: " + accountId));

    account.setEmail(accountDetails.getEmail());
    account.setPassword(accountDetails.getPassword());
    account.setUsername(accountDetails.getUsername());
    final Account updatedAccount = accountRepository.save(account);
    return ResponseEntity.ok(updatedAccount);
  }

  @PutMapping("/accounts/update/username")
  public ResponseEntity<Account> updateAccountUsername(
      @RequestBody UpdateUsernameRequest accountDetails) throws Exception {
    if (accountDetails.getId() == 0 || accountDetails.getUsername() == null) {
      throw new IllegalArgumentException("Account id cannot be null");
    }
    Account account =
        accountRepository
            .findById(accountDetails.getId())
            .orElseThrow(
                () -> new Exception("Account not found for this id :: " + accountDetails.getId()));
    account.setUsername(accountDetails.getUsername());
    final Account updatedAccount = accountRepository.save(account);
    return ResponseEntity.ok(updatedAccount);
  }

  @PutMapping("/accounts/update/password")
  public ResponseEntity<Account> updateAccountPassword(
      @RequestBody UpdatePasswordRequest accountDetails) throws Exception {
    if (accountDetails.getId() == 0
        || accountDetails.getOldPassword() == null
        || accountDetails.getNewPassword() == null) {
      throw new IllegalArgumentException("Account details cannot be null");
    }
    Account account =
        accountRepository
            .findById(accountDetails.getId())
            .orElseThrow(
                () -> new Exception("Account not found for this id :: " + accountDetails.getId()));
    if (account.getPassword().equals(accountDetails.getOldPassword())) {
      account.setPassword(accountDetails.getNewPassword());
      final Account updatedAccount = accountRepository.save(account);
      return ResponseEntity.ok(updatedAccount);
    } else {
      throw new IllegalArgumentException("Old password is incorrect");
    }
  }

  @DeleteMapping("/accounts/remove/{id}")
  public Map<String, Boolean> deleteAccount(@PathVariable(value = "id") Long accountId)
      throws Exception {
    if (accountId == null) {
      throw new IllegalArgumentException("Account id cannot be null");
    }
    Account account =
        accountRepository
            .findById(accountId)
            .orElseThrow(() -> new Exception("Account not found for this id :: " + accountId));

    if (account == null) {
      throw new IllegalArgumentException("Account cannot be null");
    }

    accountRepository.delete(account);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }
}
