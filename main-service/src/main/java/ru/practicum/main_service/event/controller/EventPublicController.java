package ru.practicum.main_service.event.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.event.dto.EventFullDto;
import ru.practicum.main_service.event.dto.EventShortDto;
import ru.practicum.main_service.event.model.EventSortType;
import ru.practicum.main_service.event.service.EventService;
import ru.practicum.main_service.utils.Messages;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.main_service.utils.Constants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@Validated
@Slf4j

public class EventPublicController {
    private final EventService eventService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getEvent(@PathVariable Long id,
                                         HttpServletRequest request) {
        log.info(Messages.getPublicEvent(id));
        return eventService.getEventByPublic(id, request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getEvents(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) @DateTimeFormat(pattern = DATE_FORMAT) LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = DATE_FORMAT) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false) EventSortType sort,
            @RequestParam(defaultValue = DEFAULT_PAGE_FROM) @PositiveOrZero Integer from,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Positive Integer size,
            HttpServletRequest request) {
        log.info(Messages.getPublicEvents());
        return eventService.getEventsByPublic(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                sort, from, size, request);
    }

}