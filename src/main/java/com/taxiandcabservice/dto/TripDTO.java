package com.taxiandcabservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TripDTO {

    @NotBlank
    private String startAddress;

    @NotBlank
    private String destAddress;

    @NotNull
    private int startSubRegionId;

    @NotNull
    private int destSubRegionId;

    @NotNull
    private int vehicleTypeId;

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getDestAddress() {
        return destAddress;
    }

    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    public int getStartSubRegionId() {
        return startSubRegionId;
    }

    public void setStartSubRegionId(int startSubRegionId) {
        this.startSubRegionId = startSubRegionId;
    }

    public int getDestSubRegionId() {
        return destSubRegionId;
    }

    public void setDestSubRegionId(int destSubRegionId) {
        this.destSubRegionId = destSubRegionId;
    }

    public int getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(int vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }
}
