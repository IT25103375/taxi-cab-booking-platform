package com.taxiandcabservice.dto;

import com.taxiandcabservice.deserializers.LowerCaseDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

public class RegionDTO {

    @NotNull
    private Integer regionId;

    @NotBlank
    @JsonDeserialize(converter = LowerCaseDeserialize.class)
    protected String regionName;

    @NotBlank
    protected String regionDisplayName;

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    @JsonDeserialize(converter = LowerCaseDeserialize.class)
    public String getRegionName() {
        return regionName;
    }

    @JsonDeserialize(converter = LowerCaseDeserialize.class)
    public RegionDTO setRegionName(String regionName) {
        this.regionName = regionName;
        return this;
    }

    public String getRegionDisplayName() {
        return regionDisplayName;
    }

    public RegionDTO setRegionDisplayName(String regionDisplayName) {
        this.regionDisplayName = regionDisplayName;
        return this;
    }
}
