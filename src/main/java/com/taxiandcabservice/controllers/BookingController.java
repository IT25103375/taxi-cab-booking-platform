package com.taxiandcabservice.controllers;

import com.taxiandcabservice.dto.TripCreationDTO;
import com.taxiandcabservice.dto.TripDTO;
import com.taxiandcabservice.entities.*;
import com.taxiandcabservice.mappers.TripMapper;
import com.taxiandcabservice.service.RegionService;
import com.taxiandcabservice.service.TripService;
import com.taxiandcabservice.service.UserService;
import com.taxiandcabservice.service.VehicleService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/booking")
public class BookingController {

    @Autowired
    TripService tripService;

    @Autowired
    RegionService regionService;

    @Autowired
    UserService userService;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    TripMapper tripMapper;

    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @PostMapping("/trip")
    @Transactional
    public ResponseEntity<Object> CreateTrip(@Valid @RequestBody TripDTO request) throws RuntimeException{

        // Use intermediate dto to reduce clutter in controller
        TripCreationDTO tCDTO = tripMapper.toTrip(request, userService.findUser(((AuthEntity)SecurityContextHolder.getContext().
                getAuthentication().getPrincipal()).getEmail()).get());

        return ResponseEntity.ok().body(tripService.createTrip(tCDTO)
                .orElseThrow(() -> new RuntimeException("Trip creation failed")));
    }
}
