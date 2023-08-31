package ru.practicum.main_service.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main_service.compilation.dto.CompilationDto;
import ru.practicum.main_service.compilation.dto.NewCompilationDto;
import ru.practicum.main_service.compilation.dto.UpdateCompilationRequest;
import ru.practicum.main_service.compilation.model.Compilation;
import ru.practicum.main_service.compilation.model.CompilationMapper;
import ru.practicum.main_service.compilation.repository.CompilationRepository;
import ru.practicum.main_service.event.dto.EventShortDto;
import ru.practicum.main_service.event.model.Event;
import ru.practicum.main_service.event.service.EventService;
import ru.practicum.main_service.validation.ValidationException;

import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.main_service.validation.ErrorMessages.RESOURCE_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CompilationServiceImpl implements CompilationService {

    private final EventService eventService;
    private final CompilationRepository compilationRepository;

    @Override
    public CompilationDto get(Long compId) {
        Compilation compilation = getCompilationById(compId);
        List<EventShortDto> eventsShortDto = eventService.toEventsShortDto(compilation.getEvents());
        return CompilationMapper.toCompilationDto(compilation, eventsShortDto);
    }

    @Override
    public List<CompilationDto> getAll(Boolean pinned, Pageable pageable) {
        List<Compilation> compilations = findCompilations(pinned, pageable);
        Map<Long, EventShortDto> eventsShortDto = getEventsShortDto(compilations);
        return compilations.stream()
                .map(compilation -> {
                    List<EventShortDto> compEventsShortDto = new ArrayList<>();
                    compilation.getEvents().forEach(event -> compEventsShortDto.add(eventsShortDto.get(event.getId())));
                    return CompilationMapper.toCompilationDto(compilation, compEventsShortDto);
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CompilationDto create(NewCompilationDto newCompilationDto) {
        List<Event> events = getEventsFromNewCompilationDto(newCompilationDto);
        Compilation compilation = compilationRepository.save(CompilationMapper.newDtoToCompilation(newCompilationDto, events));
        return get(compilation.getId());
    }

    @Override
    @Transactional
    public CompilationDto patch(Long compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = getCompilationById(compId);
        if (updateCompilationRequest.getTitle() != null) {
            compilation.setTitle(updateCompilationRequest.getTitle());
        }
        if (updateCompilationRequest.getPinned() != null) {
            compilation.setPinned(updateCompilationRequest.getPinned());
        }
        if (updateCompilationRequest.getEvents() != null) {
            List<Event> events = eventService.getEventsByIds(updateCompilationRequest.getEvents());
            checkSize(events, updateCompilationRequest.getEvents());
            compilation.setEvents(events);
        }
        compilationRepository.save(compilation);
        return get(compId);
    }

    @Override
    @Transactional
    public void delete(Long compId) {
        getCompilationById(compId);
        compilationRepository.deleteById(compId);
    }

    private List<Compilation> findCompilations(Boolean pinned, Pageable pageable) {
        if (pinned == null) {
            return compilationRepository.findAll(pageable).toList();
        } else {
            return compilationRepository.findAllByPinned(pinned, pageable);
        }
    }

    private Map<Long, EventShortDto> getEventsShortDto(List<Compilation> compilations) {
        Set<Event> uniqueEvents = new HashSet<>();
        compilations.forEach(compilation -> uniqueEvents.addAll(compilation.getEvents()));
        Map<Long, EventShortDto> eventsShortDto = new HashMap<>();
        eventService.toEventsShortDto(new ArrayList<>(uniqueEvents)).forEach(event -> eventsShortDto.put(event.getId(), event));
        return eventsShortDto;
    }

    private List<Event> getEventsFromNewCompilationDto(NewCompilationDto newCompilationDto) {
        if (!newCompilationDto.getEvents().isEmpty()) {
            List<Event> events = eventService.getEventsByIds(newCompilationDto.getEvents());
            checkSize(events, newCompilationDto.getEvents());
            return events;
        }
        return Collections.EMPTY_LIST;
    }

    private Compilation getCompilationById(Long compId) {
        return compilationRepository.findById(compId)
                .orElseThrow(() -> new ValidationException(HttpStatus.NOT_FOUND, RESOURCE_NOT_FOUND));
    }

    private void checkSize(List<Event> events, List<Long> eventsIdToUpdate) {
        if (events.size() != eventsIdToUpdate.size()) {
            throw new ValidationException(HttpStatus.NOT_FOUND, RESOURCE_NOT_FOUND);
        }
    }
}
