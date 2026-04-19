package com.taxiandcabservice.service;

import com.taxiandcabservice.entities.VehicleType;
import com.taxiandcabservice.repositories.VehicleTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleTypeService {

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    @Transactional
    public Optional<VehicleType> findVehicleType(String name) {
        return vehicleTypeRepository.findByName(name);
    }
}
