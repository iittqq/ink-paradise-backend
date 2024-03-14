package com.catsaredope.inkparadise.Repositories;

import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MangaFolderEntryRepositoryImpl implements MangaFolderEntryRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Override
	public void deleteByFolderId(Long folderId) {
		entityManager.createNativeQuery("delete from manga_folder_entry where folder_id = :folderId")
				.setParameter("folderId", folderId)
				.executeUpdate();
	}

}
