package com.catsaredope.inkparadise.Controllers;

import com.catsaredope.inkparadise.Models.AccountDetails;
import com.catsaredope.inkparadise.Repositories.AccountDetailsRepository;
import com.catsaredope.inkparadise.Services.ImageService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
public class AccountDetailsController {

  @Autowired private AccountDetailsRepository accountDetailsRepository;
  @Autowired private ImageService imageService;

  @GetMapping("/account-details/find-by-accountId/{accountId}")
  public ResponseEntity<AccountDetails> getAccountDetailsByAccountId(
      @PathVariable(value = "accountId") long accountId) throws Exception {

    AccountDetails accountDetails = accountDetailsRepository.findByAccountId(accountId);

    return ResponseEntity.ok().body(accountDetails);
  }

  @PostMapping("/account-details/create-details/new")
  public AccountDetails createAccountDetails(
      @Valid @RequestBody AccountDetails accountDetails,
      @RequestParam("profilePicture") MultipartFile[] profilePicture,
      @RequestParam("headerPicture") MultipartFile[] headerPicture) {
    if (accountDetails.getAccountId() == 0
        || accountDetails.getBio() == null
        || accountDetails.getBirthday() == null
        || accountDetails.getContentFilter() == 0) {
      throw new IllegalArgumentException("Account details missing:" + accountDetails);
    }

    String uploadDirectory = "src/main/resources/static/images/pictures";
    String profilePictureString = "";
    String headerPictureString = "";
    try {

      for (MultipartFile imageFile : profilePicture) {
        profilePictureString = imageService.saveImageToStorage(uploadDirectory, imageFile);
      }
      accountDetails.setProfilePicture(profilePictureString);
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      for (MultipartFile imageFile : headerPicture) {
        headerPictureString = imageService.saveImageToStorage(uploadDirectory, imageFile);
      }
      accountDetails.setHeaderPicture(headerPictureString);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return accountDetailsRepository.save(accountDetails);
  }

  @PutMapping("/account-details/update-details/{id}")
  public ResponseEntity<AccountDetails> updateAccountDetails(
      @PathVariable(value = "id") long id, @Valid @RequestBody AccountDetails newAccountDetails)
      throws Exception {
    AccountDetails accountDetails = accountDetailsRepository.findByAccountId(id);
    accountDetails.setAccountId(newAccountDetails.getAccountId());
    accountDetails.setBio(newAccountDetails.getBio());

    accountDetails.setBirthday(newAccountDetails.getBirthday());
    final AccountDetails updatedAccountDetails = accountDetailsRepository.save(accountDetails);
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
