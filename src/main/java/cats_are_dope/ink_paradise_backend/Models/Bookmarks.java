package cats_are_dope.ink_paradise_backend.Models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookmarks", schema = "ink_paradise")
public class Bookmarks {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "userId", nullable = false)
  private long userId;

  @Column(name = "mangaId", nullable = false)
  private String mangaId;

  @Column(name = "mangaName", nullable = false)
  private String mangaName;

  @Column(name = "chapterNumber", nullable = false)
  private BigDecimal chapterNumber;

  @Column(name = "chapterId", nullable = false)
  private String chapterId;

  @Column(name = "chapterIndex", nullable = false)
  private long chapterIndex;

  @Column(name = "pageNumber", nullable = true)
  private long pageNumber;

  @Column(name = "continueReading", nullable = true)
  private boolean continueReading;

  public long getId() {
    return id;
  }

  public void setId(long newId) {
    this.id = newId;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long newUserId) {
    this.userId = newUserId;
  }

  public String getMangaId() {
    return mangaId;
  }

  public void setMangaId(String newMangaId) {
    this.mangaId = newMangaId;
  }

  public String getMangaName() {
    return mangaName;
  }

  public void setMangaName(String newMangaName) {
    this.mangaName = newMangaName;
  }

  public BigDecimal getChapterNumber() {
    return chapterNumber;
  }

  public void setChapterNumber(BigDecimal newChapterNumber) {
    this.chapterNumber = newChapterNumber;
  }

  public String getChapterId() {
    return chapterId;
  }

  public void setChapterId(String newChapterId) {
    this.chapterId = newChapterId;
  }

  public long getChapterIndex() {
    return chapterIndex;
  }

  public void setChapterIndex(long newChapterIndex) {
    this.chapterIndex = newChapterIndex;
  }

  public long getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(long newPageNumber) {
    this.pageNumber = newPageNumber;
  }

  public boolean getContinueReading() {
    return continueReading;
  }

  public void setContinueReading(boolean newContinueReading) {
    this.continueReading = newContinueReading;
  }
}
