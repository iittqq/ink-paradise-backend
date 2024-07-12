package cats_are_dope.ink_paradise_backend.Controllers;

import cats_are_dope.ink_paradise_backend.Models.Bookmarks;
import cats_are_dope.ink_paradise_backend.Repositories.BookmarksRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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
  public Bookmarks createBookmark(@RequestBody Bookmarks bookmark) {
    if (bookmark.getMangaId() == null) {
      throw new IllegalArgumentException("Bookmark title and author cannot be null");
    }
    return bookmarksRepository.save(bookmark);
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
      @RequestParam(value = "mangaId") String mangaId,
      @RequestParam(value = "userId") Long userId) {
    bookmarksRepository.deleteByMangaIdAndUserId(mangaId, userId);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }
}
