package com.taxiandcabservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TripDTO {

    @NotNull
    private int startSubRegionId;

    @NotNull
    private int endSubRegionId;

    @NotNull
    private int vehicleTypeId;

    public int getStartSubRegionId() {
        return startSubRegionId;
    }

    public void setStartSubRegionId(int startSubRegionId) {
        this.startSubRegionId = startSubRegionId;
    }

    public int getEndSubRegionId() {
        return endSubRegionId;
    }

    public void setEndSubRegionId(int endSubRegionId) {
        this.endSubRegionId = endSubRegionId;
    }

    public int getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(int vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }
}
