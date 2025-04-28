package com.guisebastiao.api.repositories;

import com.guisebastiao.api.models.Cart;
import com.guisebastiao.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByUser(User user);
}
