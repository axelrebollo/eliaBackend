package com.axel.user.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincremental
    private Long id;

    @Column(nullable = false) // Campo obligatorio
    private String name;

    @Column(nullable = false, unique = true) // Campo obligatorio y único
    private String email;
}
