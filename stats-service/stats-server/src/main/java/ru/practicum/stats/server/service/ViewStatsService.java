package ru.practicum.stats.server.service;

import ru.practicum.stats.dto.model.ViewStatsDto;
import ru.practicum.stats.dto.model.EndpointHitDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ViewStatsService {

    void addHit(EndpointHitDto endpointHit);

    List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

}
