package com.catsaredope.inkparadise.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "reading", schema = "inkParadise")
public class Reading {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private long userId;
  private String mangaId;
  private long chapter;
  private String mangaName;

  public Reading() {}

  public Reading(long id, long userId, String mangaId, long chapter, String mangaName) {
    this.id = id;
    this.userId = userId;
    this.mangaId = mangaId;
    this.chapter = chapter;
    this.mangaName = mangaName;
  }

  public long getId() {
    return id;
  }

  public void setId(long newId) {
    this.id = newId;
  }

  @Column(name = "userId", nullable = false, length = 50)
  public long getUserId() {
    return userId;
  }

  public void setUserId(long newUserId) {
    this.userId = newUserId;
  }

  @Column(name = "mangaId", nullable = false, length = 50)
  public String getMangaId() {
    return mangaId;
  }

  public void setMangaId(String newMangaId) {
    this.mangaId = newMangaId;
  }

  @Column(name = "chapter", nullable = false)
  public long getChapter() {
    return chapter;
  }

  public void setChapter(long newChapter) {
    this.chapter = newChapter;
  }

  @Column(name = "mangaName", nullable = false, length = 50)
  public String getMangaName() {
    return mangaName;
  }

  public void setMangaName(String newMangaName) {
    this.mangaName = newMangaName;
  }
}
