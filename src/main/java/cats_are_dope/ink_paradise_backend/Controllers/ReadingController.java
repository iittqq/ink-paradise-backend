package cats_are_dope.ink_paradise_backend.Controllers;

import cats_are_dope.ink_paradise_backend.Models.Reading;
import cats_are_dope.ink_paradise_backend.Repositories.ReadingRepository;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

  @PostMapping("/reading/update_or_create")
  public ResponseEntity<String> updateOrCreateReading(@RequestBody Reading reading) {
    Optional<Reading> existingReading =
        readingRepository.findByUserIdAndMangaId(reading.getUserId(), reading.getMangaId());

    if (existingReading.isPresent()) {
      Reading existing = existingReading.get();
      existing.setChapter(reading.getChapter());
      existing.setTimestamp(reading.getTimestamp());
      readingRepository.save(existing);
      return ResponseEntity.status(HttpStatus.OK).body("Reading updated successfully");
    } else {
      readingRepository.save(reading);
      return ResponseEntity.status(HttpStatus.CREATED).body("Reading created successfully");
    }
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

  @GetMapping("/reading/find_by_user_id_and_manga_id/{userId}/{mangaId}")
  public ResponseEntity<Reading> getReadingByUserIdAndMangaId(
      @PathVariable(value = "userId") Long userId,
      @PathVariable(value = "mangaId") String mangaId) {
    if (userId == null || mangaId == null) {
      throw new IllegalArgumentException("Reading id and manga id cannot be null");
    }
    List<Reading> readings = readingRepository.findByUserIdAndMangaIdContaining(userId, mangaId);

    if (readings.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    return ResponseEntity.ok(readings.get(0));
  }

  @PostMapping("/reading/new")
  public Reading createReading(@Valid @RequestBody Reading reading) {
    if (reading.getMangaId() == null) {
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
    reading.setTimestamp(readingDetails.getTimestamp());
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
