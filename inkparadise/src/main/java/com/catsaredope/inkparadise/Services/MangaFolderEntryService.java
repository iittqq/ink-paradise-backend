package com.catsaredope.inkparadise.Services;

import com.catsaredope.inkparadise.Models.MangaFolderEntry;
import com.catsaredope.inkparadise.Repositories.MangaFolderEntryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MangaFolderEntryService {
  @Autowired private MangaFolderEntryRepository mangaFolderEntryRepository;

  public List<MangaFolderEntry> findFolderEntriesbyFolderId(Long folderId) {
    return mangaFolderEntryRepository.findByFolderId(folderId);
  }

  public void deleteByFolderId(Long folderId) {
    mangaFolderEntryRepository.deleteByFolderId(folderId);
  }

  public void deleteMangaFolderEntryByMangaIdAndFolderId(String mangaId, Long folderId) {
    mangaFolderEntryRepository.deleteByMangaIdAndFolderId(mangaId, folderId);
  }
}
