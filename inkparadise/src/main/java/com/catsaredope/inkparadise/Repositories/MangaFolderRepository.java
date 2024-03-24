package com.catsaredope.inkparadise.Repositories;

import com.catsaredope.inkparadise.Models.MangaFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MangaFolderRepository extends JpaRepository<MangaFolder, Long> {}
