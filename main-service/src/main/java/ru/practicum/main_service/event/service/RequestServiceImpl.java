package ru.practicum.main_service.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main_service.event.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main_service.event.dto.EventRequestStatusUpdateResult;
import ru.practicum.main_service.event.dto.ParticipationRequestDto;
import ru.practicum.main_service.event.mapper.RequestMapper;
import ru.practicum.main_service.event.model.*;
import ru.practicum.main_service.event.repository.RequestRepository;
import ru.practicum.main_service.user.model.User;
import ru.practicum.main_service.user.service.UserService;
import ru.practicum.main_service.validation.ConflitException;
import ru.practicum.main_service.validation.ValidationException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.main_service.validation.ErrorMessages.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RequestServiceImpl implements RequestService {
    private final UserService userService;
    private final EventService eventService;
    private final StatsService statsService;
    private final RequestRepository requestRepository;

    @Override
    public List<ParticipationRequestDto> getRequestsByRequester(Long userId) {
        userService.getUser(userId);
        return toParticipationRequestsDto(requestRepository.findAllByRequesterId(userId));
    }

    @Override
    @Transactional
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        User user = userService.getUser(userId);
        Event event = eventService.getEventById(eventId);
        checkEvent(event, userId);
        Request newRequest = Request.builder().event(event).requester(user).created(LocalDateTime.now()).build();
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            newRequest.setStatus(RequestStatus.CONFIRMED);
        } else {
            newRequest.setStatus(RequestStatus.PENDING);
        }
        return RequestMapper.toParticipationRequestDto(requestRepository.save(newRequest));
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        userService.getUser(userId);
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ValidationException(HttpStatus.NOT_FOUND, RESOURCE_NOT_FOUND));
        checkUserIsOwner(request.getRequester().getId(), userId);
        request.setStatus(RequestStatus.CANCELED);
        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> getRequestsByEventOwner(Long userId, Long eventId) {
        userService.getUser(userId);
        Event event = eventService.getEventById(eventId);
        checkUserIsOwner(event.getInitiator().getId(), userId);
        return toParticipationRequestsDto(requestRepository.findAllByEventId(eventId));
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult patchRequestsByEventOwner(
            Long userId, Long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        userService.getUser(userId);
        Event event = eventService.getEventById(eventId);
        List<Long> requestIds = eventRequestStatusUpdateRequest.getRequestIds();

        checkUserIsOwner(event.getInitiator().getId(), userId);
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0 || requestIds.isEmpty()) {
            return new EventRequestStatusUpdateResult(List.of(), List.of());
        }

        List<Request> requests = requestRepository.findAllByIdIn(requestIds);
        checkRequests(requests, requestIds);

        List<Request> confirmedList = new ArrayList<>();
        List<Request> rejectedList = new ArrayList<>();

        if (eventRequestStatusUpdateRequest.getStatus().equals(RequestStatusAction.REJECTED)) {
            rejectedList.addAll(changeStatusAndSave(requests, RequestStatus.REJECTED));
        } else {
            Long newConfirmedRequests = statsService.getConfirmedRequests(List.of(event)).getOrDefault(eventId, 0L) +
                    requestIds.size();
            checkEventLimit(newConfirmedRequests, event.getParticipantLimit());
            confirmedList.addAll(changeStatusAndSave(requests, RequestStatus.CONFIRMED));
            if (newConfirmedRequests >= event.getParticipantLimit()) {
                rejectedList.addAll(changeStatusAndSave(
                        requestRepository.findAllByEventIdAndStatus(eventId, RequestStatus.PENDING), RequestStatus.REJECTED)
                );
            }
        }
        return new EventRequestStatusUpdateResult(toParticipationRequestsDto(confirmedList), toParticipationRequestsDto(rejectedList));
    }

    private void checkRequests(List<Request> requests, List<Long> requestIds) {
        if (requests.size() != requestIds.size()) {
            throw new ValidationException(HttpStatus.NOT_FOUND, RESOURCE_NOT_FOUND);
        }
        if (!requests.stream().map(Request::getStatus).allMatch(RequestStatus.PENDING::equals)) {
            throw new ConflitException(REQUEST_IS_NOT_WAITING);
        }
    }

    private void checkEvent(Event event, Long userId) {
        if (Objects.equals(event.getInitiator().getId(), userId)) {
            throw new ConflitException(REQUEST_SELF);
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflitException(REQUEST_NOT_PUBLISHED_EVENT);
        }
        Optional<Request> oldRequest = requestRepository.findByEventIdAndRequesterId(event.getId(), userId);
        if (oldRequest.isPresent()) {
            throw new ConflitException(REPEATED_REQUEST);
        }
        checkEventLimit(statsService.getConfirmedRequests(List.of(event)).getOrDefault(event.getId(), 0L) + 1,
                event.getParticipantLimit()
        );
    }

    private List<ParticipationRequestDto> toParticipationRequestsDto(List<Request> requests) {
        return requests.stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    private List<Request> changeStatusAndSave(List<Request> requests, RequestStatus status) {
        requests.forEach(request -> request.setStatus(status));
        return requestRepository.saveAll(requests);
    }

    private void checkEventLimit(Long newLimit, Integer eventLimit) {
        if (eventLimit != 0 && (newLimit > eventLimit)) {
            throw new ConflitException(EXCEEDED_LIMIT);
        }
    }

    private void checkUserIsOwner(Long id, Long userId) {
        if (!Objects.equals(id, userId)) {
            throw new ConflitException(NOT_OWNER);
        }
    }
}
