package com.catsaredope.inkparadise.Repositories;

import com.catsaredope.inkparadise.Models.Reading;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingRepository extends JpaRepository<Reading, Long> {
  @Query("select r from Reading r where r.userId = :userId")
  List<Reading> findByUserId(@Param("userId") Long userId);
}
