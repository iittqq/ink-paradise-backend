package com.catsaredope.inkparadise.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.catsaredope.inkparadise.Models.MangaFolderEntry;
import com.catsaredope.inkparadise.Repositories.MangaFolderEntryRepository;
import java.util.List;

@Service
public class MangaFolderEntryService {
	@Autowired
	private MangaFolderEntryRepository mangaFolderEntryRepository;

	public List<MangaFolderEntry> findAccountByEmailAndPassword(Long id) {
		return mangaFolderEntryRepository.findByFolderId(id);
	}

	public void deleteByFolderId(Long folderId) {
		mangaFolderEntryRepository.deleteByFolderId(folderId);
	}
}
