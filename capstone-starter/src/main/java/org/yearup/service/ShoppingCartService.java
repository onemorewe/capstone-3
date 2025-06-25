package org.yearup.service;

import org.yearup.controllers.dto.CartDto;
import org.yearup.controllers.dto.CartUpdateDto;
import org.yearup.models.ShoppingCartItem;

import java.security.Principal;
import java.util.List;

public interface ShoppingCartService {
    void addProductToCart(int productId, Principal principal);

    CartDto getCart(Principal principal);

    List<ShoppingCartItem> getShoppingCartItems(Principal principal);

    void updateProductInCart(int productId, CartUpdateDto cartUpdateDto, Principal principal);

    void emptyCart(Principal principal);
}
