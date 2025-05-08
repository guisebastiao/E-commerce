package com.guisebastiao.api.repositories;

import com.guisebastiao.api.models.ProfilePicture;
import com.guisebastiao.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, UUID> {
    Optional<ProfilePicture> findByUser(User user);

    @Modifying
    @Query("DELETE FROM ProfilePicture p WHERE p.user = :user")
    void deleteByUser(@Param("user") User user);
}
