package ru.practicum.stats.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.stats.dto.model.ViewStatsDto;
import ru.practicum.stats.server.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ViewStatsRepository extends JpaRepository<Stats, Long> {

    @Query("SELECT new ru.practicum.stats.dto.model.ViewStatsDto(stats.app, stats.uri, COUNT(stats.ip)) " +
            "FROM Stats AS stats " +
            "WHERE stats.timestamp BETWEEN :start AND :end " +
            "AND stats.uri IN :uris OR :uris IS NULL " +
            "GROUP BY stats.app, stats.uri " +
            "ORDER BY 3 DESC ")
    List<ViewStatsDto> findStats(LocalDateTime start, LocalDateTime end, List<String> uris);

   @Query("SELECT new ru.practicum.stats.dto.model.ViewStatsDto(stats.app, stats.uri, COUNT(distinct stats.ip)) " +
            "FROM Stats AS stats " +
            "WHERE stats.timestamp BETWEEN :start AND :end " +
            "AND stats.uri IN :uris OR :uris IS NULL " +
            "GROUP BY stats.app, stats.uri " +
            "ORDER BY 3 DESC ")
    List<ViewStatsDto> findUniqueStats(LocalDateTime start, LocalDateTime end, List<String> uris);

}
