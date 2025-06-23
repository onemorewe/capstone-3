package org.yearup.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yearup.data.ProductRepository;
import org.yearup.models.Product;
import org.yearup.service.ProductService;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getProductsByCategoryId(int categoryId) {
        return Collections.unmodifiableList(productRepository.findAllByCategoryId(categoryId));
    }
}
