package com.catsaredope.inkparadise.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "manga_folder_entry", schema = "ink_paradise")
public class MangaFolderEntry {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long unique_id;
	private long folder_id;
	private String manga_id;

	public MangaFolderEntry() {
	}

	public MangaFolderEntry(long unique_id, long folder_id, String manga_id) {
		this.unique_id = unique_id;
		this.folder_id = folder_id;
		this.manga_id = manga_id;
	}

	@Column(name = "unique_id")
	public long getUniqueId() {
		return unique_id;
	}

	@Column(name = "folder_id")
	public long getFolderId() {
		return folder_id;
	}

	public void setFolderId(long newFolderId) {
		this.folder_id = newFolderId;
	}

	@Column(name = "manga_id", nullable = false)
	public String getMangaId() {
		return manga_id;
	}

	public void setMangaId(String newMangaId) {
		this.manga_id = newMangaId;
	}
}
