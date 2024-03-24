package com.catsaredope.inkparadise.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "userAccount", schema = "inkParadise")
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String email;
  private String password;
  private String username;
  private int contentFilter;

  public Account() {}

  public Account(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public Account(long id, String email, String password, String username, int contentFilter) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.username = username;
    this.contentFilter = contentFilter;
  }

  public long getId() {
    return id;
  }

  public void setId(long newId) {
    this.id = newId;
  }

  @Column(name = "email", nullable = false, length = 50)
  public String getEmail() {
    return email;
  }

  public void setEmail(String newEmail) {
    this.email = newEmail;
  }

  @Column(name = "password", nullable = false, length = 50)
  public String getPassword() {
    return password;
  }

  public void setPassword(String newPassword) {
    this.password = newPassword;
  }

  @Column(name = "username", nullable = true, length = 50)
  public String getUsername() {
    return username;
  }

  public void setUsername(String newUserName) {
    this.username = newUserName;
  }

  @Column(name = "contentFilter", nullable = false)
  public int getContentFilter() {
    return contentFilter;
  }

  public void setContentFilter(int newContentFilter) {
    this.contentFilter = newContentFilter;
  }
}
