package com.taxiandcabservice.mappers;

import com.taxiandcabservice.dto.TripCreationDTO;
import com.taxiandcabservice.dto.TripDTO;
import com.taxiandcabservice.entities.Passenger;
import com.taxiandcabservice.entities.SubRegion;
import com.taxiandcabservice.entities.Trip;
import com.taxiandcabservice.entities.VehicleType;
import com.taxiandcabservice.service.RegionService;
import com.taxiandcabservice.service.UserService;
import com.taxiandcabservice.service.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class TripMapper {

    @Autowired
    UserService userService;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    RegionService regionService;

    @Mapping(source = "dto.startSubRegionId", target = "startSubRegion")
    @Mapping(source = "dto.endSubRegionId", target = "destSubRegion")
    @Mapping(source = "dto.vehicleTypeId", target = "vehicleType")
    @Mapping(source = "passenger", target = "passenger")
    public abstract TripCreationDTO toTrip(TripDTO dto, Passenger passenger);

    public SubRegion toSubRegion(Integer id) throws EntityNotFoundException {
        return regionService.findSubRegion(id)
                .orElseThrow(() -> new EntityNotFoundException("SubRegion not found"));
    }

    public VehicleType toVehicleType(int vehicleTypeId) throws EntityNotFoundException {
        return vehicleService.findVehicleType(vehicleTypeId)
                .orElseThrow(() -> new EntityNotFoundException("VehicleType not found"));
    }
}
