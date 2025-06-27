package org.yearup.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.yearup.controllers.dto.ProductFilter;
import org.yearup.models.Product;
import org.yearup.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void searchByCategoryShouldReturnCorrectProducts() {
        // Arrange
        ProductFilter filter = new ProductFilter(1, null, null, null);

        // Act
        List<Product> products = productService.search(filter);

        // Assert
        assertEquals(22, products.size(), "Should find 22 all products in category 1.");
        for (Product product : products) {
            assertEquals(1, product.getCategoryId(), "All returned products should be in category 1.");
        }
    }

    @Test
    public void searchByMinPriceShouldReturnCorrectProducts() {
        // Arrange
        ProductFilter filter = new ProductFilter(null, new BigDecimal("1000.00"), null, null);

        // Act
        List<Product> products = productService.search(filter);

        // Assert: There is 1 product (Smart TV) with a price of $1499.99
        assertEquals(1, products.size(), "Should find one product with price >= 1000.");
        assertTrue(products.get(0).getPrice().compareTo(new BigDecimal("1000.00")) >= 0, "Product price should be >= 1000.");
    }


    @Test
    public void searchWithMultipleFiltersShouldReturnCorrectProducts() {
        // Arrange
        ProductFilter filter = new ProductFilter(1, new BigDecimal("100.00"), new BigDecimal("500.00"), "Black");

        // Act
        List<Product> products = productService.search(filter);

        // Assert: There are 3 products matching all criteria
        assertEquals(3, products.size(), "Should find products matching all criteria.");
        for (Product product : products) {
            assertEquals(1, product.getCategoryId(), "Product should be in category 1.");
            assertTrue(product.getPrice().compareTo(new BigDecimal("100.00")) >= 0, "Price should be >= 100.");
            assertTrue(product.getPrice().compareTo(new BigDecimal("500.00")) <= 0, "Price should be <= 500.");
            assertEquals("Black", product.getColor(), "Product color should be Black.");
        }
    }

    @Test
    public void searchWithNoFiltersShouldReturnAllProducts() {
        // Arrange
        ProductFilter filter = new ProductFilter(null, null, null, null);

        // Act
        List<Product> products = productService.search(filter);

        // Assert: There are 90 products in total in the test data
        assertEquals(90, products.size(), "Should return all products when no filter is applied.");
    }

    @Test
    public void updateProductShouldUpdateTheProduct() {
        // Arrange
        int productId = 1;
        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Smartphone");
        updatedProduct.setPrice(new BigDecimal("599.99"));

        // Act
        productService.updateProduct(productId, updatedProduct);

        // Assert
        Product retrievedProduct = productService.getById(productId);
        assertEquals("Updated Smartphone", retrievedProduct.getName());
        assertEquals(0, new BigDecimal("599.99").compareTo(retrievedProduct.getPrice()));
    }

    @Test
    public void updateProductWithNonExistingProductShouldThrowException() {
        // Arrange
        int nonExistingProductId = -1;
        Product productToUpdate = new Product();

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> productService.updateProduct(nonExistingProductId, productToUpdate));
    }
}
