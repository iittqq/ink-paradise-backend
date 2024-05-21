package com.catsaredope.inkparadise.Services;

import com.catsaredope.inkparadise.Models.Account;
import com.catsaredope.inkparadise.Repositories.AccountRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
  @Autowired private AccountRepository accountRepository;

  private final PasswordEncoder passwordEncoder;

  @Autowired
  public AccountService(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Autowired private JavaMailSender mailSender;

  public void registerAccount(Account account, String siteUrl)
      throws UnsupportedEncodingException, MessagingException {
    String encodedPassword = passwordEncoder.encode(account.getPassword());
    account.setPassword(encodedPassword);
    String randomCode = RandomStringUtils.randomAlphanumeric(64);

    account.setVerificationCode(randomCode);
    account.setVerified(false);
    accountRepository.save(account);

    sendVerificationEmail(account, siteUrl);
  }

  private void sendVerificationEmail(Account account, String siteUrl)
      throws MessagingException, UnsupportedEncodingException {
    String toAddress = account.getEmail();
    String fromAddress = "inkparadisemailingservice@gmail.com";
    String senderName = "Ink Paradise";
    String subject = "Please verify your registration";
    String content =
        "Dear [[name]],<br>"
            + "Please click the link below to verify your registration:<br>"
            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
            + "Thank you,<br>"
            + "Your company name.";

    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);

    helper.setFrom(fromAddress, senderName);
    helper.setTo(toAddress);
    helper.setSubject(subject);

    content = content.replace("[[name]]", account.getUsername());
    String verifyURL = siteUrl + "/verify?code=" + account.getVerificationCode();

    content = content.replace("[[URL]]", verifyURL);

    helper.setText(content, true);

    mailSender.send(message);
  }

  public boolean verify(String verificationCode) {
    Account account = accountRepository.findByVerificationCode(verificationCode);
    if (account == null || account.getVerified()) {
      return false;
    } else {
      account.setVerificationCode(null);
      account.setVerified(true);
      accountRepository.save(account);
      return true;
    }
  }

  public Account findAccountByEmailAndPassword(String email, String password) {
    return accountRepository.findByEmailAndPassword(email, password);
  }
}
