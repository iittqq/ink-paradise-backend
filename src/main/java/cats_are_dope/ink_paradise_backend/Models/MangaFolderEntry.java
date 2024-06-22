package cats_are_dope.ink_paradise_backend.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "mangaFolderEntry", schema = "ink_paradise")
public class MangaFolderEntry {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long uniqueId;

  @Column(name = "folderId")
  private long folderId;

  @Column(name = "mangaId", nullable = false)
  private String mangaId;

  public MangaFolderEntry() {}

  public MangaFolderEntry(long uniqueId, long folderId, String mangaId) {
    this.uniqueId = uniqueId;
    this.folderId = folderId;
    this.mangaId = mangaId;
  }

  public long getUniqueId() {
    return uniqueId;
  }

  public long getFolderId() {
    return folderId;
  }

  public void setFolderId(long new_folder_id) {
    this.folderId = new_folder_id;
  }

  public String getMangaId() {
    return mangaId;
  }

  public void setMangaId(String newMangaId) {
    this.mangaId = newMangaId;
  }
}
