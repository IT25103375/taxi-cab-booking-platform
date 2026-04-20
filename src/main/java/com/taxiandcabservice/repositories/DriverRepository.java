package com.taxiandcabservice.repositories;

import com.taxiandcabservice.entities.SubRegion;
import com.taxiandcabservice.entities.Driver;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DriverRepository extends CrudRepository<Driver, Integer> {

    @Modifying
    @Query("""
    UPDATE Driver d
    SET d.status = 'BOOKED'
    WHERE d.id = :id
    AND d.status = 'AVAILABLE'
    """)
    int bookDriverIfAvailable(Integer id);

    @Query("""
    SELECT d FROM Driver d
    WHERE d.subRegion = :subRegion
    AND d.status = 'AVAILABLE'
    """)
    List<Driver> findBySubRegion(SubRegion subRegion);
}
