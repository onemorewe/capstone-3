package org.yearup.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.controllers.dto.ProductFilter;
import org.yearup.models.Product;
import org.yearup.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("products")
@CrossOrigin
@RequiredArgsConstructor
public class ProductsController {
    private final ProductService productService;

    @GetMapping
    @PreAuthorize("permitAll()")
    public List<Product> search(@RequestParam(name = "cat", required = false) Integer categoryId,
                                @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
                                @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
                                @RequestParam(name = "color", required = false) String color
    ) {
        ProductFilter productFilter = new ProductFilter(categoryId, minPrice, maxPrice, color);
        return productService.search(productFilter);
    }

    @GetMapping("{id}")
    @PreAuthorize("permitAll()")
    public Product getById(@PathVariable int id) {
        return productService.getById(id);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product) {
        return productService.create(product);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable("id") int productId, @RequestBody Product product) {
        if (product.getProductId() != null && product.getProductId() != productId)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product ID in path does not match ID in body.");
        productService.updateProduct(productId, product);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
    }
}
