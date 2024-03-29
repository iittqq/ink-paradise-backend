package com.catsaredope.inkparadise.Repositories;

import com.catsaredope.inkparadise.Models.Reading;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingRepository extends JpaRepository<Reading, Long>, ReadingRepositoryCustom {
  List<Reading> findByUserId(Long userId);

  List<Reading> findByUserIdAndMangaNameContaining(Long userId, String mangaName);

  void deleteByMangaIdAndUserId(String mangaId, Long userId);
}
