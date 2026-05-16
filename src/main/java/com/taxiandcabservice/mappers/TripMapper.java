package com.taxiandcabservice.mappers;

import com.taxiandcabservice.dto.TripCreationDTO;
import com.taxiandcabservice.dto.TripDTO;
import com.taxiandcabservice.dto.TripDisplayDTO;
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

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TripMapper {

    @Autowired
    UserService userService;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    RegionService regionService;

    @Mapping(source = "dto.startAddress", target = "startAddress")
    @Mapping(source = "dto.destAddress", target = "destAddress")
    @Mapping(source = "dto.startSubRegionId", target = "startSubRegion")
    @Mapping(source = "dto.destSubRegionId", target = "destSubRegion")
    @Mapping(source = "dto.vehicleTypeId", target = "vehicleType")
    @Mapping(source = "passenger", target = "passenger")
    public abstract TripCreationDTO toTrip(TripDTO dto, Passenger passenger);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "passenger.authEntity.username", target = "passengerName")
    @Mapping(source = "driver.authEntity.username", target = "driverName")
    @Mapping(source = "startAddress", target = "startAddress")
    @Mapping(source = "startSubRegion.region.displayName", target = "startRegion")
    @Mapping(source = "startSubRegion.displayName", target = "startSubRegion")
    @Mapping(source = "destAddress", target = "destAddress")
    @Mapping(source = "destSubRegion.region.displayName", target = "destRegion")
    @Mapping(source = "destSubRegion.displayName", target = "destSubRegion")
    @Mapping(source = "vehicleType.displayName", target = "vehicleType")
    @Mapping(source = "tripStatus", target = "tripStatus")
    @Mapping(source = "tripFare", target = "tripFare")
    @Mapping(source = "expDate", target = "expDate")
    public abstract TripDisplayDTO toDisplayDTO(Trip trip);

    public abstract List<TripDisplayDTO> toDisplayDTOList(List<Trip> trips);

    public SubRegion toSubRegion(Integer id) throws EntityNotFoundException {
        return regionService.findSubRegion(id)
                .orElseThrow(() -> new EntityNotFoundException("SubRegion not found"));
    }

    public VehicleType toVehicleType(int vehicleTypeId) throws EntityNotFoundException {
        return vehicleService.findVehicleType(vehicleTypeId)
                .orElseThrow(() -> new EntityNotFoundException("VehicleType not found"));
    }
}
