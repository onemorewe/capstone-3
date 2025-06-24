package org.yearup.controllers.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CartItemDto {

    private ProductDto product;
    private int quantity;
    private BigDecimal discountPercent;
    private BigDecimal lineTotal;
}