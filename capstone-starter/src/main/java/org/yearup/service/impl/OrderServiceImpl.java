package org.yearup.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.OrderRepository;
import org.yearup.data.ProfileRepository;
import org.yearup.mapper.OrderItemMapper;
import org.yearup.mapper.OrderMapper;
import org.yearup.models.AppUser;
import org.yearup.models.Order;
import org.yearup.models.Profile;
import org.yearup.models.ShoppingCartItem;
import org.yearup.service.OrderService;
import org.yearup.service.PrincipalService;
import org.yearup.service.ShoppingCartService;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final PrincipalService principalService;
    private final ShoppingCartService shoppingCartService;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;
    private final ProfileRepository profileRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public void placeOrder(Principal principal) {
        List<ShoppingCartItem> shoppingCartItems = shoppingCartService.getShoppingCartItems(principal);
        if (shoppingCartItems.isEmpty()) {
            throw new ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_REQUEST, "Shopping cart is empty. Cannot place order.");
        }
        Order order = createOrder(principal, shoppingCartItems);
        orderRepository.save(order);
        shoppingCartService.emptyCart(principal);
    }

    private Order createOrder(Principal principal, List<ShoppingCartItem> shoppingCartItems) {
        Order order = new Order();

        AppUser user = principalService.getUserByPrincipal(principal);
        Profile profile = profileRepository.findByUser(user);
        if (profile == null || profile.getAddress().isEmpty() || profile.getCity().isEmpty() || profile.getState().isEmpty() || profile.getZip().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Profile address is incomplete. Please update your profile before placing an order.");
        }

        order.setItems(orderItemMapper.toOrderLineItems(shoppingCartItems, order));
        order.setUser(user);
        orderMapper.updateOrderAddressFromProfile(order, profile);

        return order;
    }
}
