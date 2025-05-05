package com.guisebastiao.api.repositories;

import com.guisebastiao.api.models.Address;
import com.guisebastiao.api.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    Page<Address> findAllByUser(User user, Pageable pageable);
}
