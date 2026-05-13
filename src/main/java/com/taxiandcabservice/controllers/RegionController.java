package com.taxiandcabservice.controllers;

import com.taxiandcabservice.dto.RegionCreationDTO;
import com.taxiandcabservice.dto.RegionDTO;
import com.taxiandcabservice.dto.SubRegionDTO;
import com.taxiandcabservice.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Rest api
@CrossOrigin
@RequestMapping(path = "/api/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping(path = "/regions")
    public List<RegionDTO> getRegions() { return regionService.findAllRegions(); }

    @GetMapping(path = "/sub-regions")
    public List<SubRegionDTO> getSubRegions(@RequestParam(name = "region_id") Integer regionId) { return regionService.findAllSubRegions(regionId); }
}
