package org.yearup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.yearup.controllers.dto.CartDto;
import org.yearup.controllers.dto.CartItemDto;
import org.yearup.models.ShoppingCartItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class, ProductMapper.class})
public interface CartMapper {

    @Mapping(target = "items", expression = "java(toItemMap(items))")
    CartDto toDto(List<ShoppingCartItem> items, BigDecimal total);

    default Map<Integer, CartItemDto> toItemMap(List<ShoppingCartItem> items) {
        return items.stream()
                .collect(Collectors.toMap(
                        line -> line.getProduct().getProductId(),
                        this::toDto));
    }

    @Mapping(target = "lineTotal", expression = "java(calculateLineTotal(line))")
    CartItemDto toDto(ShoppingCartItem line);

    default BigDecimal calculateLineTotal(ShoppingCartItem line) {
        BigDecimal price = line.getProduct().getPrice();
        BigDecimal quantity = new BigDecimal(line.getQuantity());
        BigDecimal discount = line.getDiscountPercent().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        return price.multiply(quantity).multiply(BigDecimal.ONE.subtract(discount));
    }
}
