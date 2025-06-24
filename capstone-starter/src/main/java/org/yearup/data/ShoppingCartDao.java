package org.yearup.data;

import org.yearup.models.ShoppingCartItem;

public interface ShoppingCartDao
{
    ShoppingCartItem getByUserId(int userId);
    // add additional method signatures here
}
