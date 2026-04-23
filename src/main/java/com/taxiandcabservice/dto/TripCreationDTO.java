package com.taxiandcabservice.dto;

import com.taxiandcabservice.entities.Passenger;
import com.taxiandcabservice.entities.SubRegion;
import com.taxiandcabservice.entities.VehicleType;
import jakarta.validation.constraints.NotNull;

public class TripCreationDTO {

    @NotNull
    private Passenger passenger;

    @NotNull
    private VehicleType vehicleType;

    @NotNull
    private SubRegion startSubRegion;

    @NotNull
    private SubRegion destSubRegion;

    public SubRegion getDestSubRegion() {
        return destSubRegion;
    }

    public void setDestSubRegion(SubRegion destSubRegion) {
        this.destSubRegion = destSubRegion;
    }

    public SubRegion getStartSubRegion() {
        return startSubRegion;
    }

    public void setStartSubRegion(SubRegion startSubRegion) {
        this.startSubRegion = startSubRegion;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
}
