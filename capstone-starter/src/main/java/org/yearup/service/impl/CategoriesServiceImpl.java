package org.yearup.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yearup.data.mysql.CategoryRepository;
import org.yearup.models.Category;
import org.yearup.service.CategoryService;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriesServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAllCategories() {
        return Collections.unmodifiableList(categoryRepository.findAll());
    }

    @Override
    public Category findById(int id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    @Override
    public Category createCategory(Category category) {
        if (category == null || category.getName() == null || category.getName().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
        return categoryRepository.save(category);
    }

}
