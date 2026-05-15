package com.taxiandcabservice.mappers;

import com.taxiandcabservice.dto.VehicleDTO;
import com.taxiandcabservice.dto.VehicleTypeDTO;
import com.taxiandcabservice.entities.Driver;
import com.taxiandcabservice.entities.Vehicle;
import com.taxiandcabservice.entities.VehicleType;
import com.taxiandcabservice.repositories.VehicleTypeRepository;
import com.taxiandcabservice.service.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class VehicleMapper {

    @Autowired
    VehicleService vehicleService;

    @Mapping(source = "dto.vehicleTypeId", target = "vehicleType")
    public abstract Vehicle toVehicle(VehicleDTO dto);

    public VehicleType toVehicleType(int vehicleTypeId) throws EntityNotFoundException {
        return vehicleService.findVehicleType(vehicleTypeId)
                .orElseThrow(() -> new EntityNotFoundException("VehicleType not found"));
    }

    public abstract VehicleTypeDTO toVehicleTypeDTO(VehicleType vehicleType);

    public abstract List<VehicleTypeDTO> toVehicleTypeDTOList(List<VehicleType> vehicleTypes);
}
