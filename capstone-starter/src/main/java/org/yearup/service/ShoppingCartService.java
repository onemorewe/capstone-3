package org.yearup.service;

import org.yearup.controllers.dto.CartDto;
import org.yearup.controllers.dto.CartUpdateDto;

import java.security.Principal;

public interface ShoppingCartService {
    void addProductToCart(int productId, Principal principal);

    CartDto getCart(Principal principal);

    void updateProductInCart(int productId, CartUpdateDto cartUpdateDto, Principal principal);

    void emptyCart(Principal principal);
}
