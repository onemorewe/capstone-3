package org.yearup.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.yearup.controllers.dto.CartDto;
import org.yearup.controllers.dto.CartUpdateDto;
import org.yearup.security.JwtAccessDeniedHandler;
import org.yearup.security.JwtAuthenticationEntryPoint;
import org.yearup.security.UserModelDetailsService;
import org.yearup.security.jwt.TokenProvider;
import org.yearup.service.ShoppingCartService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link org.yearup.controllers.ShoppingCartController}  class.
 **/

@WebMvcTest(ShoppingCartController.class)
@SuppressWarnings("unused")
class ShoppingCartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingCartService shoppingCartService;

    @MockBean
    private TokenProvider tokenProvider;
    @MockBean
    private UserModelDetailsService userModelDetailsService;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * Test for {@link ShoppingCartController#getCart(Principal)}  method.
     **/
    @Test
    @WithMockUser(username = "test", roles = "USER")
    void getCartShouldReturnCartForAuthenticatedUser() throws Exception {
        // ARRANGE
        CartDto cartDto = new CartDto();
        cartDto.setItems(Collections.emptyMap());
        cartDto.setTotal(BigDecimal.ZERO);

        when(shoppingCartService.getCart(any(Principal.class))).thenReturn(cartDto);

        // ACT & ASSERT
        mockMvc.perform(get("/cart")
                        .with(user("test").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(0.0));
    }

    /**
     * Test for {@link ShoppingCartController#addProductToCart(int, Principal)}  method.
     **/
    @Test
    @WithMockUser(username = "test", roles = "USER")
    void addProductShouldReturnNoContent() throws Exception {
        // ARRANGE
        int productId = 1;
        doNothing().when(shoppingCartService).addProductToCart(eq(productId), any(Principal.class));

        // ACT & ASSERT
        mockMvc.perform(post("/cart/products/{productId}", productId)
                        .with(user("test").roles("USER")))
                .andExpect(status().isNoContent());

        verify(shoppingCartService).addProductToCart(eq(productId), any(Principal.class));
    }

    /**
     * Test for {@link ShoppingCartController#updateProductInCart(int, CartUpdateDto, Principal)}  method.
     **/
    @Test
    @WithMockUser(username = "test", roles = "USER")
    void updateProductShouldReturnNoContent() throws Exception {
        // ARRANGE
        int productId = 1;
        CartUpdateDto updateDto = new CartUpdateDto();
        updateDto.setQuantity(5);

        doNothing().when(shoppingCartService).updateProductInCart(eq(productId), any(CartUpdateDto.class), any(Principal.class));

        // ACT & ASSERT
        mockMvc.perform(patch("/cart/products/{productId}", productId)
                        .with(user("test").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isNoContent());

        verify(shoppingCartService).updateProductInCart(eq(productId), any(CartUpdateDto.class), any(Principal.class));
    }
}
