package cats_are_dope.ink_paradise_backend.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "mangaFolder", schema = "ink_paradise")
public class MangaFolder {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long folderId;

  @Column(name = "userId", nullable = false)
  private long userId;

  @Column(name = "folderName", nullable = false, length = 50)
  private String folderName;

  @Column(name = "folderDescription", nullable = true, length = 100)
  private String folderDescription;

  @Column(name = "folderCover", nullable = true, length = 1000)
  private String folderCover;

  public MangaFolder() {}

  public MangaFolder(long folderId, long userId, String folderName) {
    this.folderId = folderId;
    this.userId = userId;
    this.folderName = folderName;
  }

  public long getFolderId() {
    return folderId;
  }

  public void setFolderId(long newFolderId) {
    this.folderId = newFolderId;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long newUserId) {
    this.userId = newUserId;
  }

  public String getFolderName() {
    return folderName;
  }

  public void setFolderName(String newFolderName) {
    this.folderName = newFolderName;
  }

  public String getFolderDescription() {
    return folderDescription;
  }

  public void setFolderDescription(String newFolderDescription) {
    this.folderDescription = newFolderDescription;
  }

  public String getFolderCover() {
    return folderCover;
  }

  public void setFolderCover(String newFolderCover) {
    this.folderCover = newFolderCover;
  }
}
