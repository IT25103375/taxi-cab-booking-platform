package com.taxiandcabservice.service;

import com.taxiandcabservice.dto.TripCreationDTO;
import com.taxiandcabservice.entities.*;
import com.taxiandcabservice.repositories.TripRepository;
import com.taxiandcabservice.enums.TripStatus;
import com.taxiandcabservice.repositories.DriverRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    @Autowired
    DriverService driverService;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    TripRepository tripRepository;

    @Transactional
    public Optional<Trip> createTrip(TripCreationDTO dto) {

        List<Driver> driverList = driverRepository.
                findDriver(dto.getStartSubRegion(), dto.getVehicleType());
        Optional<Trip> opTrip = Optional.empty();

        for (Driver d : driverList) {
            if (driverRepository.bookDriverIfAvailable(d.getId()) == 1) {

                // Check in case of race condition
                if (d.getCurrentVehicleId() != null)
                    throw new RuntimeException("Current vehicle not set");

                Trip trip = new Trip();
                trip.setDriver(d);
                trip.setPassenger(dto.getPassenger());
                trip.setStartSubRegion(dto.getStartSubRegion());
                trip.setDestSubRegion(dto.getDestSubRegion());
                trip.setTripStatus(TripStatus.PICKUP);

                tripRepository.save(trip);
                opTrip = Optional.of(trip);
                break;
            }
        }

        return opTrip;
    }
}
