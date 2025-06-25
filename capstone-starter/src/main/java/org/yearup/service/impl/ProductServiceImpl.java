package org.yearup.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.controllers.dto.ProductFilter;
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

    @Override
    public List<Product> search(ProductFilter productFilter) {
        Specification<Product> productSpec = buildSpecification(productFilter);
        return Collections.unmodifiableList(productRepository.findAll(productSpec));
    }

    @Override
    public void updateProduct(int productId, Product product) {
        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException("Product with ID " + productId + " does not exist.");
        }
        product.setProductId(productId);
        productRepository.save(product);
    }

    @Override
    public Product getById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + " does not exist."));
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);

    }

    @Override
    public void deleteProduct(int id) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID " + id + " does not exist.");
        }
        productRepository.deleteById(id);
    }

    private Specification<Product> buildSpecification(ProductFilter productFilter) {
        Specification<Product> spec = Specification.where(null);

        if (productFilter.getCategoryId() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("categoryId"), productFilter.getCategoryId()));
        }

        if (productFilter.getMinPrice() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("price"), productFilter.getMinPrice()));
        }

        if (productFilter.getMaxPrice() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("price"), productFilter.getMaxPrice()));
        }

        if (productFilter.getColor() != null && !productFilter.getColor().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("color"), productFilter.getColor()));
        }

        return spec;
    }
}
