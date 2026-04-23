package com.taxiandcabservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taxiandcabservice.enums.DriverStatus;
import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;

import java.util.List;

@Entity
public class Driver{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Nullable Integer id;

    @OneToOne
    @JoinColumn(name = "authId", nullable = false)
    private AuthEntity authEntity;

    @JsonIgnore
    @OneToMany(mappedBy = "driver",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Vehicle> registeredVehicles;

    private Integer currentVehicleId;

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

    public @Nullable Integer getId() {
        return id;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }

    public AuthEntity getAuthEntity() {
        return authEntity;
    }

    public void setAuthEntity(AuthEntity authEntity) {
        this.authEntity = authEntity;
    }

    public Integer getCurrentVehicleId() {
        return currentVehicleId;
    }

    public void setCurrentVehicleId(Integer currentVehicleId) {
        this.currentVehicleId = currentVehicleId;
    }
}
