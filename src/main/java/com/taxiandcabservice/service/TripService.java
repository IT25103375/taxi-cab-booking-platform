package com.taxiandcabservice.service;

import com.taxiandcabservice.dto.TripCreationDTO;
import com.taxiandcabservice.dto.TripDisplayDTO;
import com.taxiandcabservice.dto.TripMinimalDTO;
import com.taxiandcabservice.dto.TripMinimalFareDTO;
import com.taxiandcabservice.entities.*;
import com.taxiandcabservice.enums.DriverStatus;
import com.taxiandcabservice.exceptions.AlreadyBookedException;
import com.taxiandcabservice.exceptions.TripNotFoundException;
import com.taxiandcabservice.mappers.TripMapper;
import com.taxiandcabservice.repositories.TripRepository;
import com.taxiandcabservice.enums.TripStatus;
import com.taxiandcabservice.repositories.DriverRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TripService {

    @Autowired
    UserService userService;

    @Autowired
    DriverService driverService;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    TripRepository tripRepository;

    @Autowired
    TripMapper tripMapper;

    @Transactional
    public Trip createTrip(TripCreationDTO dto) {

        // Trips are created first with no driver assigned
        // Drivers poll for available trips to take
        // TODO: Implement websockets for push if necessary

        Trip trip = new Trip();
        trip.setDriver(null);
        trip.setPassenger(dto.getPassenger());
        trip.setStartAddress(dto.getStartAddress());
        trip.setDestAddress(dto.getDestAddress());
        trip.setStartSubRegion(dto.getStartSubRegion());
        trip.setDestSubRegion(dto.getDestSubRegion());
        trip.setVehicleType(dto.getVehicleType());
        trip.setTripStatus(TripStatus.REQUESTING);

        tripRepository.save(trip);
        return trip;
    }

    // TODO: Do global expiry checks(or a better solution?)
    @Transactional
    public void checkExpiredTrips() { tripRepository.checkExpiredTrips(); }

    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @Transactional
    public List<Trip> checkForNewTrips() throws AlreadyBookedException {

        // Current system only checks if the driver's set subregion is same
        // as the subregion of the driver
        Driver driver = userService.getCurrentDriver();
        checkIfDriverBusy(driver);

        // TODO: DTO for trip details
        return tripRepository.findTripRequests(driver.getSubRegion(), driver.getCurrentVehicle().getVehicleType());
    }

    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @Transactional
    public Optional<Trip> assignForTrip(TripMinimalDTO request) throws TripNotFoundException, AlreadyBookedException {

        Driver driver = userService.getCurrentDriver();
        checkIfDriverBusy(driver); // Recheck if driver is busy

        // Recheck if driver is within correct region
        Trip trip = tripRepository.findTripRequest(driver.getSubRegion(), driver.getCurrentVehicle().getVehicleType(), request.getTripId())
                .orElseThrow(() -> new TripNotFoundException("Trip does not exist"));

        if (trip.getTripStatus() == TripStatus.REQUESTING) {

            trip.setDriver(driver);
            trip.setTripStatus(TripStatus.PICKUP);
            driver.setStatus(DriverStatus.BOOKED);
            tripRepository.save(trip);
            driverRepository.save(driver);
            return Optional.of(trip);
        }

        return Optional.empty();
    }

    @PreAuthorize("hasRole('ROLE_DRIVER')")
    public void checkIfDriverBusy(Driver driver) throws AlreadyBookedException {

        // Roundabout way of calling
        // FIXME: Structure this better
        tripRepository.checkExpiredTrips(driver);
        if (driver.getStatus() == DriverStatus.BOOKED)
            throw new AlreadyBookedException("Driver already booked");
    }

    @PreAuthorize("hasAnyRole('ROLE_PASSENGER', 'ROLE_DRIVER')")
    @Transactional
    public int cancelTrip(TripMinimalDTO request) throws EntityNotFoundException {

        Integer[] userIds = userService.getCurrentUserIds();
        Trip trip = tripRepository.findById(request.getTripId())
                .orElseThrow(() -> new EntityNotFoundException("Trip not found"));

        if (trip.getDriver() != null && !Objects.equals(trip.getDriver().getId(), userIds[1]))
            throw new RuntimeException("Unauthorized");
        if (trip.getPassenger() != null && !Objects.equals(trip.getPassenger().getId(), userIds[0])) {
            throw new RuntimeException("Unauthorized");
        }

        if (trip.getTripStatus() == TripStatus.PICKUP || trip.getTripStatus() == TripStatus.ONGOING ||
                trip.getTripStatus() == TripStatus.REQUESTING) {

            trip.setTripStatus(TripStatus.CANCELLED);
            driverService.updateDriverStatus(trip);
        }
        else return 0;

        tripRepository.save(trip);
        return 1;
    }

    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @Transactional
    public int finishTrip(TripMinimalFareDTO request) {

        Trip trip = tripRepository.findById(request.getTripId())
                .orElseThrow(() -> new EntityNotFoundException("Trip not found"));
        Driver driver = userService.getCurrentDriver();

        if (trip.getTripStatus() == TripStatus.ONGOING && trip.getDriver() == driver) {
            trip.setTripStatus(TripStatus.FINISHED);
            trip.setTripFare(trip.getVehicleType().getBaseFare() +
                    (request.getTripKM() * trip.getVehicleType().getPricePerKM()));

            driverService.updateDriverStatus(trip);
            tripRepository.save(trip);

            return 1;
        }
        else return 0;
    }

    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @Transactional
    public int startTrip(TripMinimalDTO request){

        Trip trip = tripRepository.findById(request.getTripId())
                .orElseThrow(() -> new EntityNotFoundException("Trip not found"));

        if (trip.getTripStatus() == TripStatus.PICKUP && trip.getDriver() == userService.getCurrentDriver()) {
            trip.setTripStatus(TripStatus.ONGOING);
            tripRepository.save(trip);

            return 1;
        }
        else return 0;
    }

    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @Transactional
    public Optional<Trip> getBookedTrip(Driver driver) {
        return tripRepository.findBookedTrip(driver);
    }

    @Transactional
    public List<TripDisplayDTO> getAllPassengerTrips() { return tripMapper.toDisplayDTOList(tripRepository.findAllByPassenger(userService.getCurrentPassenger())); }

    @Transactional
    public List<TripDisplayDTO> getAllDriverTrips() { return tripMapper.toDisplayDTOList(tripRepository.findAllByDriver(userService.getCurrentDriver())); }
}
