package com.catsaredope.inkparadise.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "reading", schema = "inkParadise")
public class Reading {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String mangaId;
  private long chapter;

  public Reading() {}

  public Reading(long id, String mangaId, long chapter) {
    this.id = id;
    this.mangaId = mangaId;
    this.chapter = chapter;
  }

  public long getId() {
    return id;
  }

  public void setId(long newId) {
    this.id = newId;
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
}
