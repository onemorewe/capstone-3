package org.yearup.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductFilter {
    private Integer categoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String color;
}
