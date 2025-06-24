package org.yearup.service;

import org.yearup.controllers.dto.CartDto;

import java.security.Principal;

public interface ShoppingCartService {
    void addProductToCart(int productId, Principal principal);

    CartDto getCart(Principal principal);
}
