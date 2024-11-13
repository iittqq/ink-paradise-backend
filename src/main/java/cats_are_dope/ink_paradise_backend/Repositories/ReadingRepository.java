package cats_are_dope.ink_paradise_backend.Repositories;

import cats_are_dope.ink_paradise_backend.Models.Reading;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingRepository extends JpaRepository<Reading, Long>, ReadingRepositoryCustom {
  List<Reading> findByUserId(Long userId);

  List<Reading> findByUserIdAndMangaNameContaining(Long userId, String mangaName);

  void deleteByMangaIdAndUserId(String mangaId, Long userId);

  Optional<Reading> findByUserIdAndMangaId(Long userId, String mangaId);
}
