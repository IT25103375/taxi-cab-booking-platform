package com.taxiandcabservice.service;

import com.taxiandcabservice.dto.RegionRequest;
import com.taxiandcabservice.entities.Region;
import com.taxiandcabservice.mappers.SubRegionMapper;
import com.taxiandcabservice.repositories.RegionRepository;
import com.taxiandcabservice.entities.SubRegion;
import com.taxiandcabservice.repositories.SubRegionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private SubRegionRepository subRegionRepository;

    @Autowired
    private SubRegionMapper subRegionMapper;

    @Transactional
    public Optional<Region> findRegion(String name) {
        return regionRepository.findByName(name);
    }

    @Transactional
    public Optional<SubRegion> findSubRegion(String name) {
        return subRegionRepository.findByName(name);
    }

    @Transactional
    public void addRegion(RegionRequest request) {

        // Find an existing region entity or make a new one
        Region region = regionRepository.findByName(request.getRegionName()).orElseGet(() -> (new Region())
                        .setName(request.getRegionName())
                        .setDisplayName(request.getRegionDisplayName()));

        // Find existing subregions or return empty list
        List<SubRegion> subRegions = subRegionRepository.findByRegionName(request.getRegionName());

        // Add new subregions and list
        subRegions.addAll(subRegionMapper.toSubRegionList(request.getSubRegions(), region));
        region.setSubRegions(subRegions);

        regionRepository.save(region);
        subRegionRepository.saveAll(subRegions);
    }
}