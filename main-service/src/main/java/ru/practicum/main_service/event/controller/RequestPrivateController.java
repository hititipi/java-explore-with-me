package ru.practicum.main_service.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.event.dto.ParticipationRequestDto;
import ru.practicum.main_service.event.service.RequestService;
import ru.practicum.main_service.utils.Messages;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
@Slf4j
public class RequestPrivateController {

    private final RequestService requestService;

    @GetMapping
    public List<ParticipationRequestDto> getRequests(@PathVariable Long userId) {
        log.info(Messages.getRequests(userId));
        return requestService.getRequestsByRequester(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createRequest(@PathVariable Long userId,
                                                 @RequestParam Long eventId) {
        log.info(Messages.addRequest(userId, eventId));
        return requestService.createRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable Long userId,
                                                 @PathVariable Long requestId) {
        log.info(Messages.cancelRequest(userId, requestId));
        return requestService.cancelRequest(userId, requestId);
    }
}