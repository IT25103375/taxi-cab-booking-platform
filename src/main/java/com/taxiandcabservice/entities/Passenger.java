package com.taxiandcabservice.entities;

import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;

@Entity
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Nullable Integer id;

    @OneToOne
    @JoinColumn(name = "authId", nullable = false)
    private AuthEntity authEntity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AuthEntity getAuthEntity() {
        return authEntity;
    }

    public void setAuthEntity(AuthEntity authEntity) {
        this.authEntity = authEntity;
    }
}
