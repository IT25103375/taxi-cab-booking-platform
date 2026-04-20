package com.taxiandcabservice.dto;

import jakarta.validation.constraints.NotBlank;

public class SubRegionRequest {

    @NotBlank
    private String subRegionName;

    @NotBlank
    private String subRegionDisplayName;

    public String getSubRegionName() {
        return subRegionName;
    }

    public SubRegionRequest setSubRegionName(String subRegionName) {
        this.subRegionName = subRegionName;
        return this;
    }

    public String getSubRegionDisplayName() {
        return subRegionDisplayName;
    }

    public SubRegionRequest setSubRegionDisplayName(String subRegionDisplayName) {
        this.subRegionDisplayName = subRegionDisplayName;
        return this;
    }
}
