package com.taxiandcabservice.dto;

import com.taxiandcabservice.deserializers.LowerCaseDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

public class RegionCreationDTO extends RegionDTO{

    @NotNull
    private List<SubRegionCreationDTO> subRegions;

    public List<SubRegionCreationDTO> getSubRegions() {
        return subRegions;
    }

    public RegionCreationDTO setSubRegions(List<SubRegionCreationDTO> subRegions) {
        this.subRegions = subRegions;
        return this;
    }
}
