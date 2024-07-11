package cats_are_dope.ink_paradise_backend.Repositories;

import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class BookmarksRepositoryImpl implements BookmarksRepositoryCustom {
  @PersistenceContext private EntityManager entityManager;

  @Transactional
  @Override
  public void deleteByMangaIdAndUserId(String mangaId, Long userId) {
    entityManager
        .createNativeQuery("delete from bookmarks where mangaId = :mangaId and userId = :userId")
        .setParameter("mangaId", mangaId)
        .setParameter("userId", userId)
        .executeUpdate();
  }
}
