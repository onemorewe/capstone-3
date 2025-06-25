package org.yearup.service;

import java.security.Principal;

public interface OrderService {
    void placeOrder(Principal principal);
}
