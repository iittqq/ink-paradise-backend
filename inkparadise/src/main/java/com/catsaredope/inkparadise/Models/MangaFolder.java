package com.catsaredope.inkparadise.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "manga_folder", schema = "ink_paradise")
public class MangaFolder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long folderId;
	private long userId;
	private String folderName;

	public MangaFolder() {
	}

	public MangaFolder(long folderId, long userId, String folderName) {
		this.folderId = folderId;
		this.userId = userId;
		this.folderName = folderName;
	}

	@Column(name = "folder_id")
	public long getFolderId() {
		return folderId;
	}

	public void setFolderId(long newFolderId) {
		this.folderId = newFolderId;
	}

	@Column(name = "user_id", nullable = false)
	public long getUserId() {
		return userId;
	}

	public void setUserId(long newUserId) {
		this.userId = newUserId;
	}

	@Column(name = "folder_name", nullable = false, length = 20)
	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String newFolderName) {
		this.folderName = newFolderName;
	}
}
