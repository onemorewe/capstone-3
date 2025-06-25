package org.yearup.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.controllers.dto.CartDto;
import org.yearup.controllers.dto.CartUpdateDto;
import org.yearup.service.ShoppingCartService;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
@PreAuthorize("hasRole('ROLE_USER')")
@CrossOrigin
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @PostMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addProductToCart(@PathVariable int productId, Principal principal) {
        shoppingCartService.addProductToCart(productId, principal);
    }

    @GetMapping
    public CartDto getCart(Principal principal) {
        return shoppingCartService.getCart(principal);
    }

    @PatchMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProductInCart(@PathVariable int productId, @RequestBody CartUpdateDto cartUpdateDto, Principal principal) {
        shoppingCartService.updateProductInCart(productId, cartUpdateDto, principal);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void emptyCart(Principal principal) {
        shoppingCartService.emptyCart(principal);
    }
}
