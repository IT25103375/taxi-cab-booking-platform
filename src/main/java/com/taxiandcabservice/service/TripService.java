package com.taxiandcabservice.service;

import com.taxiandcabservice.entities.SubRegion;
import com.taxiandcabservice.entities.Trip;
import com.taxiandcabservice.repositories.TripRepository;
import com.taxiandcabservice.enums.TripStatus;
import com.taxiandcabservice.entities.Driver;
import com.taxiandcabservice.repositories.DriverRepository;
import com.taxiandcabservice.entities.Passenger;
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
    public Optional<Trip> createTrip(Passenger passenger, SubRegion startSubRegion, SubRegion destSubRegion) {
        List<Driver> driverList = driverRepository.findBySubRegion(startSubRegion);
        Optional<Trip> opTrip = Optional.empty();

        for (Driver d : driverList) {
            if (driverRepository.bookDriverIfAvailable(d.getId()) == 1) {
                Trip trip = new Trip();
                trip.setDriver(d);
                trip.setPassenger(passenger);
                trip.setStartSubRegion(startSubRegion);
                trip.setDestSubRegion(destSubRegion);
                trip.setTripStatus(TripStatus.PICKUP);

                tripRepository.save(trip);
                opTrip = Optional.of(trip);
                break;
            }
        }

        return opTrip;
    }
}
