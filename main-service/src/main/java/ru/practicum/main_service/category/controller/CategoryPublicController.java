package ru.practicum.main_service.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.category.dto.CategoryDto;
import ru.practicum.main_service.category.model.CategoryMapper;
import ru.practicum.main_service.category.service.CategoryService;
import ru.practicum.main_service.utils.Messages;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.main_service.utils.Constants.DEFAULT_PAGE_FROM;
import static ru.practicum.main_service.utils.Constants.DEFAULT_PAGE_SIZE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@Validated
@Slf4j
public class CategoryPublicController {

    private final CategoryService categoryService;

    @GetMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getById(@PathVariable Long catId) {
        log.info(Messages.getCategory(catId));
        return CategoryMapper.toCategoryDto(categoryService.getCategory(catId));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getAll(
            @RequestParam(defaultValue = DEFAULT_PAGE_FROM) @PositiveOrZero Integer from,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Positive Integer size) {
        log.info(Messages.getAllCategories());
        return categoryService.getAll(PageRequest.of(from / size, size));
    }

}
