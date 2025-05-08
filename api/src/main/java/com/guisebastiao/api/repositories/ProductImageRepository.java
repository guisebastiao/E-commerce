package com.guisebastiao.api.repositories;

import com.guisebastiao.api.models.Product;
import com.guisebastiao.api.models.ProductImage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ProductImageRepository extends CrudRepository<ProductImage, UUID> {
    List<ProductImage> findAllByProduct(Product product);
}
