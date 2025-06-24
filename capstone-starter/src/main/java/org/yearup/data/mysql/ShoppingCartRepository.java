package org.yearup.data.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yearup.models.ShoppingCartItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartItem, Integer> {

    List<ShoppingCartItem> findAllByUser_Id(Integer id);

    Optional<ShoppingCartItem> findByUser_IdAndProduct_ProductId(Integer userId, Integer productId);
}
