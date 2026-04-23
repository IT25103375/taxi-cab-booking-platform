package com.taxiandcabservice.dto;

import com.taxiandcabservice.deserializers.LowerCaseDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

public class RegionRequest {

    @NotBlank
    @JsonDeserialize(converter = LowerCaseDeserialize.class)
    private String regionName;

    @NotBlank
    private String regionDisplayName;

    @NotNull
    private List<SubRegionRequest> subRegions;

    @JsonDeserialize(converter = LowerCaseDeserialize.class)
    public String getRegionName() {
        return regionName;
    }

    @JsonDeserialize(converter = LowerCaseDeserialize.class)
    public RegionRequest setRegionName(String regionName) {
        this.regionName = regionName;
        return this;
    }

    public List<SubRegionRequest> getSubRegions() {
        return subRegions;
    }

    public RegionRequest setSubRegions(List<SubRegionRequest> subRegions) {
        this.subRegions = subRegions;
        return this;
    }

    public String getRegionDisplayName() {
        return regionDisplayName;
    }

    public RegionRequest setRegionDisplayName(String regionDisplayName) {
        this.regionDisplayName = regionDisplayName;
        return this;
    }
}
