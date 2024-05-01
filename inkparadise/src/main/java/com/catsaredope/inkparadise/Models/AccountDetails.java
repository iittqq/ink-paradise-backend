package com.catsaredope.inkparadise.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "accountDetails", schema = "inkParadise")
public class AccountDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private long accountId;

  private String username;

  private String bio;

  private byte[] profilePicture;

  private byte[] headerPicture;

  private String birthday;

  public AccountDetails() {}

  public AccountDetails(
      long id,
      long accountId,
      String username,
      String bio,
      byte[] profilePicture,
      byte[] headerPicture,
      String birthday) {
    this.id = id;
    this.accountId = accountId;
    this.username = username;
    this.bio = bio;
    this.profilePicture = profilePicture;
    this.headerPicture = headerPicture;
    this.birthday = birthday;
  }

  public long getId() {
    return id;
  }

  public void setId(long newId) {
    this.id = newId;
  }

  @Column(name = "accountId", nullable = false)
  public long getAccountId() {
    return accountId;
  }

  public void setAccountId(long newAccountId) {
    this.accountId = newAccountId;
  }

  @Column(name = "username", nullable = false, length = 50)
  public String getUsername() {
    return username;
  }

  public void setUsername(String newUsername) {
    this.username = newUsername;
  }

  @Column(name = "bio", nullable = true, length = 150)
  public String getBio() {
    return bio;
  }

  public void setBio(String newBio) {
    this.bio = newBio;
  }

  @Lob
  @Column(name = "profilePicture", columnDefinition = "LONGBLOB")
  public byte[] getProfilePicture() {
    return profilePicture;
  }

  public void setProfilePicture(byte[] newProfilePicture) {
    this.profilePicture = newProfilePicture;
  }

  @Lob
  @Column(name = "headerPicture", columnDefinition = "LONGBLOB")
  public byte[] getHeaderPicture() {
    return headerPicture;
  }

  public void setHeaderPicture(byte[] newHeaderPicture) {
    this.headerPicture = newHeaderPicture;
  }

  @Column(name = "birthday", nullable = true, length = 50)
  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String newBirthday) {
    this.birthday = newBirthday;
  }
}
