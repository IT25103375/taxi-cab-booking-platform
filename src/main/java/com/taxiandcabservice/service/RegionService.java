package com.taxiandcabservice.service;

import com.taxiandcabservice.dto.RegionRequest;
import com.taxiandcabservice.entities.Region;
import com.taxiandcabservice.mappers.RegionMapper;
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
    private RegionMapper regionMapper;

    @Transactional
    public Optional<Region> findRegion(Integer id) {
        return regionRepository.findById(id);
    }

    @Transactional
    public Optional<SubRegion> findSubRegion(Integer id) {
        return subRegionRepository.findById(id);
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
        subRegions.addAll(regionMapper.toSubRegionList(request.getSubRegions(), region));
        region.setSubRegions(subRegions);

        regionRepository.save(region);
        subRegionRepository.saveAll(subRegions);
    }
}