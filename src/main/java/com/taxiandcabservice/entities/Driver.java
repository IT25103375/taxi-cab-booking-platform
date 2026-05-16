package com.taxiandcabservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taxiandcabservice.abstracts.User;
import com.taxiandcabservice.enums.DriverStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class Driver extends User {

    @JsonIgnore
    @OneToMany(mappedBy = "driver",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Vehicle> registeredVehicles;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Vehicle currentVehicle;

    @JsonIgnore
    @ManyToOne
    private Region region;

    @ManyToOne
    private SubRegion subRegion;

    @Enumerated(EnumType.STRING)
    private DriverStatus status;

    public List<Vehicle> getRegisteredVehicles() {
        return registeredVehicles;
    }

    public void setRegisteredVehicles(List<Vehicle> registeredVehicles) {
        this.registeredVehicles = registeredVehicles;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public SubRegion getSubRegion() {
        return subRegion;
    }

    public void setSubRegion(SubRegion subRegion) {
        this.subRegion = subRegion;
    }

    public Driver(){
        status = DriverStatus.OFFLINE;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }

    public Vehicle getCurrentVehicle() {
        return currentVehicle;
    }

    public void setCurrentVehicle(Vehicle currentVehicle) {
        this.currentVehicle = currentVehicle;
    }
}
