package org.yearup.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yearup.controllers.dto.CartDto;
import org.yearup.data.mysql.ProductRepository;
import org.yearup.data.mysql.ShoppingCartRepository;
import org.yearup.data.mysql.UserRepository;
import org.yearup.mapper.CartMapper;
import org.yearup.models.AppUser;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCartItem;
import org.yearup.service.ShoppingCartService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final UserRepository userRepository;
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
        AppUser user = getUser(principal);
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
        AppUser user = getUser(principal);
        List<ShoppingCartItem> allByUser = shoppingCartRepository.findAllByUser(user);
        BigDecimal total = calculateTotal(allByUser);
        return cartMapper.toDto(allByUser, total);
    }

    private void saveNewCartItem(AppUser user, Product product) {
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setUser(user);
        shoppingCartItem.setProduct(product);
        shoppingCartItem.setQuantity(1);
        shoppingCartRepository.save(shoppingCartItem);
    }

    private AppUser getUser(Principal principal) {
        String userName = principal.getName();
        return userRepository.getUserByUsername(userName);
    }
}
