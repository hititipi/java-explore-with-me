package ru.practicum.stats.client.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.stats.client.client.StatsClient;
import ru.practicum.stats.dto.model.EndpointHitDto;
import ru.practicum.stats.dto.utils.Messages;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.stats.dto.utils.Constants.DATE_FORMAT;

@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
public class StatsController {

    public final StatsClient statsClient;

    @PostMapping("/hit")
    public ResponseEntity<Object> postHit(@Valid @RequestBody EndpointHitDto endpointHit) {
        log.info(Messages.postHit(endpointHit.getUri()));
        return statsClient.postHit(endpointHit);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getStats(@RequestParam @DateTimeFormat(pattern = DATE_FORMAT) LocalDateTime start,
                                           @RequestParam @DateTimeFormat(pattern = DATE_FORMAT) LocalDateTime end,
                                           @RequestParam(required = false) List<String> uris,
                                           @RequestParam(defaultValue = "false") boolean unique) {
        log.info(Messages.getStats(start, end, uris, unique));
        return statsClient.getStats(start, end, uris, unique);
    }

}
