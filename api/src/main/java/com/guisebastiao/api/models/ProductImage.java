package com.guisebastiao.api.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "product_images")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "object_id", length = 255, nullable = false)
    private String objectId;
}
