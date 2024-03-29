package com.catsaredope.inkparadise.Repositories;

import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ReadingRepositoryImpl implements ReadingRepositoryCustom {
  @PersistenceContext private EntityManager entityManager;

  @Transactional
  @Override
  public void deleteByMangaIdAndUserId(String mangaId, Long userId) {
    entityManager
        .createNativeQuery("delete from reading where mangaId = :mangaId and userId = :userId")
        .setParameter("mangaId", mangaId)
        .setParameter("userId", userId)
        .executeUpdate();
  }
}
