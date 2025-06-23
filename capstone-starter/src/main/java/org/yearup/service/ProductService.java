package org.yearup.service;

import org.yearup.models.Product;

import java.util.List;


public interface ProductService {
    List<Product> getProductsByCategoryId(int categoryId);
}
