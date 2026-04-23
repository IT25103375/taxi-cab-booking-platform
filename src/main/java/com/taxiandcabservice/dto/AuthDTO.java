package com.taxiandcabservice.dto;

import com.taxiandcabservice.deserializers.LowerCaseDeserialize;
import com.taxiandcabservice.enums.UserType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.annotation.JsonDeserialize;

public class AuthDTO {

    // TODO: what the heck was this declared for

    @NotNull
    @JsonDeserialize(converter = LowerCaseDeserialize.class)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @JsonDeserialize(converter = LowerCaseDeserialize.class)
    public String getUsername() {
        return email;
    }

    @JsonDeserialize(converter = LowerCaseDeserialize.class)
    public void setUsername(String username) {
        this.email = username;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
