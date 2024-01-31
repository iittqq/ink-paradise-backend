package com.catsaredope.inkparadise.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "manga_folder_entry", schema = "ink_paradise")
public class MangaFolderEntry {
    private long folderId;
    private long mangaId;

    public MangaFolderEntry() {
    }

    public MangaFolderEntry(long folderId, long mangaId) {
        this.folderId = folderId;
        this.mangaId = mangaId;
    }

    @Id
    @Column(name = "folder_id")
    public long getFolderId() {
        return folderId;
    }

    public void setFolderId(long newFolderId) {
        this.folderId = newFolderId;
    }

    @Column(name = "manga_id", nullable = false)
    public long getMangaId() {
        return mangaId;
    }

    public void setMangaId(long newMangaId) {
        this.mangaId = newMangaId;
    }
}
