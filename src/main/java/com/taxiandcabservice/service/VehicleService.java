package com.taxiandcabservice.service;

import com.taxiandcabservice.entities.Vehicle;
import com.taxiandcabservice.entities.VehicleType;
import com.taxiandcabservice.repositories.VehicleRepository;
import com.taxiandcabservice.repositories.VehicleTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Transactional
    public Optional<VehicleType> findVehicleType(int id) {
        return vehicleTypeRepository.findById(id);
    }

    @Transactional
    public List<VehicleType> findAllVehicleTypes() { return (List<VehicleType>) vehicleTypeRepository.findAll(); }

    @Transactional
    public List<Vehicle> findAllVehiclesById(int driverId) { return vehicleRepository.findByDriver_Id(driverId); }

    @Transactional
    public void addVehicleType(VehicleType vehicleType) { vehicleTypeRepository.save(vehicleType); }

    @Transactional
    public Optional<Vehicle> findVehicleById(int id) { return vehicleRepository.findById(id); }
}
