package com.guisebastiao.api.models;

import com.guisebastiao.api.enums.State;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "street", nullable = false, length = 255)
    private String street;

    @Column(name = "city", nullable = false, length = 255)
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false, length = 2)
    private State state;

    @Column(name = "zip_code", nullable = false, length = 8)
    private String zip;
}
