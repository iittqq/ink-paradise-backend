package com.catsaredope.inkparadise.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "mangaFolderEntry", schema = "inkParadise")
public class MangaFolderEntry {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long uniqueId;

  private long folderId;
  private String mangaId;

  public MangaFolderEntry() {}

  public MangaFolderEntry(long uniqueId, long folderId, String mangaId) {
    this.uniqueId = uniqueId;
    this.folderId = folderId;
    this.mangaId = mangaId;
  }

  @Column(name = "uniqueId")
  public long getUniqueId() {
    return uniqueId;
  }

  @Column(name = "folderId")
  public long getFolderId() {
    return folderId;
  }

  public void setFolderId(long new_folder_id) {
    this.folderId = new_folder_id;
  }

  @Column(name = "mangaId", nullable = false)
  public String getMangaId() {
    return mangaId;
  }

  public void setMangaId(String newMangaId) {
    this.mangaId = newMangaId;
  }
}
