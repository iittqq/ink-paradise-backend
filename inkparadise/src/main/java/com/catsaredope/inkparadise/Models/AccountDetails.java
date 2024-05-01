package com.catsaredope.inkparadise.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accountDetails", schema = "inkParadise")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "accountId", nullable = false)
  private long accountId;

  @Column(name = "username", nullable = false, length = 50)
  private String username;

  @Column(name = "bio", nullable = true, length = 150)
  private String bio;

  @Lob
  @Column(name = "profilePicture", columnDefinition = "LONGBLOB", nullable = true)
  private byte[] profilePicture;

  @Lob
  @Column(name = "headerPicture", columnDefinition = "LONGBLOB", nullable = true)
  private byte[] headerPicture;

  @Column(name = "birthday", nullable = true, length = 50)
  private String birthday;

  @Column(name = "contentFilter", nullable = false)
  private long contentFilter;

  public long getId() {
    return id;
  }

  public void setId(long newId) {
    this.id = newId;
  }

  public long getAccountId() {
    return accountId;
  }

  public void setAccountId(long newAccountId) {
    this.accountId = newAccountId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String newUsername) {
    this.username = newUsername;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String newBio) {
    this.bio = newBio;
  }

  public byte[] getProfilePicture() {
    return profilePicture;
  }

  public void setProfilePicture(byte[] newProfilePicture) {
    this.profilePicture = newProfilePicture;
  }

  public byte[] getHeaderPicture() {
    return headerPicture;
  }

  public void setHeaderPicture(byte[] newHeaderPicture) {
    this.headerPicture = newHeaderPicture;
  }

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String newBirthday) {
    this.birthday = newBirthday;
  }

  public long getContentFilter() {
    return contentFilter;
  }

  public void setContentFilter(long newContentFilter) {
    this.contentFilter = newContentFilter;
  }
}
