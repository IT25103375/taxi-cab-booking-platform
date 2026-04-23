package com.taxiandcabservice.controllers;

import com.taxiandcabservice.dto.VehicleDTO;
import com.taxiandcabservice.dto.VehicleMinimalDTO;
import com.taxiandcabservice.mappers.VehicleMapper;
import com.taxiandcabservice.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/vehicle")
public class VehicleController {

    @Autowired
    DriverService driverService;

    @Autowired
    VehicleMapper vehicleMapper;

    @PostMapping(path = "/register")
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    public ResponseEntity<Object> registerVehicle(@Valid @RequestBody VehicleDTO request) {

        return ResponseEntity.ok(driverService.addVehicle(request));
    }

    @PostMapping(path = "/current")
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    public ResponseEntity<Object> setCurrentVehicle(@Valid @RequestBody VehicleMinimalDTO request) {

        return ResponseEntity.ok(driverService.setCurrentVehicle(request));
    }

    @PostMapping(path = "/remove")
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    public ResponseEntity<Object> removeVehicle(@Valid @RequestBody VehicleMinimalDTO request) {

        return ResponseEntity.ok(driverService.removeVehicle(request));
    }
}
