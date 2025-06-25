package org.yearup.controllers.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
public class CartDto {
    private Map<Integer, CartItemDto> items;
    private BigDecimal total;
}
