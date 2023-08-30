package ru.practicum.main_service.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main_service.category.dto.CategoryDto;
import ru.practicum.main_service.category.dto.NewCategoryDto;
import ru.practicum.main_service.category.model.Category;
import ru.practicum.main_service.category.model.CategoryMapper;
import ru.practicum.main_service.category.CategoryRepository;
import ru.practicum.main_service.event.repository.EventRepository;
import ru.practicum.main_service.validation.ConflitException;
import ru.practicum.main_service.validation.ErrorMessages;
import ru.practicum.main_service.validation.ValidationException;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.main_service.validation.ErrorMessages.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public Category getCategory(Long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new ValidationException(HttpStatus.NOT_FOUND, RESOURCE_NOT_FOUND));
    }

    @Override
    public List<CategoryDto> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        if (categoryRepository.findByName(newCategoryDto.getName()) != null) {
            throw new ConflitException(CATEGORY_NAME_ALREADY_EXISTS);
        }
        return CategoryMapper.toCategoryDto(categoryRepository.save(CategoryMapper.newCategoryDtoToCategory(newCategoryDto)));
    }

    @Override
    @Transactional
    public CategoryDto patchCategory(Long catId, CategoryDto categoryDto) {
        getCategory(catId);
        if (categoryRepository.findByName(categoryDto.getName(), catId) != null) {
            throw new ConflitException(CATEGORY_NAME_ALREADY_EXISTS);
        }
        categoryDto.setId(catId);
        return CategoryMapper.toCategoryDto(categoryRepository.save(CategoryMapper.categoryDtoToCategory(categoryDto)));
    }

    @Override
    @Transactional
    public void deleteCategory(Long catId) {
        getCategory(catId);
        if (!eventRepository.findAllByCategoryId(catId).isEmpty()) {
            throw new ConflitException(CATEGORY_HAS_EVENTS);
        }
        categoryRepository.deleteById(catId);
    }

}
