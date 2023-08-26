package ru.practicum.stats.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.stats.dto.model.ViewStatsDto;
import ru.practicum.stats.server.model.StatsMapper;
import ru.practicum.stats.server.repository.ViewStatsRepository;
import ru.practicum.stats.dto.model.EndpointHitDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ViewStatsServerImpl implements  ViewStatsService {

    private final ViewStatsRepository statsRepository;

    @Override
    @Transactional
    public void addHit(EndpointHitDto endpointHit) {
        statsRepository.save(StatsMapper.toViewStats(endpointHit));
    }

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (unique) {
            return statsRepository.findUniqueStats(start, end, uris);
        } else {
            return statsRepository.findStats(start, end, uris);
        }
    }
}
