package com.taxiandcabservice.dto;

import com.taxiandcabservice.deserializers.UpperCaseDeserialize;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.annotation.JsonDeserialize;

public class VehicleDTO {

    @NotNull
    private String displayName;

    @NotNull
    @JsonDeserialize(converter = UpperCaseDeserialize.class)
    private String plateNumber;

    @NotNull
    private int vehicleTypeId;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @JsonDeserialize(converter = UpperCaseDeserialize.class)
    public String getPlateNumber() {
        return plateNumber;
    }

    @JsonDeserialize(converter = UpperCaseDeserialize.class)
    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public int getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(int vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }
}
