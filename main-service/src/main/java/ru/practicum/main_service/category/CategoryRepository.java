package ru.practicum.main_service.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.main_service.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);

    @Query("FROM Category AS C " +
            "WHERE LOWER(C.name) LIKE LOWER(:name) " +
            "AND C.id NOT IN (:excludeCatId)")
    Category findByName(String name, Long excludeCatId);

}