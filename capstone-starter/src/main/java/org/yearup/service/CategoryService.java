package org.yearup.service;

import org.yearup.models.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategories();

    Category findById(int id);

    Category createCategory(Category category);

    void updateCategory(int id, Category category);

    void deleteCategory(int id);
}
