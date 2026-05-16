package com.taxiandcabservice.dto;

import com.taxiandcabservice.deserializers.UpperCaseDeserialize;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.annotation.JsonDeserialize;

public class VehicleDisplayDTO {

    @NotNull
    private String displayName;

    @NotNull
    private int vehicleTypeId;

    @NotNull
    private Integer id;

    public int getId() {
        return id;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(int vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }
}
