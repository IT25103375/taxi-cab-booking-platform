package com.taxiandcabservice.dto;

import com.taxiandcabservice.deserializers.LowerCaseDeserialize;
import jakarta.validation.constraints.NotBlank;
import tools.jackson.databind.annotation.JsonDeserialize;

public class SubRegionRequest {

    @NotBlank
    @JsonDeserialize(converter = LowerCaseDeserialize.class)
    private String subRegionName;

    @NotBlank
    private String subRegionDisplayName;

    @JsonDeserialize(converter = LowerCaseDeserialize.class)
    public String getSubRegionName() {
        return subRegionName;
    }

    @JsonDeserialize(converter = LowerCaseDeserialize.class)
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
