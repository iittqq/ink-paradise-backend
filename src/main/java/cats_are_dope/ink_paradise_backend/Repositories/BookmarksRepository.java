package cats_are_dope.ink_paradise_backend.Repositories;

import cats_are_dope.ink_paradise_backend.Models.Bookmarks;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarksRepository extends JpaRepository<Bookmarks, Long> {
  List<Bookmarks> findByUserId(Long userId);

  List<Bookmarks> findByUserIdAndMangaName(Long userId, String mangaName);

  void deleteByMangaIdAndUserId(String mangaId, Long userId);
}
