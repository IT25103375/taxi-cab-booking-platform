package com.taxiandcabservice.dto;

import com.taxiandcabservice.deserializers.LowerCaseDeserialize;
import com.taxiandcabservice.enums.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.annotation.JsonDeserialize;

public class
RegisterRequest {
    //Common
    @NotBlank
    private String username;

    @NotBlank
    @JsonDeserialize(converter = LowerCaseDeserialize.class)
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private UserType userType;

    //Driver-only
    private Integer regionId;
    private Integer subRegionId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonDeserialize(converter = LowerCaseDeserialize.class)
    public String getEmail() {
        return email;
    }

    @JsonDeserialize(converter = LowerCaseDeserialize.class)
    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Integer getSubRegionId() {
        return subRegionId;
    }

    public void setSubRegionId(Integer subRegionId) {
        this.subRegionId = subRegionId;
    }

}
