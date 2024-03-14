package com.catsaredope.inkparadise.Repositories;

public interface MangaFolderEntryRepositoryCustom {
	void deleteByFolderId(Long folderId);

	void deleteByMangaIdAndFolderId(String mangaId, Long folderId);

}
