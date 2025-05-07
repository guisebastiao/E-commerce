package com.guisebastiao.api.repositories;

import com.guisebastiao.api.models.Favorite;
import com.guisebastiao.api.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {
    Page<Favorite> findAllByUser(User user, Pageable pageable);
}
