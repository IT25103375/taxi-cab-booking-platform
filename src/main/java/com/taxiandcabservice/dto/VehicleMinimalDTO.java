package com.taxiandcabservice.dto;

import jakarta.validation.constraints.NotNull;

public class VehicleMinimalDTO {

    @NotNull
    private int vehicleId;

    public @NotNull int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(@NotNull int vehicleId) {
        this.vehicleId = vehicleId;
    }
}
