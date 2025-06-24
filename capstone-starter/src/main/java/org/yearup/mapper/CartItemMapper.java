package org.yearup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.yearup.controllers.dto.CartItemDto;
import org.yearup.models.ShoppingCartItem;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface CartItemMapper {


    @Mapping(target = "lineTotal", expression = "java(calculateLineTotal(entity))")
    CartItemDto toDto(ShoppingCartItem entity);

    default BigDecimal calculateLineTotal(ShoppingCartItem entity) {
        BigDecimal price = entity.getProduct().getPrice();
        BigDecimal quantity = new BigDecimal(entity.getQuantity());
        BigDecimal discount = entity.getDiscountPercent().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        return price.multiply(quantity).multiply(BigDecimal.ONE.subtract(discount));
    }
}
