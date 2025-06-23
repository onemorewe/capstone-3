package org.yearup.service;

import org.yearup.controllers.dto.ProductFilter;
import org.yearup.models.Product;

import java.util.List;


public interface ProductService {
    List<Product> getProductsByCategoryId(int categoryId);

    List<Product> search(ProductFilter productFilter);

    Product updateProduct(int productId, Product product);
}
