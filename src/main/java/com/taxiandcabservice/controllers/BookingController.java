package com.taxiandcabservice.controllers;

import com.taxiandcabservice.abstracts.User;
import com.taxiandcabservice.dto.*;
import com.taxiandcabservice.entities.*;
import com.taxiandcabservice.exceptions.AlreadyBookedException;
import com.taxiandcabservice.exceptions.CurrentVehicleException;
import com.taxiandcabservice.exceptions.TripNotFoundException;
import com.taxiandcabservice.mappers.TripMapper;
import com.taxiandcabservice.service.TripService;
import com.taxiandcabservice.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/booking")
public class BookingController {

    @Autowired
    TripService tripService;

    @Autowired
    UserService userService;

    @Autowired
    TripMapper tripMapper;

    // TODO: Add fare calculation methods to be requested from
    // TODO: Implement start date/time for calculating fare

    // TODO: Rest controller advice to stop duplicate try-catch error handling
    // TODO: Handle runtime exceptions like EntityNotFound in controller advice
    // TODO: Safely remove preAuthorize on controller and keep only in service

    @PostMapping(path = "/trip")
    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @Transactional
    public ResponseEntity<Object> CreateTrip(@Valid @RequestBody TripDTO request) throws RuntimeException{

        // Use intermediate dto to reduce clutter in controller
        TripCreationDTO tCDTO = tripMapper.toTrip(request, userService.getCurrentPassenger());
        return ResponseEntity.ok().body(tripService.createTrip(tCDTO));
    }

    @GetMapping(path = "/poll")
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @Transactional
    public ResponseEntity<List<TripDisplayDTO>> pollGetRequestedTrips() {

        try {
            List<Trip> requestedTrip = tripService.checkForNewTrips();

            if (requestedTrip.isEmpty()) return ResponseEntity.ok().build();
            else return ResponseEntity.ok(tripMapper.toDisplayDTOList(requestedTrip));
        }
        catch (AlreadyBookedException aBE) {
            Optional<Trip> bookedTrip = tripService.getBookedTrip(userService.getCurrentDriver());
            return bookedTrip.map(trip -> ResponseEntity.ok(Collections.singletonList(tripMapper.toDisplayDTO(
                    trip)))).orElseGet(() -> ResponseEntity.ok().body(Collections.emptyList()));
        }
        catch (CurrentVehicleException cVE) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping(path = "/assign-driver")
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @Transactional
    public ResponseEntity<String> assignTripDriver(@Valid @RequestBody TripMinimalDTO request) {

        try {
            Optional<Trip> opTrip = tripService.assignForTrip(request);

            // Intellij things
            return opTrip.map(_ ->
                    ResponseEntity.ok().body("Success")).orElseGet(() ->
                    ResponseEntity.badRequest().body("Trip already taken"));
        }
        catch (TripNotFoundException | AlreadyBookedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping(path = "/cancel-trip")
    @PreAuthorize("hasAnyRole('ROLE_PASSENGER', 'ROLE_DRIVER')")
    @Transactional
    public ResponseEntity<String> cancelTrip(@Valid @RequestBody TripMinimalDTO request) {

        if (tripService.cancelTrip(request) == 1) return ResponseEntity.ok().body("Success");
        else return ResponseEntity.badRequest().body("Failed to cancel trip");
    }

    @PatchMapping(path = "/finish-trip")
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @Transactional
    public ResponseEntity<String> finishTrip(@Valid @RequestBody TripMinimalFareDTO request) {

        if (tripService.finishTrip(request) == 1) return ResponseEntity.ok().body("Success");
        else return ResponseEntity.badRequest().body("Failed to finish trip");
    }

    @PatchMapping(path = "/start-trip")
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @Transactional
    public ResponseEntity<String> startTrip(@Valid @RequestBody TripMinimalDTO request) {

        if (tripService.startTrip(request) == 1) return ResponseEntity.ok().body("Success");
        else return ResponseEntity.badRequest().body("Failed to start trip");
    }

    @GetMapping(path = "/trip")
    @Transactional
    public ResponseEntity<List<TripDisplayDTO>> getAllTrips() {
        User[] users = userService.getCurrentUser();
        return ResponseEntity.ok((users[0] != null) ? tripService.getAllPassengerTrips() : tripService.getAllDriverTrips());
    }
}
