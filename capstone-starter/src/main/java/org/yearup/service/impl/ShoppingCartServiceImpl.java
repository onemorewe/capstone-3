package org.yearup.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yearup.controllers.dto.CartDto;
import org.yearup.controllers.dto.CartUpdateDto;
import org.yearup.data.ProductRepository;
import org.yearup.data.ShoppingCartRepository;
import org.yearup.mapper.CartMapper;
import org.yearup.models.AppUser;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCartItem;
import org.yearup.service.PrincipalService;
import org.yearup.service.ShoppingCartService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final PrincipalService principalService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    private static BigDecimal calculateTotal(List<ShoppingCartItem> allByUser) {
        return allByUser.stream()
                .map(ShoppingCartItem::getProduct)
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void addProductToCart(int productId, Principal principal) {
        if (productId <= 0) {
            throw new IllegalArgumentException("Product ID must be greater than zero");
        }
        AppUser user = principalService.getUserByPrincipal(principal);
        Product product = productRepository.getReferenceById(productId);
        shoppingCartRepository.findByUserAndProduct(user, product).ifPresentOrElse(
                item -> {
                    item.setQuantity(item.getQuantity() + 1);
                    shoppingCartRepository.save(item);
                },
                () -> saveNewCartItem(user, product)
        );
    }

    @Override
    public CartDto getCart(Principal principal) {
        List<ShoppingCartItem> allByUser = getShoppingCartItems(principal);
        BigDecimal total = calculateTotal(allByUser);
        return cartMapper.toDto(allByUser, total);
    }

    @Override
    public List<ShoppingCartItem> getShoppingCartItems(Principal principal) {
        AppUser user = principalService.getUserByPrincipal(principal);
        return shoppingCartRepository.findAllByUser(user);
    }

    @Override
    public void updateProductInCart(int productId, CartUpdateDto cartUpdateDto, Principal principal) {
        ShoppingCartItem shoppingCartItem = getUserCartItemByProductId(productId, principal);

        if (cartUpdateDto.getQuantity() <= 0) {
            shoppingCartRepository.delete(shoppingCartItem);
        } else {
            shoppingCartItem.setQuantity(cartUpdateDto.getQuantity());
            shoppingCartRepository.save(shoppingCartItem);
        }
    }

    private ShoppingCartItem getUserCartItemByProductId(int productId, Principal principal) {
        AppUser user = principalService.getUserByPrincipal(principal);
        Product product = productRepository.getReferenceById(productId);
        return shoppingCartRepository.findByUserAndProduct(user, product)
                .orElseThrow(() -> new IllegalArgumentException("Product not found in cart"));
    }

    @Override
    public void emptyCart(Principal principal) {
        List<ShoppingCartItem> userCartItems = getShoppingCartItems(principal);
        if (userCartItems.isEmpty()) {
            return;
        }
        shoppingCartRepository.deleteAll(userCartItems);
    }

    private void saveNewCartItem(AppUser user, Product product) {
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setUser(user);
        shoppingCartItem.setProduct(product);
        shoppingCartItem.setQuantity(1);
        shoppingCartRepository.save(shoppingCartItem);
    }
}
