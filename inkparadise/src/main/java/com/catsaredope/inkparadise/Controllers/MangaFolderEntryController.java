package com.catsaredope.inkparadise.Controllers;

import com.catsaredope.inkparadise.Models.MangaFolderEntry;
import com.catsaredope.inkparadise.Repositories.MangaFolderEntryRepository;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MangaFolderEntryController {
  @Autowired private MangaFolderEntryRepository mangaFolderEntryRepository;

  @GetMapping("/manga_folder_entries")
  public List<MangaFolderEntry> getMangaFolderEntries() {
    return mangaFolderEntryRepository.findAll();
  }

  @GetMapping("/manga_folder_entries/{uniqueId}")
  public ResponseEntity<MangaFolderEntry> getMangaFolderEntryById(
      @PathVariable(value = "uniqueId") Long uniqueId) throws Exception {
    if (uniqueId == null) {
      throw new IllegalArgumentException("Manga folder entry id cannot be null");
    }
    MangaFolderEntry mangaFolderEntry =
        mangaFolderEntryRepository
            .findById(uniqueId)
            .orElseThrow(
                () -> new Exception("Manga folder entry not found for this id :: " + uniqueId));
    return ResponseEntity.ok().body(mangaFolderEntry);
  }

  @PostMapping("/manga_folder_entries/new")
  public MangaFolderEntry createMangaFolderEntry(
      @Valid @RequestBody MangaFolderEntry mangaFolderEntry) {
    if (mangaFolderEntry.getFolderId() == 0 || mangaFolderEntry.getMangaId() == null) {
      throw new IllegalArgumentException(
          "Manga folder entry folder id and manga id cannot be null");
    }
    return mangaFolderEntryRepository.save(mangaFolderEntry);
  }

  @PutMapping("/manga_folder_entries/update/{uniqueId}")
  public ResponseEntity<MangaFolderEntry> updateMangaFolderEntry(
      @PathVariable(value = "uniqueId") Long uniqueId,
      @Valid @RequestBody MangaFolderEntry mangaFolderEntryDetails)
      throws Exception {
    if (uniqueId == null) {
      throw new IllegalArgumentException("Manga folder entry id cannot be null");
    }
    MangaFolderEntry mangaFolderEntry =
        mangaFolderEntryRepository
            .findById(uniqueId)
            .orElseThrow(
                () -> new Exception("Manga folder entry not found for this id :: " + uniqueId));
    mangaFolderEntry.setFolderId(mangaFolderEntryDetails.getFolderId());
    mangaFolderEntry.setMangaId(mangaFolderEntryDetails.getMangaId());
    final MangaFolderEntry updatedMangaFolderEntry =
        mangaFolderEntryRepository.save(mangaFolderEntry);
    return ResponseEntity.ok(updatedMangaFolderEntry);
  }

  @DeleteMapping("/manga_folder_entries/delete/{uniqueId}")
  public Map<String, Boolean> deleteMangaFolderEntry(
      @PathVariable(value = "uniqueId") Long uniqueId) throws Exception {
    if (uniqueId == null) {
      throw new IllegalArgumentException("Manga folder entry id cannot be null");
    }
    MangaFolderEntry mangaFolderEntry =
        mangaFolderEntryRepository
            .findById(uniqueId)
            .orElseThrow(
                () -> new Exception("Manga folder entry not found for this id :: " + uniqueId));
    if (mangaFolderEntry == null) {
      throw new Exception("Manga folder entry not found for this id :: " + uniqueId);
    }
    mangaFolderEntryRepository.delete(mangaFolderEntry);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }

  @GetMapping("/manga_folder_entries/find_by_folder_id/{folderId}")
  public List<MangaFolderEntry> findMangaFolderEntryByFolderId(@PathVariable Long folderId) {
    if (folderId == null) {
      throw new IllegalArgumentException("Manga folder entry folder id cannot be null");
    }
    return mangaFolderEntryRepository.findByFolderId(folderId);
  }

  @DeleteMapping("/manga_folder_entries/delete_by_folder_id/{folderId}")
  public ResponseEntity<Map<String, Boolean>> deleteMangaFolderEntryByFolderId(
      @PathVariable Long folderId) {
    if (folderId == null) {
      throw new IllegalArgumentException("Manga folder entry folder id cannot be null");
    }
    try {
      mangaFolderEntryRepository.deleteByFolderId(folderId);
      return ResponseEntity.ok().body(null);
    } catch (Exception e) {
      throw new IllegalArgumentException(
          "Manga folder entry not found for this folder id :: " + folderId);
    }
  }

  @DeleteMapping("/manga_folder_entries/delete_by_manga_id_and_folder_id/{mangaId}/{folderId}")
  public ResponseEntity<Map<String, Boolean>> deleteMangaFolderEntryByMangaIdAndFolderId(
      @PathVariable String mangaId, @PathVariable Long folderId) {
    if (mangaId == null || folderId == null) {
      throw new IllegalArgumentException(
          "Manga folder entry manga id and folder id cannot be null");
    }
    try {
      mangaFolderEntryRepository.deleteByMangaIdAndFolderId(mangaId, folderId);
      return ResponseEntity.ok().body(null);
    } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
      /**
       * throw new IllegalArgumentException( "Manga folder entry not found for this manga id :: " +
       * mangaId + " and folder id :: " + folderId);
       */
    }
  }
}
