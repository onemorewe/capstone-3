package org.yearup.data.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yearup.models.AppUser;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCartItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartItem, Integer> {

    Optional<ShoppingCartItem> findByUserAndProduct(AppUser userId, Product productId);

    List<ShoppingCartItem> findAllByUser(AppUser user);
}
