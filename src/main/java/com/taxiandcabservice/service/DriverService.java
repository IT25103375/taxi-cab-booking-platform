package com.taxiandcabservice.service;

import com.taxiandcabservice.dto.VehicleDTO;
import com.taxiandcabservice.dto.VehicleMinimalDTO;
import com.taxiandcabservice.entities.AuthEntity;
import com.taxiandcabservice.entities.Driver;
import com.taxiandcabservice.entities.Trip;
import com.taxiandcabservice.entities.Vehicle;
import com.taxiandcabservice.enums.DriverStatus;
import com.taxiandcabservice.enums.TripStatus;
import com.taxiandcabservice.mappers.VehicleMapper;
import com.taxiandcabservice.repositories.DriverRepository;
import com.taxiandcabservice.repositories.TripRepository;
import com.taxiandcabservice.repositories.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class DriverService {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    VehicleMapper vehicleMapper;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    TripRepository tripRepository;

    @Autowired
    UserService userService;

    @Transactional
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    public Optional<Vehicle> addVehicle(VehicleDTO request) {

        // FIXME: STOP RELYING ON ROLLBACK AND IMPLEMENT PROPER CHECKS
        try {
            Driver driver = driverRepository.findByAuthEntity_Email(((AuthEntity) SecurityContextHolder.getContext().
                            getAuthentication().getPrincipal()).getEmail())
                    .orElseThrow(() -> new EntityNotFoundException("Driver does not exist"));
            Vehicle newVehicle = vehicleMapper.toVehicle(request);
            newVehicle.setDriver(driver);

            // Disallow vehicle modifications if booked
            if (driver.getStatus() == DriverStatus.BOOKED)
                throw new RuntimeException("Not allowed while booked");

            // Modify driver to update two-way relation
            // Set currentVehicle to newVehicle if non is set
            driver.getRegisteredVehicles().add(newVehicle);
            if (driver.getCurrentVehicle() == null) driver.setCurrentVehicle(newVehicle);
            // FIXME: Check if this works properly

            vehicleRepository.save(newVehicle);
            driverRepository.save(driver);

            return Optional.of(newVehicle);
        }
        catch (Exception e) {
            // TODO: Proper error system
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    public boolean setCurrentVehicle(VehicleMinimalDTO request) {

        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new EntityNotFoundException("Vehicle does not exist"));
        Driver driver = driverRepository.findByAuthEntity_Email(((AuthEntity) SecurityContextHolder.getContext().
                        getAuthentication().getPrincipal()).getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Driver does not exist"));

        // Don't update if driver is busy
        if (driver.getStatus() == DriverStatus.BOOKED)
            return false;

        // Check if vehicle is registered under driver
        if (!driver.getRegisteredVehicles().contains(vehicle))
            throw new EntityNotFoundException("Vehicle not registered under driver");

        driver.setCurrentVehicle(vehicle);
        driverRepository.save(driver);
        return true;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    public boolean removeVehicle(VehicleMinimalDTO request) {

        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new EntityNotFoundException("Vehicle does not exist"));
        Driver driver = driverRepository.findByAuthEntity_Email(((AuthEntity) SecurityContextHolder.getContext().
                        getAuthentication().getPrincipal()).getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Driver does not exist"));

        // Don't update if driver is busy
        if (driver.getStatus() == DriverStatus.BOOKED)
            return false;

        if (Objects.equals(driver.getCurrentVehicle(), vehicle))
            driver.setCurrentVehicle(null);

        if (driver.getRegisteredVehicles().remove(vehicle)) {
            driverRepository.save(driver);
            return true;
        }
        else throw new EntityNotFoundException("No such vehicle");
    }

    @Transactional
    public void updateDriverStatus(Trip trip) {

        Driver driver = trip.getDriver();

        if (trip.getTripStatus() == TripStatus.CANCELLED ||
                trip.getTripStatus() == TripStatus.FINISHED) {

            driver.setStatus(DriverStatus.AVAILABLE);
            driverRepository.save(driver);
        }
    }
}
