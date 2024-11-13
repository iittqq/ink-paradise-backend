package cats_are_dope.ink_paradise_backend.Repositories;

import cats_are_dope.ink_paradise_backend.Models.Bookmarks;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarksRepository extends JpaRepository<Bookmarks, Long> {
  List<Bookmarks> findByUserId(Long userId);

  List<Bookmarks> findByUserIdAndMangaName(Long userId, String mangaName);

  Optional<Bookmarks> findByMangaIdAndChapterNumberAndPageNumber(
      String mangaId, BigDecimal chapterNumber, long pageNumber);

  void deleteByMangaIdAndUserId(String mangaId, Long userId);

  Optional<Bookmarks> findByUserIdAndMangaIdAndContinueReading(
      Long userId, String mangaId, boolean continueReading);

  Optional<Bookmarks> findByUserIdAndMangaIdAndChapterNumberAndPageNumber(
      Long userId, String mangaId, BigDecimal chapterNumber, Long pageNumber);
}
