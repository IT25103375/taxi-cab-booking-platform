package com.taxiandcabservice.users;

import com.taxiandcabservice.common.Region;
import com.taxiandcabservice.common.SubRegion;
import com.taxiandcabservice.common.VehicleType;
import jakarta.persistence.*;

@Entity
public class Driver extends Passenger{

    @ManyToOne
    private VehicleType vehicleType;

    @ManyToOne
    private Region region;

    @ManyToOne
    private SubRegion subRegion;

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
}
