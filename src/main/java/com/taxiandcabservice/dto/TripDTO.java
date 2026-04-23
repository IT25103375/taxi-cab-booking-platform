package com.taxiandcabservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TripDTO {

    @NotBlank
    private Integer startSubRegionId;

    @NotBlank
    private Integer endSubRegionId;

    @NotNull
    private int vehicleTypeId;

    public Integer getStartSubRegionId() {
        return startSubRegionId;
    }

    public void setStartSubRegionId(Integer startSubRegionId) {
        this.startSubRegionId = startSubRegionId;
    }

    public Integer getEndSubRegionId() {
        return endSubRegionId;
    }

    public void setEndSubRegionId(Integer endSubRegionId) {
        this.endSubRegionId = endSubRegionId;
    }

    public int getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(int vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }
}
