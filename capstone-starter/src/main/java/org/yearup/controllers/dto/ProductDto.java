package org.yearup.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductDto {
    private Integer productId;
    private String name;
    private BigDecimal price;
    private int categoryId;
    private String description;
    private String color;
    private int stock;
    @JsonProperty("featured")
    private boolean isFeatured;
    private String imageUrl;
}