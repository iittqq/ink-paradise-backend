package cats_are_dope.ink_paradise_backend.Services;

import cats_are_dope.ink_paradise_backend.Models.Account;
import cats_are_dope.ink_paradise_backend.Repositories.AccountRepository;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import java.io.IOException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
  @Autowired private AccountRepository accountRepository;

  @Value("${sendgrid.api.key}")
  private String sendGridApiKey;

  public void registerAccount(Account account, String siteUrl) throws IOException {

    String randomCode = RandomStringUtils.randomAlphanumeric(64);

    account.setVerificationCode(randomCode);
    account.setVerified(false);
    accountRepository.save(account);

    Email from = new Email("mail@ink-paradise.com");
    String subject = "Email Verification";
    Email to = new Email(account.getEmail());
    Content content = new Content("text/html", buildEmailContent(siteUrl, account));
    Mail mail = new Mail(from, subject, to, content);

    SendGrid sg = new SendGrid(sendGridApiKey);
    Request request = new Request();
    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      Response response = sg.api(request);
      System.out.println(response.getStatusCode());
      System.out.println(response.getBody());
      System.out.println(response.getHeaders());
    } catch (IOException ex) {
      throw ex;
    }
  }

  private String buildEmailContent(String url, Account account) {
    return "Dear "
        + account.getUsername()
        + ",<br>"
        + "Please click the link below to verify your registration:<br>"
        + "<h2><a href=\"https://ink-paradise-api.com/api/v1/accounts/verify?code="
        + account.getVerificationCode()
        + "\" target=\"_self\">VERIFY</a></h2>"
        + "Thank you,<br>"
        + "ink-paradise.";
  }

  public boolean verify(String verificationCode) {
    Account account = accountRepository.findByVerificationCode(verificationCode);
    System.out.println(account);
    if (account == null) {
      return false;
    } else {
      account.setVerified(true);
      accountRepository.save(account);
      return true;
    }
  }

  public Account findAccountByEmail(String email) {
    return accountRepository.findByEmail(email);
  }
}
