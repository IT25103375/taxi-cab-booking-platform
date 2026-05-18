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
    private String plateNumber;

    @NotNull
    private Integer id;

    public int getId() {
        return id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public void setId(Integer id) {
        this.id = id;
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
