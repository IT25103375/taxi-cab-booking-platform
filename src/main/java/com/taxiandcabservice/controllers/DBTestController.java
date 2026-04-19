package com.taxiandcabservice.controllers;

import com.taxiandcabservice.entities.*;
import com.taxiandcabservice.repositories.RegionRepository;
import com.taxiandcabservice.repositories.SubRegionRepository;
import com.taxiandcabservice.repositories.VehicleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/test")
public class DBTestController {

    @Autowired
    VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    SubRegionRepository subRegionRepository;

    @PostMapping("/vehicle-type")
    public ResponseEntity<VehicleType> testVehicleTypeAdd(@RequestBody VehicleType vehicleType){
        vehicleTypeRepository.save(vehicleType);
        return ResponseEntity.ok(vehicleType);
    }

    @PostMapping("/region")
    public ResponseEntity<Region> testRegionAdd(@RequestBody Region region){
        regionRepository.save(region);
        return ResponseEntity.ok(region);
    }

    @PostMapping("/sub-region")
    public ResponseEntity<SubRegion> testSubRegionAdd(@RequestBody SubRegion subRegion){

//        // Update region's subregion list - Not Needed, automatically updated
//        Optional<Region> associatedRegion = regionRepository.findByName(subRegion.getRegion().getName());
//        associatedRegion.ifPresent(region -> {
//            region.subRegions.add(subRegion);
//            regionRepository.save(region);
//        });

        subRegionRepository.save(subRegion);
        return ResponseEntity.ok(subRegion);
    }
}
