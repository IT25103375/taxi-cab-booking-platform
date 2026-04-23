package com.taxiandcabservice.controllers;

import com.taxiandcabservice.dto.RegionRequest;
import com.taxiandcabservice.entities.*;
import com.taxiandcabservice.repositories.VehicleTypeRepository;
import com.taxiandcabservice.service.RegionService;
import com.taxiandcabservice.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/admin")
public class AdminDBController {

    @Autowired
    VehicleService vehicleService;

    @Autowired
    RegionService regionService;

    @PostMapping("/vehicle-type")
    public ResponseEntity<VehicleType> testVehicleTypeAdd(@RequestBody VehicleType vehicleType){
        vehicleService.addVehicleType(vehicleType);
        return ResponseEntity.ok(vehicleType);
    }

    @PostMapping("/region")
    public ResponseEntity<Object> testRegionAdd(@RequestBody RegionRequest regionRequest){

        try {
            regionService.addRegion(regionRequest);
            return  ResponseEntity.ok("Success");
        }
        catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }
}
