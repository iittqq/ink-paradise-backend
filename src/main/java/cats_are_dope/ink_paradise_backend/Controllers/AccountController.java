package cats_are_dope.ink_paradise_backend.Controllers;

import cats_are_dope.ink_paradise_backend.Models.Account;
import cats_are_dope.ink_paradise_backend.Models.JwtResponse;
import cats_are_dope.ink_paradise_backend.Models.LoginRequest;
import cats_are_dope.ink_paradise_backend.Models.UpdatePasswordRequest;
import cats_are_dope.ink_paradise_backend.Models.UpdateUsernameRequest;
import cats_are_dope.ink_paradise_backend.Repositories.AccountRepository;
import cats_are_dope.ink_paradise_backend.Services.AccountService;
import cats_are_dope.ink_paradise_backend.Services.JwtService;
import cats_are_dope.ink_paradise_backend.Services.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    return ResponseEntity.ok(account);
  }

  @Autowired private AccountService accountService;

  @Autowired private PasswordEncoder passwordEncoder;

  @PostMapping("/accounts/new")
  public Account createAccount(@Valid @RequestBody Account account, HttpServletRequest request)
      throws IOException {
    System.out.println("url: " + getSiteURL(request));

    if (account.getEmail() == null || account.getPassword() == null) {
      throw new IllegalArgumentException("Account email, password, and username cannot be null");
    }
    account.setPassword(passwordEncoder.encode(account.getPassword()));

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
      httpServletResponse.setHeader("Location", "https://ink-paradise.com/");
      httpServletResponse.setStatus(302);
    }
  }

  @Autowired private JwtService jwtService;

  @Autowired private RefreshTokenService refreshTokenService;

  @PostMapping("/accounts/refresh")
  public ResponseEntity<Map<String, String>> refresh(@RequestBody Map<String, String> request) {
    String refreshToken = request.get("refreshToken");

    try {
      // Check if the refresh token exists in the database and is valid
      if (!refreshTokenService.validateRefreshToken(refreshToken)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
      }

      // Validate expiration of the refresh token
      if (jwtService.isRefreshTokenExpired(refreshToken)) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
      }

      // Retrieve the account ID from the refresh token and generate a new access token
      String accountId = jwtService.getClaimsFromToken(refreshToken).getSubject();
      String newAccessToken = jwtService.generateAccessToken(Long.parseLong(accountId));

      Map<String, String> tokens = new HashMap<>();
      tokens.put("accessToken", newAccessToken);

      return ResponseEntity.ok(tokens);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
  }

  // Login Endpoint
  @PostMapping("/accounts/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    String email = loginRequest.getEmail();
    String password = loginRequest.getPassword();

    Account account = accountService.findAccountByEmail(email);
    if (account != null && passwordEncoder.matches(password, account.getPassword())) {
      // Generate JWT access token and refresh token
      String accessToken = jwtService.generateAccessToken(account.getId());
      String refreshToken = jwtService.generateRefreshToken(account.getId());

      // Store refresh token in the database associated with this account
      refreshTokenService.createOrUpdateRefreshToken(account.getId(), refreshToken);

      // Return both tokens
      return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken));
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
  }

  @GetMapping("/accounts/email/{email}")
  public Account getAccountByEmail(@PathVariable(value = "email") String email) {
    return accountRepository.findByEmail(email);
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
    account.setPassword(passwordEncoder.encode(accountDetails.getPassword()));
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
    if (passwordEncoder.matches(accountDetails.getOldPassword(), account.getPassword())) {
      account.setPassword(passwordEncoder.encode(accountDetails.getNewPassword()));

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

  @GetMapping("/accounts/reset/password/{email}")
  public void resetPassword(@PathVariable(value = "email") String email, HttpServletRequest request)
      throws IOException {
    Account account = accountService.findAccountByEmail(email);
    if (account != null) {
      accountService.resetPassword(account, getSiteURL(request));
    }
  }
}
