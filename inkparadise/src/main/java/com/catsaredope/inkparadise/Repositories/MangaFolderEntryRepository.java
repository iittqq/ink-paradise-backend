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
	@Query("select f from MangaFolderEntry f where f.folder_id = ?1")
	List<MangaFolderEntry> findByFolderId(@Param("id") Long id);

}
