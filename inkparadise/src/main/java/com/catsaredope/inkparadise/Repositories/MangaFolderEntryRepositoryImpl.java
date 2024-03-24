package com.catsaredope.inkparadise.Repositories;

import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MangaFolderEntryRepositoryImpl implements MangaFolderEntryRepositoryCustom {

  @PersistenceContext private EntityManager entityManager;

  @Transactional
  @Override
  public void deleteByFolderId(Long folderId) {
    entityManager
        .createNativeQuery("delete from mangaFolderEntry where folderId = :folderId")
        .setParameter("folderId", folderId)
        .executeUpdate();
  }

  @Transactional
  @Override
  public void deleteByMangaIdAndFolderId(String mangaId, Long folderId) {
    entityManager
        .createNativeQuery(
            "delete from mangaFolderEntry where mangaId = :mangaId and folderId = :folderId")
        .setParameter("mangaId", mangaId)
        .setParameter("folderId", folderId)
        .executeUpdate();
  }
}
