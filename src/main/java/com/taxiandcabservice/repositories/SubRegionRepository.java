package com.taxiandcabservice.repositories;

import com.taxiandcabservice.entities.SubRegion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SubRegionRepository extends CrudRepository<SubRegion, Integer> {

    Optional<SubRegion> findByName(String name);

    @Query("""
    SELECT sr FROM SubRegion sr
    WHERE sr.region.name = :regionName
    """)
    List<SubRegion> findAllByRegionName(String regionName);

    @Query("""
    SELECT sr FROM SubRegion sr
    WHERE sr.region.id = :regionId
    """)
    List<SubRegion> findAllByRegionId(Integer regionId);
}
