package com.taxiandcabservice.repositories;

import com.taxiandcabservice.entities.Region;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RegionRepository extends CrudRepository<Region, Integer> {

    Optional<Region> findByName(String name);
}
