package com.taxiandcabservice.dto;

import com.taxiandcabservice.enums.UserType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public class AuthDTO {

    @NotNull
    private String username;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserType userType;

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
