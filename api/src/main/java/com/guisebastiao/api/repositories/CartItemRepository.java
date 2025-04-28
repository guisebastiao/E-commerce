package com.guisebastiao.api.repositories;

import com.guisebastiao.api.models.Cart;
import com.guisebastiao.api.models.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    Page<CartItem> findByCart(Cart cart, Pageable pageable);
}
