package com.guisebastiao.api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "coupons")
public class Coupom {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(name = "value_discount", nullable = false)
    private Double valueDiscount;

    @ManyToMany(mappedBy = "coupons")
    private List<User> usedBy = new ArrayList<>();
}
