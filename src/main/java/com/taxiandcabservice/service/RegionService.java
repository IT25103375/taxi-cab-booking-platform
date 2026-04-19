package com.taxiandcabservice.service;

import com.taxiandcabservice.entities.Region;
import com.taxiandcabservice.repositories.RegionRepository;
import com.taxiandcabservice.entities.SubRegion;
import com.taxiandcabservice.repositories.SubRegionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private SubRegionRepository subRegionRepository;

    @Transactional
    public Optional<Region> findRegion(String name) {
        return regionRepository.findByName(name);
    }

    @Transactional
    public Optional<SubRegion> findSubRegion(String name) {
        return subRegionRepository.findByName(name);
    }
}
