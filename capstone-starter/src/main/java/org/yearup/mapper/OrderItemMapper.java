package org.yearup.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.yearup.models.Order;
import org.yearup.models.OrderLineItem;
import org.yearup.models.ShoppingCartItem;

import java.util.LinkedList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {


    LinkedList<OrderLineItem> toOrderLineItems(List<ShoppingCartItem> shoppingCartItems, @Context Order order);

    @Mapping(target = "discount", source = "shoppingCartItem.discountPercent")
    @Mapping(target = "salesPrice", source = "shoppingCartItem.product.price")
    @Mapping(target = "order", expression = "java(order)")
    @Mapping(target = "id", ignore = true)
    OrderLineItem shoppingCartItemToOrderLineItem(ShoppingCartItem shoppingCartItem, @Context Order order);
}
