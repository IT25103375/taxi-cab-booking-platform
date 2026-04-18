package com.taxiandcabservice.accessing_data_mysql.users;

import com.taxiandcabservice.accessing_data_mysql.common.Region;
import com.taxiandcabservice.accessing_data_mysql.common.SubRegion;
import com.taxiandcabservice.accessing_data_mysql.common.VehicleType;
import jakarta.persistence.*;

@Entity
@Table(name = "drivers")
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
