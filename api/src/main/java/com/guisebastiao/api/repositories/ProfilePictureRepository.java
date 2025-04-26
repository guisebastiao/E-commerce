package com.guisebastiao.api.repositories;

import com.guisebastiao.api.models.ProfilePicture;
import com.guisebastiao.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, UUID> {
    Optional<ProfilePicture> findByUser(User user);
}
