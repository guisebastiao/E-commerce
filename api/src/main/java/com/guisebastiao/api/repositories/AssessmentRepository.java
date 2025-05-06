package com.guisebastiao.api.repositories;


import com.guisebastiao.api.models.Assessment;
import com.guisebastiao.api.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssessmentRepository extends JpaRepository<Assessment, UUID> {
    Page<Assessment> findAllByProduct(Product product, Pageable pageable);
}
