package com.catsaredope.inkparadise.Repositories;

import com.catsaredope.inkparadise.Models.MangaFolderEntry;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MangaFolderEntryRepository
    extends JpaRepository<MangaFolderEntry, Long>, MangaFolderEntryRepositoryCustom {
  List<MangaFolderEntry> findByFolderId(Long folderId);

  void deleteByFolderId(Long folderId);

  void deleteByMangaIdAndFolderId(String mangaId, Long folderId);
}
