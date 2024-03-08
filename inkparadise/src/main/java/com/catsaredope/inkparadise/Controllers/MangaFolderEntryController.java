package com.catsaredope.inkparadise.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.catsaredope.inkparadise.Repositories.MangaFolderEntryRepository;
import com.catsaredope.inkparadise.Models.MangaFolderEntry;

@RestController
@RequestMapping("/api/v1")
public class MangaFolderEntryController {
	@Autowired
	private MangaFolderEntryRepository mangaFolderEntryRepository;

	@GetMapping("/manga_folder_entries")
	public List<MangaFolderEntry> getMangaFolderEntries() {
		return mangaFolderEntryRepository.findAll();
	}

	@GetMapping("/manga_folder_entries/{id}")
	public ResponseEntity<MangaFolderEntry> getMangaFolderEntryById(@PathVariable(value = "id") Long mangaFolderEntryId)
			throws Exception {
		if (mangaFolderEntryId == null) {
			throw new IllegalArgumentException("Manga folder entry id cannot be null");
		}
		MangaFolderEntry mangaFolderEntry = mangaFolderEntryRepository.findById(mangaFolderEntryId)
				.orElseThrow(() -> new Exception("Manga folder entry not found for this id :: " + mangaFolderEntryId));
		return ResponseEntity.ok().body(mangaFolderEntry);
	}

	@PostMapping("/manga_folder_entries/new")
	public MangaFolderEntry createMangaFolderEntry(@Valid @RequestBody MangaFolderEntry mangaFolderEntry) {
		if (mangaFolderEntry.getFolderId() == 0 || mangaFolderEntry.getMangaId() == null) {
			throw new IllegalArgumentException("Manga folder entry folder id and manga id cannot be null");
		}
		return mangaFolderEntryRepository.save(mangaFolderEntry);
	}

	@PutMapping("/manga_folder_entries/update/{id}")
	public ResponseEntity<MangaFolderEntry> updateMangaFolderEntry(@PathVariable(value = "id") Long mangaFolderId,
			@Valid @RequestBody MangaFolderEntry mangaFolderEntryDetails) throws Exception {
		if (mangaFolderId == null) {
			throw new IllegalArgumentException("Manga folder entry id cannot be null");
		}
		MangaFolderEntry mangaFolderEntry = mangaFolderEntryRepository.findById(mangaFolderId)
				.orElseThrow(() -> new Exception("Manga folder entry not found for this id :: " + mangaFolderId));
		mangaFolderEntry.setFolderId(mangaFolderEntryDetails.getFolderId());
		mangaFolderEntry.setMangaId(mangaFolderEntryDetails.getMangaId());
		final MangaFolderEntry updatedMangaFolderEntry = mangaFolderEntryRepository.save(mangaFolderEntry);
		return ResponseEntity.ok(updatedMangaFolderEntry);
	}

	@DeleteMapping("/manga_folder_entries/delete/{id}")
	public Map<String, Boolean> deleteMangaFolderEntry(@PathVariable(value = "id") Long mangaFolderEntryId)
			throws Exception {
		if (mangaFolderEntryId == null) {
			throw new IllegalArgumentException("Manga folder entry id cannot be null");
		}
		MangaFolderEntry mangaFolderEntry = mangaFolderEntryRepository.findById(mangaFolderEntryId)
				.orElseThrow(() -> new Exception("Manga folder entry not found for this id :: " + mangaFolderEntryId));
		if (mangaFolderEntry == null) {
			throw new Exception("Manga folder entry not found for this id :: " + mangaFolderEntryId);
		}
		mangaFolderEntryRepository.delete(mangaFolderEntry);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
