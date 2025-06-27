package org.yearup.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.mapper.OrderItemMapper;
import org.yearup.mapper.OrderMapper;
import org.yearup.models.AppUser;
import org.yearup.models.Profile;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.OrderRepository;
import org.yearup.repository.ProfileRepository;
import org.yearup.service.PrincipalService;
import org.yearup.service.ShoppingCartService;

import java.security.Principal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test for {@link org.yearup.service.impl.OrderServiceImpl}  class.
 **/

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("unused")
class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private PrincipalService principalService;
    @Mock
    private ShoppingCartService shoppingCartService;
    @Mock
    private OrderItemMapper orderItemMapper;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private OrderRepository orderRepository;

    /**
     * Test for {@link OrderServiceImpl#placeOrder(Principal)}  method.
     **/
    @Test
    void shouldThrowExceptionWhenShoppingCartIsEmpty() {
        // Arrange
        Principal principal = () -> "testUser";
        when(shoppingCartService.getShoppingCartItems(principal)).then(invocation -> List.of());

        // Act
        Exception exception = assertThrows(ResponseStatusException.class,
                () -> orderService.placeOrder(principal));

        String expectedMessage = "Shopping cart is empty. Cannot place order.";
        String actualMessage = exception.getMessage();

        // Assert
        assertNotNull(actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test for {@link OrderServiceImpl#placeOrder(Principal)}  method.
     **/
    @Test
    void shouldThrowExceptionWhenProfileIsNull() {
        // Arrange
        when(shoppingCartService.getShoppingCartItems(any())).then(invocation -> List.of(new ShoppingCartItem()));
        when(profileRepository.findByUser(any())).thenReturn(null);
        String expectedMessage = "Profile address is incomplete. Please update your profile before placing an order.";

        // Act
        Exception exception = assertThrows(ResponseStatusException.class,
                () -> orderService.placeOrder(any()));


        // Assert
        String actualMessage = exception.getMessage();
        assertNotNull(actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test for {@link OrderServiceImpl#placeOrder(Principal)}  method.
     **/
    @Test
    void shouldThrowExceptionWhenProfileIsEmpty() {
        // Arrange
        Profile profile = new Profile();
        profile.setAddress("");
        profile.setCity("");
        profile.setState("");
        profile.setZip("");

        when(shoppingCartService.getShoppingCartItems(any())).then(invocation -> List.of(new ShoppingCartItem()));
        when(profileRepository.findByUser(any())).thenReturn(profile);

        String expectedMessage = "Profile address is incomplete. Please update your profile before placing an order.";

        // Act
        Exception exception = assertThrows(ResponseStatusException.class,
                () -> orderService.placeOrder(any()));

        // Assert
        String actualMessage = exception.getMessage();
        assertNotNull(actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test for {@link OrderServiceImpl#placeOrder(Principal)}  method.
     **/
    @Test
    void shouldPlaceOrderAndEmptyCartWhenUserExistsAndProfileNotEmptyAndCartNotEmpty() {
        Profile profile = new Profile();
        profile.setAddress("123 Main St");
        profile.setCity("Springfield");
        profile.setState("IL");
        profile.setZip("62701");

        // When
        when(principalService.getUserByPrincipal(any())).thenReturn(new AppUser());
        when(shoppingCartService.getShoppingCartItems(any())).then(invocation -> List.of(new ShoppingCartItem()));
        when(profileRepository.findByUser(any())).thenReturn(profile);

        //Then
        orderService.placeOrder(any());

        // Assert
        verify(orderRepository, times(1)).save(any());
        verify(shoppingCartService, times(1)).emptyCart(any());
    }
}