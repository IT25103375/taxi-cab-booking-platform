package com.taxiandcabservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class RegionRequest {

    @NotBlank
    private String regionName;

    @NotBlank
    private String regionDisplayName;

    @NotNull
    private List<SubRegionRequest> subRegions;

    public String getRegionName() {
        return regionName;
    }

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
