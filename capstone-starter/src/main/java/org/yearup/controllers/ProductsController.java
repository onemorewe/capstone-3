package org.yearup.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.controllers.dto.ProductFilter;
import org.yearup.data.ProductDao;
import org.yearup.models.Product;
import org.yearup.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("products")
@CrossOrigin
@RequiredArgsConstructor
public class ProductsController {
    private final ProductDao productDao;
    private final ProductService productService;

    @GetMapping("")
    @PreAuthorize("permitAll()")
    public List<Product> search(@RequestParam(name = "cat", required = false) Integer categoryId,
                                @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
                                @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
                                @RequestParam(name = "color", required = false) String color
    ) {
        try {
            ProductFilter productFilter = new ProductFilter(categoryId, minPrice, maxPrice, color);
            return productService.search(productFilter);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    @GetMapping("{id}")
    @PreAuthorize("permitAll()")
    public Product getById(@PathVariable int id) {
        try {
            var product = productDao.getById(id);

            if (product == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            return product;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Product addProduct(@RequestBody Product product) {
        try {
            return productDao.create(product);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateProduct(@RequestBody Product product) {
        try {
            productDao.create(product);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteProduct(@PathVariable int id) {
        try {
            var product = productDao.getById(id);

            if (product == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            productDao.delete(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
}
