package com.catsaredope.inkparadise.Controllers;

import com.catsaredope.inkparadise.Models.Reading;
import com.catsaredope.inkparadise.Repositories.ReadingRepository;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ReadingController {
  @Autowired private ReadingRepository readingRepository;

  @GetMapping("/reading")
  public List<Reading> getAllReadings() {
    return readingRepository.findAll();
  }

  @GetMapping("/reading/{id}")
  public ResponseEntity<Reading> getReadingById(@PathVariable(value = "id") Long id)
      throws Exception {
    if (id == null) {
      throw new IllegalArgumentException("Reading id cannot be null");
    }
    Reading reading =
        readingRepository
            .findById(id)
            .orElseThrow(() -> new Exception("Reading not found for this id :: " + id));
    return ResponseEntity.ok().body(reading);
  }

  @GetMapping("/reading/find_by_user_id/{userId}")
  public List<Reading> getReadingByUserId(@PathVariable(value = "userId") Long userId) {
    if (userId == null) {
      throw new IllegalArgumentException("Reading id cannot be null");
    }
    return readingRepository.findByUserId(userId);
  }

  @GetMapping("/reading/find_by_user_id_and_manga_name/{userId}/{mangaName}")
  public List<Reading> getReadingByUserIdAndMangaName(
      @PathVariable(value = "userId") Long userId,
      @PathVariable(value = "mangaName") String mangaName) {
    if (userId == null || mangaName == null) {
      throw new IllegalArgumentException("Reading id and manga name cannot be null");
    }
    return readingRepository.findByUserIdAndMangaNameContaining(userId, mangaName);
  }

  @PostMapping("/reading/new")
  public Reading createReading(@Valid @RequestBody Reading reading) {
    if (reading.getMangaId() == null || reading.getChapter() == 0) {
      throw new IllegalArgumentException("Reading title and author cannot be null");
    }
    return readingRepository.save(reading);
  }

  @PutMapping("/reading/update/{id}")
  public ResponseEntity<Reading> updateReading(
      @PathVariable(value = "id") Long id, @Valid @RequestBody Reading readingDetails)
      throws Exception {
    Reading reading =
        readingRepository
            .findById(id)
            .orElseThrow(() -> new Exception("Reading not found for this id :: " + id));
    reading.setMangaId(readingDetails.getMangaId());
    reading.setChapter(readingDetails.getChapter());
    final Reading updatedReading = readingRepository.save(reading);
    return ResponseEntity.ok(updatedReading);
  }

  @DeleteMapping("/reading/delete/{id}")
  public Map<String, Boolean> deleteReading(@PathVariable(value = "id") Long id) throws Exception {
    Reading reading =
        readingRepository
            .findById(id)
            .orElseThrow(() -> new Exception("Reading not found for this id :: " + id));
    readingRepository.delete(reading);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }

  @DeleteMapping("/reading/delete_by_manga_id_and_user_id/{mangaId}/{userId}")
  public Map<String, Boolean> deleteReadingByMangaIdAndUserId(
      @PathVariable(value = "mangaId") String mangaId, @PathVariable(value = "userId") Long userId)
      throws Exception {
    if (mangaId == null || userId == null) {
      throw new IllegalArgumentException("Manga id and user id cannot be null");
    }
    readingRepository.deleteByMangaIdAndUserId(mangaId, userId);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }
}
