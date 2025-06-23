package org.yearup.service;

import org.yearup.controllers.dto.ProductFilter;
import org.yearup.models.Product;

import java.util.List;


public interface ProductService {
    List<Product> getProductsByCategoryId(int categoryId);

    List<Product> search(ProductFilter productFilter);

    void updateProduct(int productId, Product product);

    Product getById(int id);

    Product create(Product product);

    void deleteProduct(int id);
}
