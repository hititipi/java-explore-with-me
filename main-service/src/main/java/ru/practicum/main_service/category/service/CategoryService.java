package ru.practicum.main_service.category.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.main_service.category.dto.CategoryDto;
import ru.practicum.main_service.category.dto.NewCategoryDto;
import ru.practicum.main_service.category.model.Category;

import java.util.List;

public interface CategoryService {

    Category getCategory(Long catId);

    List<CategoryDto> getAll(Pageable pageable);

    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    CategoryDto patchCategory(Long catId, CategoryDto categoryDto);

    void deleteCategory(Long catId);


}
