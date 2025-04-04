package cats_are_dope.ink_paradise_backend.Models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reading", schema = "ink_paradise")
public class Reading {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "userId", nullable = false)
  private long userId;

  @Column(name = "mangaId", nullable = false, length = 50)
  private String mangaId;

  @Column(name = "chapter", nullable = false, precision = 15, scale = 2)
  private BigDecimal chapter;

  @Column(name = "mangaName", nullable = false)
  private String mangaName;

  @Column(name = "timestamp", nullable = false, length = 50)
  private String timestamp;

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

  public BigDecimal getChapter() {
    return chapter;
  }

  public void setChapter(BigDecimal newChapter) {
    this.chapter = newChapter;
  }

  public String getMangaName() {
    return mangaName;
  }

  public void setMangaName(String newMangaName) {
    this.mangaName = newMangaName;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String newTimestamp) {
    this.timestamp = newTimestamp;
  }
}
