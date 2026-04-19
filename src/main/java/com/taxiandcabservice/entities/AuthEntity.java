package com.taxiandcabservice.entities;

import com.taxiandcabservice.enums.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class AuthEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String encryptedPW;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToOne(mappedBy = "authEntity")
    private Driver driver;

    @OneToOne(mappedBy = "authEntity")
    private Passenger passenger;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedPW() {
        return encryptedPW;
    }

    public void setEncryptedPW(String encryptedPW) {
        this.encryptedPW = encryptedPW;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
