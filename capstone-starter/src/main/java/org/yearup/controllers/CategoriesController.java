package org.yearup.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.service.CategoryService;
import org.yearup.service.ProductService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@CrossOrigin
public class CategoriesController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @GetMapping
    public List<Category> getAll() {
        return categoryService.findAllCategories();
    }


    @GetMapping("/{id}")
    public Category getById(@PathVariable int id) {
        return categoryService.findById(id);
    }

    @GetMapping("{categoryId}/products")
    public List<Product> getProductsById(@PathVariable int categoryId) {
        return productService.getProductsByCategoryId(categoryId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        Category newCategory = categoryService.createCategory(category);
        URI location = URI.create("/categories/" + newCategory.getCategoryId());
        return ResponseEntity
                .created(location)
                .body(newCategory);
    }

    @PutMapping("/{id}")
    public void updateCategory(@PathVariable int id, @RequestBody Category category) {
        categoryService.updateCategory(id, category);
    }


    // add annotation to call this method for a DELETE action - the url path must include the categoryId
    // add annotation to ensure that only an ADMIN can call this function
    public void deleteCategory(@PathVariable int id) {
        // delete the category by id
    }
}
