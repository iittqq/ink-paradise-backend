package com.catsaredope.inkparadise.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "mangaFolder", schema = "inkParadise")
public class MangaFolder {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long folderId;

  private long userId;
  private String folderName;
  private String folderDescription;

  public MangaFolder() {}

  public MangaFolder(long folderId, long userId, String folderName) {
    this.folderId = folderId;
    this.userId = userId;
    this.folderName = folderName;
  }

  @Column(name = "folderId")
  public long getFolderId() {
    return folderId;
  }

  public void setFolderId(long newFolderId) {
    this.folderId = newFolderId;
  }

  @Column(name = "userId", nullable = false)
  public long getUserId() {
    return userId;
  }

  public void setUserId(long newUserId) {
    this.userId = newUserId;
  }

  @Column(name = "folderName", nullable = false, length = 20)
  public String getFolderName() {
    return folderName;
  }

  public void setFolderName(String newFolderName) {
    this.folderName = newFolderName;
  }

  @Column(name = "folderDescription", length = 100)
  public String getFolderDescription() {
    return folderDescription;
  }

  public void setFolderDescription(String newFolderDescription) {
    this.folderDescription = newFolderDescription;
  }
}
