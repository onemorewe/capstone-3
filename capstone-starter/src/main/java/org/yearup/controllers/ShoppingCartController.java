package org.yearup.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.mysql.ProductRepository;
import org.yearup.data.mysql.ShoppingCartRepository;
import org.yearup.data.mysql.UserRepository;
import org.yearup.models.AppUser;
import org.yearup.models.ShoppingCartItem;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
@PreAuthorize("hasRole('ROLE_USER')")
@CrossOrigin
@RequiredArgsConstructor
public class ShoppingCartController {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;

    // each method in this controller requires a Principal object as a parameter


    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be added

    @PostMapping("/products/{productId}")
    public void addProductToCart(@PathVariable int productId, Principal principal) {
        AppUser user = getUser(principal);
        shoppingCartRepository.findByUser_IdAndProduct_ProductId(user.getId(), productId).ifPresentOrElse(
                item -> {
                    item.setQuantity(item.getQuantity() + 1);
                    shoppingCartRepository.save(item);
                },
                () -> saveNewCartItem(user, productId)
        );
    }

    private void saveNewCartItem(AppUser user, int productId) {
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setUser(user);
        shoppingCartItem.setProduct(productRepository.getReferenceById(productId));
        shoppingCartItem.setQuantity(1);
        shoppingCartRepository.save(shoppingCartItem);
    }

    private AppUser getUser(Principal principal) {
        String userName = principal.getName();
        return userRepository.getUserByUsername(userName);
    }

    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15 (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated


    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart

}
