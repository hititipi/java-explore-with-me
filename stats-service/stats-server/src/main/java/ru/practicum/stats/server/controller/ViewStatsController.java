package ru.practicum.stats.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.dto.model.EndpointHitDto;
import ru.practicum.stats.dto.model.ViewStatsDto;
import ru.practicum.stats.dto.utils.Constants;
import ru.practicum.stats.dto.utils.Messages;
import ru.practicum.stats.server.service.ViewStatsService;

import javax.validation.Valid;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ViewStatsController {

    private final ViewStatsService viewStatsService;

    @PostMapping(value = Constants.HIT_ENDPOINT, produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.CREATED)
    public void addHit(@Valid @RequestBody EndpointHitDto endpointHit) {
        log.info(Messages.postHit(endpointHit.getUri()));
        viewStatsService.addHit(endpointHit);
    }

    @GetMapping(Constants.STATS_ENDPOINT)
    public List<ViewStatsDto> getStats(@RequestParam String start,
                                       @RequestParam String end,
                                       @RequestParam(required = false, defaultValue = "false") Boolean unique,
                                       @RequestParam(required = false) List<String> uris) {
        log.info(Messages.getStats(start, end, uris, unique));
        LocalDateTime startTime = LocalDateTime.parse(URLDecoder.decode(start, Charset.defaultCharset()));
        LocalDateTime endTime = LocalDateTime.parse(URLDecoder.decode(end, Charset.defaultCharset()));
        return viewStatsService.getStats(startTime, endTime, uris, unique);
    }
}
