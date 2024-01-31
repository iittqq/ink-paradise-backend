package com.catsaredope.inkparadise.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.catsaredope.inkparadise.Models.MangaFolderEntry;
import org.springframework.stereotype.Repository;

@Repository
public interface MangaFolderEntryRepository extends JpaRepository<MangaFolderEntry, Long> {

}
