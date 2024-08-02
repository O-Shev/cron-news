package com.leshkins.cronnews.server.repository;

import com.leshkins.cronnews.server.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    Optional<News> findByHeadline(String headline);

    @Query("SELECT n FROM News n WHERE n.publishedAt BETWEEN :start AND :end")
    List<News> findNewsByPublishedAtBetween(@Param("start") Timestamp start, @Param("end") Timestamp end);
}
