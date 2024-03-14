package com.catsaredope.inkparadise.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.catsaredope.inkparadise.Models.MangaFolderEntry;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface MangaFolderEntryRepository
		extends JpaRepository<MangaFolderEntry, Long>, MangaFolderEntryRepositoryCustom {
	@Query("select f from MangaFolderEntry f where f.folderId = :folderId")
	List<MangaFolderEntry> findByFolderId(@Param("folderId") Long folderId);

	void deleteByFolderId(Long folderId);

	void deleteByMangaIdAndFolderId(String mangaId, Long folderId);
	/**
	 * @Query("delete from MangaFolderEntry mfe where mfe.mangaId = ?1 and
	 * mfe.folderId = ?2")
	 * void deleteByMangaIdAndFolderId(String mangaId, Long folderId);
	 */
}
