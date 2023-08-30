package ru.practicum.main_service.event.service;


import org.springframework.data.domain.Pageable;
import ru.practicum.main_service.event.dto.*;
import ru.practicum.main_service.event.model.Event;
import ru.practicum.main_service.event.model.EventSortType;
import ru.practicum.main_service.event.model.EventState;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    EventFullDto getEventByPublic(Long id, HttpServletRequest request);

    List<EventShortDto> getEventsByPublic(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                          LocalDateTime rangeEnd, Boolean onlyAvailable, EventSortType sort,
                                          Integer from, Integer size, HttpServletRequest request);

    List<EventFullDto> getEventsByAdmin(List<Long> users, List<EventState> states, List<Long> categories,
                                        LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    EventFullDto patchEventByAdmin(Long eventId, UpdateEventAdminRequest updateEventAdminRequest);

    EventFullDto getEventByPrivate(Long userId, Long eventId);

    List<EventShortDto> getAllEventsByPrivate(Long userId, Pageable pageable);

    EventFullDto createEventByPrivate(Long userId, NewEventDto newEventDto);

    EventFullDto patchEventByPrivate(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest);

    Event getEventById(Long eventId);

    List<Event> getEventsByIds(List<Long> eventsId);

    List<EventShortDto> toEventsShortDto(List<Event> events);
}