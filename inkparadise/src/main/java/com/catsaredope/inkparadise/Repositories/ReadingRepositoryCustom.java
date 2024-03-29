package com.catsaredope.inkparadise.Repositories;

public interface ReadingRepositoryCustom {
  void deleteByMangaIdAndUserId(String mangaId, Long userId);
}
