package com.taxiandcabservice.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taxiandcabservice.entities.AuthEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.Date;

@MappedSuperclass
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Nullable Integer id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "authId", nullable = false)
    private AuthEntity authEntity;

    public @Nullable Integer getId() {
        return id;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }

    public AuthEntity getAuthEntity() {
        return authEntity;
    }

    public void setAuthEntity(AuthEntity authEntity) {
        this.authEntity = authEntity;
    }
}
