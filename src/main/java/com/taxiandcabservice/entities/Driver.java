package com.taxiandcabservice.entities;

import com.taxiandcabservice.enums.DriverStatus;
import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;

@Entity
public class Driver{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Nullable Integer id;

    @OneToOne
    @JoinColumn(name = "authId", nullable = false)
    private AuthEntity authEntity;

    @ManyToOne
    private VehicleType vehicleType;

    @ManyToOne
    private Region region;

    @ManyToOne
    private SubRegion subRegion;

    @Enumerated(EnumType.STRING)
    private DriverStatus status;

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AuthEntity getAuthEntity() {
        return authEntity;
    }

    public void setAuthEntity(AuthEntity authEntity) {
        this.authEntity = authEntity;
    }
}
