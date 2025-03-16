package cats_are_dope.ink_paradise_backend.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Entity
@Table(name = "userAccount", schema = "ink_paradise")
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "email", nullable = false, length = 50)
  private String email;

  @Column(name = "password", nullable = false, length = 100)
  private String password;

  @Column(name = "username", nullable = false, length = 20)
  private String username;

  @Column(name = "verificationCode", nullable = false, length = 64)
  private String verificationCode;

  @Column(name = "verified", nullable = false)
  private boolean verified;

  public Account(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public Account() {}

  public long getId() {
    return id;
  }

  public void setId(long newId) {
    this.id = newId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String newEmail) {
    this.email = newEmail;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String newPassword) {
    this.password = newPassword;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String newUserName) {
    this.username = newUserName;
  }

  public String getVerificationCode() {
    return verificationCode;
  }

  public void setVerificationCode(String newVerificationCode) {
    this.verificationCode = newVerificationCode;
  }

  public boolean getVerified() {
    return verified;
  }

  public void setVerified(boolean newVerified) {
    this.verified = newVerified;
  }
}
