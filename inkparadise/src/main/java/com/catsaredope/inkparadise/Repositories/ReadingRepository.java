package com.catsaredope.inkparadise.Repositories;

import com.catsaredope.inkparadise.Models.Reading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingRepository extends JpaRepository<Reading, Long> {}
