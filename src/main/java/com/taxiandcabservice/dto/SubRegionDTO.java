package com.taxiandcabservice.dto;

import com.taxiandcabservice.deserializers.LowerCaseDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.annotation.JsonDeserialize;

public class SubRegionDTO {

    @NotNull
    private Integer subRegionId;

    @NotBlank
    @JsonDeserialize(converter = LowerCaseDeserialize.class)
    protected String subRegionName;

    @NotBlank
    protected String subRegionDisplayName;

    public Integer getSubRegionId() {
        return subRegionId;
    }

    public void setSubRegionId(Integer subRegionId) {
        this.subRegionId = subRegionId;
    }

    @JsonDeserialize(converter = LowerCaseDeserialize.class)
    public String getSubRegionName() {
        return subRegionName;
    }

    @JsonDeserialize(converter = LowerCaseDeserialize.class)
    public SubRegionDTO setSubRegionName(String subRegionName) {
        this.subRegionName = subRegionName;
        return this;
    }

    public String getSubRegionDisplayName() {
        return subRegionDisplayName;
    }

    public SubRegionDTO setSubRegionDisplayName(String subRegionDisplayName) {
        this.subRegionDisplayName = subRegionDisplayName;
        return this;
    }
}
