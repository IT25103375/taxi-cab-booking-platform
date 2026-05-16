package com.taxiandcabservice.controllers;

import com.taxiandcabservice.dto.VehicleDTO;
import com.taxiandcabservice.dto.VehicleDisplayDTO;
import com.taxiandcabservice.dto.VehicleMinimalDTO;
import com.taxiandcabservice.dto.VehicleTypeDTO;
import com.taxiandcabservice.mappers.VehicleMapper;
import com.taxiandcabservice.repositories.DriverRepository;
import com.taxiandcabservice.service.DriverService;
import com.taxiandcabservice.service.UserService;
import com.taxiandcabservice.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(path = "/api/vehicle")
public class VehicleController {

    @Autowired
    DriverService driverService;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    VehicleMapper vehicleMapper;

    @Autowired
    UserService userService;

    @PostMapping(path = "/register")
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    public ResponseEntity<VehicleDisplayDTO> registerVehicle(@Valid @RequestBody VehicleDTO request) {

        Optional<VehicleDisplayDTO> vehicleDisplay = driverService.addVehicle(request);
        return vehicleDisplay.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PatchMapping(path = "/current")
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    public ResponseEntity<Boolean> setCurrentVehicle(@Valid @RequestBody VehicleMinimalDTO request) {

        return ResponseEntity.ok(driverService.setCurrentVehicle(request));
    }

    @DeleteMapping(path = "/remove")
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    public ResponseEntity<Boolean> removeVehicle(@Valid @RequestBody VehicleMinimalDTO request) {

        return ResponseEntity.ok(driverService.removeVehicle(request));
    }

    @GetMapping(path = "/get")
    public ResponseEntity<List<VehicleDisplayDTO>> getVehicles() {

        return ResponseEntity.ok(vehicleMapper.toVehicleDTOList(vehicleService.findAllVehiclesById(userService.getCurrentDriver().getId())));
    }

    @GetMapping(path = "/get-types")
    public ResponseEntity<List<VehicleTypeDTO>> getVehicleTypes() {

        return ResponseEntity.ok(vehicleMapper.toVehicleTypeDTOList(vehicleService.findAllVehicleTypes()));
    }

    @GetMapping(path = "/get-current")
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    public ResponseEntity<VehicleDisplayDTO> getCurrentVehicle() {

        return ResponseEntity.ok(driverService.getCurrentVehicle());
    }
}
