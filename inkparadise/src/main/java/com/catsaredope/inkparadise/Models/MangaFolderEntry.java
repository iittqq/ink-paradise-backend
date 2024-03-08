package com.catsaredope.inkparadise.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "manga_folder_entry", schema = "ink_paradise")
public class MangaFolderEntry {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long uniqueId;
	private long folderId;
	private String mangaId;

	public MangaFolderEntry() {
	}

	public MangaFolderEntry(long unique_id, long folderId, String mangaId) {
		this.uniqueId = unique_id;
		this.folderId = folderId;
		this.mangaId = mangaId;
	}

	@Column(name = "unique_id")
	public long getUniqueId() {
		return uniqueId;
	}

	@Column(name = "folder_id")
	public long getFolderId() {
		return folderId;
	}

	public void setFolderId(long newFolderId) {
		this.folderId = newFolderId;
	}

	@Column(name = "manga_id", nullable = false)
	public String getMangaId() {
		return mangaId;
	}

	public void setMangaId(String newMangaId) {
		this.mangaId = newMangaId;
	}
}
