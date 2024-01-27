package com.catsaredope.inkparadise.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "account")
public class Account {
    private long id;
    private String email;
    private String password;
    private String userName;
    private int contentFilter;

    public Account() {
    }

    public Account(long id, String email, String password, String userName, int contentFilter) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.contentFilter = contentFilter;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "username", nullable = false, length = 50)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String newUserName) {
        this.userName = newUserName;
    }

    @Column(name = "contentfilter", nullable = false)
    public int getContentFilter() {
        return contentFilter;
    }

    public void setContentFilter(int newContentFilter) {
        this.contentFilter = newContentFilter;
    }
}
