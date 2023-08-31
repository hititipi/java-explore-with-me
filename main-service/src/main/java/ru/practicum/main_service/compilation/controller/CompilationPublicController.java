package ru.practicum.main_service.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.compilation.dto.CompilationDto;
import ru.practicum.main_service.compilation.service.CompilationService;
import ru.practicum.main_service.utils.Messages;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.main_service.utils.Constants.DEFAULT_PAGE_FROM;
import static ru.practicum.main_service.utils.Constants.DEFAULT_PAGE_SIZE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
@Validated
@Slf4j
public class CompilationPublicController {

    private final CompilationService compilationService;

    @GetMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto get(@PathVariable Long compId) {
        log.info(Messages.getCompilation(compId));
        return compilationService.get(compId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CompilationDto> getAll(
            @RequestParam(required = false) Boolean pinned,
            @RequestParam(defaultValue = DEFAULT_PAGE_FROM) @PositiveOrZero Integer from,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Positive Integer size) {
        log.info(Messages.getAllCompilations());
        return compilationService.getAll(pinned, PageRequest.of(from / size, size));
    }

}
