package com.catsaredope.inkparadise.Controllers;

import com.catsaredope.inkparadise.Models.MangaFolder;
import com.catsaredope.inkparadise.Repositories.MangaFolderRepository;
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
public class MangaFolderController {
  @Autowired private MangaFolderRepository mangaFolderRepository;

  @GetMapping("/manga_folders")
  public List<MangaFolder> getAllMangaFolders() {
    return mangaFolderRepository.findAll();
  }

  @GetMapping("/manga_folders/{id}")
  public ResponseEntity<MangaFolder> getMangaFolderById(
      @PathVariable(value = "folderId") Long folderId) throws Exception {
    if (folderId == null) {
      throw new IllegalArgumentException("Manga folder id cannot be null");
    }
    MangaFolder mangaFolder =
        mangaFolderRepository
            .findById(folderId)
            .orElseThrow(() -> new Exception("Manga folder not found for this id :: " + folderId));
    return ResponseEntity.ok().body(mangaFolder);
  }

  @PostMapping("/manga_folders/new")
  public MangaFolder createMangaFolder(@Valid @RequestBody MangaFolder mangaFolder) {
    if (mangaFolder.getUserId() == 0 || mangaFolder.getFolderName() == null) {
      throw new IllegalArgumentException("Manga folder user id and folder name cannot be null");
    }
    return mangaFolderRepository.save(mangaFolder);
  }

  @PutMapping("/manga_folders/update/{folderId}")
  public ResponseEntity<MangaFolder> updateMangaFolder(
      @PathVariable(value = "folderId") Long folderId,
      @Valid @RequestBody MangaFolder mangaFolderDetails)
      throws Exception {
    if (folderId == null) {
      throw new IllegalArgumentException("Manga folder id cannot be null");
    }
    MangaFolder mangaFolder =
        mangaFolderRepository
            .findById(folderId)
            .orElseThrow(() -> new Exception("Manga folder not found for this id :: " + folderId));
    mangaFolder.setUserId(mangaFolderDetails.getUserId());
    mangaFolder.setFolderName(mangaFolderDetails.getFolderName());
    mangaFolder.setFolderDescription(mangaFolderDetails.getFolderDescription());

    final MangaFolder updatedMangaFolder = mangaFolderRepository.save(mangaFolder);
    return ResponseEntity.ok(updatedMangaFolder);
  }

  @DeleteMapping("/manga_folders/delete/{folderId}")
  public Map<String, Boolean> deleteMangaFolder(@PathVariable(value = "folderId") Long folderId)
      throws Exception {
    if (folderId == null) {
      throw new IllegalArgumentException("Manga folder id cannot be null");
    }
    MangaFolder mangaFolder =
        mangaFolderRepository
            .findById(folderId)
            .orElseThrow(() -> new Exception("Manga folder not found for this id :: " + folderId));

    if (mangaFolder == null) {
      throw new Exception("Manga folder not found for this id :: " + folderId);
    }
    mangaFolderRepository.delete(mangaFolder);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }
}
