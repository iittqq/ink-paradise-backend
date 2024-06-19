package cats_are_dope.ink_paradise_backend.Repositories;

public interface MangaFolderEntryRepositoryCustom {
  void deleteByFolderId(Long folderId);

  void deleteByMangaIdAndFolderId(String mangaId, Long folderId);
}
