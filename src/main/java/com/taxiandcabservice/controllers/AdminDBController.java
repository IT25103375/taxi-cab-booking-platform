package com.taxiandcabservice.controllers;

import com.taxiandcabservice.dto.RegionRequest;
import com.taxiandcabservice.entities.*;
import com.taxiandcabservice.repositories.VehicleTypeRepository;
import com.taxiandcabservice.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/admin")
public class AdminDBController {

    @Autowired
    VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    RegionService regionService;

    @PostMapping("/vehicle-type")
    public ResponseEntity<VehicleType> testVehicleTypeAdd(@RequestBody VehicleType vehicleType){
        vehicleTypeRepository.save(vehicleType);
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
