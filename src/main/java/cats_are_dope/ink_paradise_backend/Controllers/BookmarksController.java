package cats_are_dope.ink_paradise_backend.Controllers;

import cats_are_dope.ink_paradise_backend.Models.Bookmarks;
import cats_are_dope.ink_paradise_backend.Repositories.BookmarksRepository;
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
public class BookmarksController {
  @Autowired private BookmarksRepository bookmarksRepository;

  @GetMapping("/bookmarks")
  public List<Bookmarks> getAllBookmarks() {
    return bookmarksRepository.findAll();
  }

  @GetMapping("/bookmarks/{id}")
  public ResponseEntity<Bookmarks> getBookmarkById(@PathVariable(value = "id") Long id)
      throws Exception {
    if (id == null) {
      throw new IllegalArgumentException("Bookmark id cannot be null");
    }
    Bookmarks bookmark =
        bookmarksRepository
            .findById(id)
            .orElseThrow(() -> new Exception("Bookmark not found for this id :: " + id));
    return ResponseEntity.ok().body(bookmark);
  }

  @GetMapping("/bookmarks/find_by_user_id/{userId}")
  public List<Bookmarks> getBookmarkByUserId(@PathVariable(value = "userId") Long userId) {
    if (userId == null) {
      throw new IllegalArgumentException("Bookmark id cannot be null");
    }
    return bookmarksRepository.findByUserId(userId);
  }

  @GetMapping("/bookmarks/find_by_user_id_and_manga_name/{userId}/{mangaName}")
  public List<Bookmarks> getBookmarkByUserIdAndMangaName(
      @PathVariable(value = "userId") Long userId,
      @PathVariable(value = "mangaName") String mangaName) {
    if (userId == null || mangaName == null) {
      throw new IllegalArgumentException("Bookmark id and manga name cannot be null");
    }
    return bookmarksRepository.findByUserIdAndMangaName(userId, mangaName);
  }

  @PostMapping("/bookmarks/new")
  public ResponseEntity<String> createBookmark(@RequestBody Bookmarks bookmark) {
    if (bookmark.getMangaId() == null
        || bookmark.getChapterNumber() == null
        || bookmark.getPageNumber() == 0) {
      throw new IllegalArgumentException("Bookmark details cannot be null");
    }

    // Check if a duplicate bookmark exists
    Optional<Bookmarks> existingBookmark =
        bookmarksRepository.findByMangaIdAndChapterNumberAndPageNumber(
            bookmark.getMangaId(), bookmark.getChapterNumber(), bookmark.getPageNumber());

    if (existingBookmark.isPresent()) {
      // Return a response indicating the bookmark already exists
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Bookmark already exists");
    }

    // Save the new bookmark
    bookmarksRepository.save(bookmark);
    return ResponseEntity.status(HttpStatus.CREATED).body("Bookmark created successfully");
  }

  @PostMapping("/bookmarks/update_or_create")
  public ResponseEntity<String> updateOrCreateBookmark(@RequestBody Bookmarks bookmark) {
    if (bookmark.getContinueReading()) {
      // Handle "Continue Reading" bookmark
      Optional<Bookmarks> continueReadingBookmark =
          bookmarksRepository.findByUserIdAndMangaIdAndContinueReading(
              bookmark.getUserId(), bookmark.getMangaId(), true);

      if (continueReadingBookmark.isPresent()) {
        // Update existing "Continue Reading" bookmark
        Bookmarks existing = continueReadingBookmark.get();
        existing.setChapterNumber(bookmark.getChapterNumber());
        existing.setChapterIndex(bookmark.getChapterIndex());
        existing.setPageNumber(bookmark.getPageNumber());
        existing.setChapterId(bookmark.getChapterId());
        bookmarksRepository.save(existing);
        return ResponseEntity.status(HttpStatus.OK)
            .body("Continue reading bookmark updated successfully");
      } else {
        // Create new "Continue Reading" bookmark
        bookmarksRepository.save(bookmark);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Continue reading bookmark created successfully");
      }
    } else {
      // Handle custom bookmark
      Optional<Bookmarks> customBookmark =
          bookmarksRepository.findByUserIdAndMangaIdAndChapterNumberAndPageNumber(
              bookmark.getUserId(),
              bookmark.getMangaId(),
              bookmark.getChapterNumber(),
              bookmark.getPageNumber());

      if (customBookmark.isPresent()) {
        // Custom bookmark already exists
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Custom bookmark already exists");
      } else {
        // Create new custom bookmark
        bookmarksRepository.save(bookmark);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Custom bookmark created successfully");
      }
    }
  }

  @PutMapping("/bookmarks/update/{id}")
  public ResponseEntity<Bookmarks> updateBookmark(
      @PathVariable(value = "id") Long id, @RequestBody Bookmarks bookmarkDetails) {
    Bookmarks bookmark =
        bookmarksRepository
            .findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException("Bookmark not found for this id :: " + id));
    bookmark.setUserId(bookmarkDetails.getUserId());
    bookmark.setMangaId(bookmarkDetails.getMangaId());
    bookmark.setChapterId(bookmarkDetails.getChapterId());
    bookmark.setMangaName(bookmarkDetails.getMangaName());
    bookmark.setChapterIndex(bookmarkDetails.getChapterIndex());
    bookmark.setChapterNumber(bookmarkDetails.getChapterNumber());

    if (bookmarkDetails.getContinueReading() != true) {
      bookmark.setPageNumber(bookmarkDetails.getPageNumber());
    }

    final Bookmarks updatedBookmark = bookmarksRepository.save(bookmark);
    return ResponseEntity.ok(updatedBookmark);
  }

  @DeleteMapping("/bookmarks/delete/{id}")
  public Map<String, Boolean> deleteBookmark(@PathVariable(value = "id") Long id) {
    Bookmarks bookmark =
        bookmarksRepository
            .findById(id)
            .orElseThrow(
                () -> new IllegalArgumentException("Bookmark not found for this id :: " + id));
    bookmarksRepository.delete(bookmark);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }

  @DeleteMapping("/bookmarks/delete_by_manga_id_and_user_id/{mangaId}/{userId}")
  public Map<String, Boolean> deleteBookmarkByMangaIdAndUserId(
      @PathVariable(value = "mangaId") String mangaId,
      @PathVariable(value = "userId") Long userId) {
    bookmarksRepository.deleteByMangaIdAndUserId(mangaId, userId);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }
}
