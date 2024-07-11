package cats_are_dope.ink_paradise_backend.Repositories;

public interface BookmarksRepositoryCustom {
  void deleteByMangaIdAndUserId(String mangaId, Long userId);
}
