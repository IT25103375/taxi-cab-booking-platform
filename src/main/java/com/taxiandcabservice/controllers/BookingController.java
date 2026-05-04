package com.taxiandcabservice.controllers;

import com.taxiandcabservice.dto.TripCreationDTO;
import com.taxiandcabservice.dto.TripDTO;
import com.taxiandcabservice.dto.TripMinimalDTO;
import com.taxiandcabservice.entities.*;
import com.taxiandcabservice.exceptions.AlreadyBookedException;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/booking")
public class BookingController {

    @Autowired
    TripService tripService;

    @Autowired
    UserService userService;

    @Autowired
    TripMapper tripMapper;

    // TODO: Implement proper return responses instead of <Object>
    // TODO: Add fare calculation methods to be requested from
    // TODO: Implement start date/time for calculating fare
    // TODO: Implement fare dto to return when requested and when finishing trip

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
    public ResponseEntity<Object> pollGetRequestedTrips() {

        try {
            List<Trip> requestedTrip = tripService.checkForNewTrips();

            // Intellij optimized, no idea about this functional expression
            if (requestedTrip == null) return ResponseEntity.ok().body("No trips available");
            else return ResponseEntity.ok(requestedTrip);
        }
        catch (AlreadyBookedException aBE) {
            return ResponseEntity.ok().body(aBE.getMessage());
        }
    }

    @PatchMapping(path = "/assign-driver")
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @Transactional
    public ResponseEntity<Object> assignTripDriver(@Valid @RequestBody TripMinimalDTO request) {

        try {
            Optional<Trip> opTrip = tripService.assignForTrip(request);

            // Intellij things
            return opTrip.<ResponseEntity<Object>>map(trip ->
                    ResponseEntity.ok().body(trip)).orElseGet(() ->
                    ResponseEntity.ok().body("Trip already taken"));
        }
        catch (TripNotFoundException | AlreadyBookedException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping(path = "/cancel-trip")
    @PreAuthorize("hasAnyRole('ROLE_PASSENGER', 'ROLE_DRIVER')")
    @Transactional
    public ResponseEntity<Object> cancelTrip(@Valid @RequestBody TripMinimalDTO request) {

        if (tripService.cancelTrip(request) == 1) return ResponseEntity.ok().body("Success");
        else return ResponseEntity.badRequest().body("Failed to cancel trip");
    }

    @PatchMapping(path = "/finish-trip")
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @Transactional
    public ResponseEntity<Object> finishTrip(@Valid @RequestBody TripMinimalDTO request) {

        if (tripService.finishTrip(request) == 1) return ResponseEntity.ok().body("Success");
        else return ResponseEntity.badRequest().body("Failed to finish trip");
    }

    @PatchMapping(path = "/start-trip")
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @Transactional
    public ResponseEntity<Object> startTrip(@Valid @RequestBody TripMinimalDTO request) {

        if (tripService.startTrip(request) == 1) return ResponseEntity.ok().body("Success");
        else return ResponseEntity.badRequest().body("Failed to start trip");
    }
}
