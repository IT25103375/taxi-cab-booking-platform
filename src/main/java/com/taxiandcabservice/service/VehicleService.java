package com.taxiandcabservice.service;

import com.taxiandcabservice.entities.VehicleType;
import com.taxiandcabservice.repositories.VehicleTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    @Transactional
    public Optional<VehicleType> findVehicleType(int id) {
        return vehicleTypeRepository.findById(id);
    }

    public void addVehicleType(VehicleType vehicleType) { vehicleTypeRepository.save(vehicleType); }
}
